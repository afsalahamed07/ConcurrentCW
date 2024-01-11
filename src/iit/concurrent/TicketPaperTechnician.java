package iit.concurrent;

import java.util.Random;

public class TicketPaperTechnician extends Technician {

    protected TicketPaperTechnician(String name, ServiceTicketMachine ticketMachine) {
        super(name, ticketMachine);
    }

    @Override
    public void run() {
        /**
         * This way the paper will be filled only thrice
         * after that
         * the passengers will starve
         */
        // should reuse the random object
        // to avoid creating a new one every time
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            if (ticketMachine.refillTicketPaper() == Constants.POISON_PILL) {
                Thread.currentThread().interrupt();
                return;
            }
            try {
//                Thread.sleep(1000);
                int sleepTime = random.nextInt(2000);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
