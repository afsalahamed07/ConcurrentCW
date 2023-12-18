package iit.concurrent;

public class Passenger implements Runnable {

    private final String name;
    private final Printer ticketMachine;
    private Ticket ticket;

    public Passenger(String name,
                     Printer ticketMachine) {
        this.name = name;
        this.ticketMachine = ticketMachine;
    }

    public Passenger(String name,
                     Printer ticketMachine,
                     int numStops) {
        this.name = name;
        this.ticketMachine = ticketMachine;
    }

    @Override
    public void run() {
        ticketMachine.registerPassenger();

        if (ticketMachine.printTicket(this) == Constants.POISON_PILL) {
            Thread.currentThread().interrupt();
            return;
        }

        ticketMachine.unregisterPassenger();
    }

    public String getName() {
        return name;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                ", ticketMachine=" + ticketMachine +
                '}';
    }
}
