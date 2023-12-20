package iit.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TicketMachine implements ServiceTicketMachine, Printer {
    private static TicketMachine TICKET_MACHINE;
    private int currentTonerLevel;
    private int currentPaperLevel;
    private int ticketsDispensed; // keeps track of tickets dispensed

    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final Condition haveToner = reentrantLock.newCondition();
    private final Condition noToner = reentrantLock.newCondition();
    private final Condition havePaper = reentrantLock.newCondition();
    private final Condition noPaper = reentrantLock.newCondition();


    // keep tracks of current passenger threads accessing the machine
    private final AtomicInteger activePassenger = new AtomicInteger(0);
    // to terminate long waiting threads
    private static final Logger LOGGER = Logger.getLogger(TicketMachine.class.getName());


    private TicketMachine(int currentTonerLevel, int currentPaperLevel) {
        this.currentTonerLevel = currentTonerLevel;
        this.currentPaperLevel = currentPaperLevel;
        this.ticketsDispensed = 0;
    }

    public static TicketMachine getTicketMachine() {
        // Making the ticket machine singleton
        // to make sure there's only one machine in the system
        if (TICKET_MACHINE == null) TICKET_MACHINE = new TicketMachine(Constants.FULL_TONER_LEVEL, Constants.FULL_PAPER_TRAY);
        return TICKET_MACHINE;
    }

    public static TicketMachine getHalfFullTicketMachine() {
        // Making the ticket machine singleton
        // to make sure there's only one machine in the system
        if (TICKET_MACHINE == null) TICKET_MACHINE = new TicketMachine(50, 50);
        return TICKET_MACHINE;
    }

    @Override
    public int replaceTonerCartridge() {
        reentrantLock.lock();
        try {
            LOGGER.log(Level.INFO, "{0} attempting replace Toner Cartridge",
                    Thread.currentThread().getName());

            while (currentTonerLevel > Constants.MINIMUM_TONER_LEVEL) {
                haveToner.await(1, TimeUnit.SECONDS);
                if (activePassengerCheck()) return Constants.POISON_PILL;
            }

            noToner.signalAll();
            currentTonerLevel = Constants.FULL_TONER_LEVEL;
            LOGGER.log(Level.INFO, "{0} replaced Toner Cartridge", Thread.currentThread().getName());
            LOGGER.log(Level.INFO, "current machine state {0}", this);
            return 1;
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, () -> Thread.currentThread().getName() + "Thread interrupted" + e);
            // Re-interrupt the thread to preserve the interrupted status
            Thread.currentThread().interrupt();
            // Terminating the thread with poison pill
            return Constants.POISON_PILL;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public int refillTicketPaper() {
        reentrantLock.lock();
        try {
            LOGGER.log(Level.INFO, "{0} attempting replace refill ticket paper",
                    Thread.currentThread().getName());

            while (currentPaperLevel >= 200) {

                havePaper.await(1, TimeUnit.SECONDS);
                if (activePassengerCheck()) return Constants.POISON_PILL;
            }

            currentPaperLevel += Constants.SHEETS_PER_PACK;
            noPaper.signalAll();
            LOGGER.log(Level.INFO, "{0} refilled papers", Thread.currentThread().getName());
            LOGGER.log(Level.INFO, "current machine state {0}", this);
            return 1;
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, () -> Thread.currentThread().getName() + "Thread interrupted" + e);
            // Re-interrupt the thread to preserve the interrupted status
            Thread.currentThread().interrupt();
            // Terminating the thread with poison pill
            return Constants.POISON_PILL;
        } finally {
            reentrantLock.unlock();
        }
    }


    @Override
    public int printTicket(Passenger passenger) {
        reentrantLock.lock();

        try {
            // keeps track of number waits by the thread
            int passengerTries = 0;

            while (currentTonerLevel < Constants.MINIMUM_TONER_LEVEL || currentPaperLevel <= 0) {
                try {
                       passengerTries++;
                    int finalPassengerTries = passengerTries;
                    LOGGER.log(Level.INFO, () -> Thread.currentThread().getName() + " number of tries " + finalPassengerTries);


                    if (currentTonerLevel < Constants.MINIMUM_TONER_LEVEL) {
                        noToner.await(1, TimeUnit.SECONDS);
                        LOGGER.log(Level.WARNING, "{0} is waiting due to not enough toner", Thread.currentThread().getName());
                    }

                    if (currentPaperLevel <= 0) {
                        noPaper.await(1, TimeUnit.SECONDS);
                        LOGGER.log(Level.WARNING, "{0} is waiting due to not enough paper", Thread.currentThread().getName());
                    }

                    if (passengerTries >= 3) {
                        LOGGER.log(Level.WARNING, "{0} got interrupted due to multiple tries", Thread.currentThread().getName());
                        return Constants.POISON_PILL;
                    }
                } catch (InterruptedException e) {
                    LOGGER.log(Level.SEVERE, () -> Thread.currentThread().getName() + "Thread interrupted" + e);
                    // Re-interrupt the thread to preserve the interrupted status
                    Thread.currentThread().interrupt();
                    // Terminating the thread with poison pill
                    return Constants.POISON_PILL;
                }
            }


            Ticket ticket = generateTicket(passenger);
            passenger.setTicket(ticket);

            // updating current states of the machine
            currentTonerLevel--;
            currentPaperLevel--;
            ticketsDispensed++;

            havePaper.signalAll();
            haveToner.signalAll();

            LOGGER.log(Level.INFO, () -> Thread.currentThread().getName() + " printed a ticket : " + ticket);
            return 1;
        } finally {
            reentrantLock.unlock();
        }

    }

    private Ticket generateTicket(Passenger passenger) {
        int ticketNumber =  Math.floorDiv(passenger.getFrom().getValue(), passenger.getTo().getValue());
        return new Ticket(passenger, ticketsDispensed, ticketNumber);
    }

    @Override
    public void registerPassenger() {
        activePassenger.incrementAndGet();
    }

    @Override
    public void unregisterPassenger() {
        activePassenger.decrementAndGet();
    }

    private boolean activePassengerCheck() {
        if (activePassenger.get() == 0) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.WARNING, "{0} got interrupted due to no passenger", Thread.currentThread().getName());
            return true;
        }

        LOGGER.log(Level.WARNING, "{0} technician waiting", Thread.currentThread().getName());

        return false;
    }

    @Override
    public String toString() {
        return "TicketMachine{" +
                "currentTonerLevel=" + currentTonerLevel +
                ", currentPaperLevel=" + currentPaperLevel +
                ", ticketsDispensed=" + ticketsDispensed +
                ", activePassenger=" + activePassenger +
                '}';
    }
}
