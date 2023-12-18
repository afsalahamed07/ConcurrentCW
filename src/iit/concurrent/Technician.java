package iit.concurrent;

public abstract class Technician implements Runnable {

    protected String name;
    protected ServiceTicketMachine ticketMachine;


    protected Technician(String name,
                         ServiceTicketMachine ticketMachine) {
        this.name = name;
        this.ticketMachine = ticketMachine;
    }

    public String getName() {
        return name;
    }
}
