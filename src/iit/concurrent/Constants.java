package iit.concurrent;

import java.math.BigDecimal;

public class Constants {
    private Constants() {
    }

    public static final int FULL_PAPER_TRAY = 250;
    public static final int SHEETS_PER_PACK = 50;

    public static final int FULL_TONER_LEVEL = 500;
    public static final int MINIMUM_TONER_LEVEL = 10;

    public static final int POISON_PILL = -1;

    public static final BigDecimal PRICE_PER_STOP = new BigDecimal(10);


}
