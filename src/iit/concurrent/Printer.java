package iit.concurrent;

public interface Printer {

    // print the "ticker"
    public int printTicket(Passenger passenger);

    public void registerPassenger();

    public void unregisterPassenger();
}
