package iit.concurrent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Ticket {
    private final Passenger passenger;
    private final String ticketID;

    private int numOfStops;
    private BigDecimal price;
    private LocalDate journeyDate;

    public Ticket(Passenger passenger,
                  int ticketNo) {
        this.passenger = passenger;
        this.ticketID = "TIK" + ticketNo;
    }

    public Ticket(Passenger passenger,
                   int ticketNo,
                   int numOfStops) {
        Objects.requireNonNull(passenger, "Passenger cannot be null");
        this.passenger = passenger;
        this.ticketID = "TIK" + ticketNo;
        this.numOfStops = numOfStops;
        this.price = BigDecimal.valueOf((long) numOfStops * Constants.PRICE_PER_STOP.precision());
        this.journeyDate = LocalDate.now(); // Setting the journey date to the current date
    }

    public String getTicketID() {
        return ticketID;
    }

    @Override
    public String toString() {
        return "iit.concurrent.Ticket{" +
                "ticketID='" + ticketID + '\'' +
                "Passenger='" + passenger.getName() + '\'' +
                '}';
    }
}
