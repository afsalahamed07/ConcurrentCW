package iit.concurrent;

public class TicketPrintingSystem {
    public static void main(String[] args) {
        // lets creat a que to have tasks to print tickets (msg que)
        // should hold tickets
        // then assign these to the passenger threads

//        TicketMachine ticketMachine = TicketMachine.getTicketMachine();
        TicketMachine ticketMachine = TicketMachine.getHalfFullTicketMachine();


        ThreadGroup passagnersThreadGroup = new ThreadGroup("Passengers");
        ThreadGroup technecionThreadGroup = new ThreadGroup("Technicians");

        Passenger[] passengers = new Passenger[100];
        Technician[] technicians = new Technician[2];

        for (int i = 0; i < passengers.length; i++) {
            passengers[i] = new Passenger("passenger_" + (i + 1), Destinations.CITY4, Destinations.CITY1, ticketMachine);
        }

        technicians[0] = new TicketPaperTechnician("paper_technician", ticketMachine);
        technicians[1] = new TicketTonerTechnician("toner_Technician", ticketMachine);

        Thread[] threads = new Thread[102];

        for (int i = 0; i < passengers.length; i++) {
            threads[i] = new Thread(passagnersThreadGroup, passengers[i], passengers[i].getName() + "_thread");
        }

        for (int i = 0; i < technicians.length; i++) {
            threads[i + 4] = new Thread(technecionThreadGroup, technicians[i], technicians[i].getName() + "_thread");
        }

        for (Thread thread :
                threads) {
            if (thread != null)
                thread.start();
        }
    }
}
