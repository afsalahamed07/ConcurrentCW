package iit.concurrent;

public enum Destinations {
    CITY1(1),
    CITY2(2),
    CITY3(3),
    CITY4(4);

    private final int value;

    Destinations(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        // returns the value of the destinations
        // used to calculate the number of stops
        return value;
    }
}
