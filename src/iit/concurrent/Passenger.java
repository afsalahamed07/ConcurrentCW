package iit.concurrent;

public class Passenger implements Runnable {

    private final String name;
    private Ticket ticket;

    // the initialisation of the ticket machine is not done by the passenger
    private final Printer ticketMachine;

    private Destinations from;
    private Destinations to;

//    public Passenger(String name, TicketMachine ticketMachine) {
//        this.name = name;
//        this.ticketMachine = ticketMachine;
//    }

    public Passenger(String name,
                     Destinations from,
                     Destinations to,
                     TicketMachine ticketMachine) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.ticketMachine = ticketMachine;
    }

    @Override
    public void run() {
        printTicket(ticketMachine);
    }

    public void printTicket(Printer ticketMachine) {
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

    public Destinations getFrom() {
        return from;
    }

    public Destinations getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                '}';
    }
}
