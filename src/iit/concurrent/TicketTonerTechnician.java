package iit.concurrent;

import java.util.Random;

public class TicketTonerTechnician extends Technician {
    public TicketTonerTechnician(String name, ServiceTicketMachine ticketMachine) {
        super(name, ticketMachine);
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            if (ticketMachine.replaceTonerCartridge() == Constants.POISON_PILL) {
                Thread.currentThread().interrupt();
                return;
            }
            try {
//                Thread.sleep(1000);
                // Generate a random sleep time between 0 and 2000 milliseconds
                int sleepTime = random.nextInt(2000);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
