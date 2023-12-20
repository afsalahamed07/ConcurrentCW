package iit.concurrent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Ticket {
    private final Passenger passenger;
    private final String ticketID;

    private int numberOfStops;
    private BigDecimal price;
    private LocalDate journeyDate;

    public Ticket(Passenger passenger,
                  int ticketNo,
                  int numberOfStops) {
        Objects.requireNonNull(passenger, "Passenger cannot be null");
        setNumberOfStops(numberOfStops);
        setPrice();
        this.passenger = passenger;
        this.ticketID = "TIK" + ticketNo;
        this.journeyDate = LocalDate.now(); // Setting the journey date to the current date
    }

    public void setNumberOfStops(int numberOfStops) {
        if (numberOfStops < 0) {
            throw new IllegalArgumentException("Number of stops cannot be negative");
        }
        this.numberOfStops = numberOfStops;
    }

    public void setPrice() {
        price = BigDecimal.valueOf((long) numberOfStops * Constants.PRICE_PER_STOP.precision());
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
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
