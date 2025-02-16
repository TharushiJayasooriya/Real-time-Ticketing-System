package OOP.java.Real.Time_Ticketing.System.Example.Model;

import java.util.logging.Logger;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    Logger logger= Logger.getLogger(Customer.class.getName());
    private PostTool postTool = new PostTool();

    public Customer(TicketPool ticketPool, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        int ticketID = 1;
        while (!Thread.currentThread().isInterrupted() && ticketID <= retrievalRate) {
            try {
                ticketPool.retrieveTicket();
                //System.out.println("Customer retrieved ticket: " + ticket);
                LoggerFileHandler.Logging(logger , Thread.currentThread().getName()+ "remove ticket" +ticketID , false );
                postTool.sendPostRequestWithJSON("http://localhost:8080/api/logs",Thread.currentThread().getName() +" bought ticket " + ticketID);

                Thread.sleep(1000 / retrievalRate);
            } catch (InterruptedException e) {
                System.out.println("Customer thread interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
            ticketID++;
        }
    }
}