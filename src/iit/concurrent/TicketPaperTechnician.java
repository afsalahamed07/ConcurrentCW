package iit.concurrent;

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
        for (int i = 0; i < 3; i++) {
            if (ticketMachine.refillTicketPaper() == Constants.POISON_PILL) {
                Thread.currentThread().interrupt();
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
