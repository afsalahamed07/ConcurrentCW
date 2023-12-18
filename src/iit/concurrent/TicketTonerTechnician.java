package iit.concurrent;

public class TicketTonerTechnician extends Technician {
    public TicketTonerTechnician(String name, ServiceTicketMachine ticketMachine) {
        super(name, ticketMachine);
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            if (ticketMachine.replaceTonerCartridge() == Constants.POISON_PILL) {
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
