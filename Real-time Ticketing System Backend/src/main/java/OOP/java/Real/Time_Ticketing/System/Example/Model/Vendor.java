package OOP.java.Real.Time_Ticketing.System.Example.Model;

import java.util.logging.Logger;

public class Vendor implements Runnable{
    PostTool postTool=new PostTool();
    private final TicketPool ticketPool;
    private final int releaseRate;
    Logger logger=Logger.getLogger(Vendor.class.getName());

    public Vendor(TicketPool ticketPool, int releaseRate) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        int ticketID = 1;
        while (!Thread.currentThread().isInterrupted() && ticketID <= releaseRate) {
            try {
                String ticket = "Ticket - " + ticketID;
                ticketPool.addTicket(ticket);
                postTool.sendPostRequestWithJSON("http://localhost:8080/api/logs",Thread.currentThread().getName() +" added ticket " + ticketID);
                LoggerFileHandler.Logging(logger,Thread.currentThread().getName() +" added ticket " + ticketID, false);
                Thread.sleep(1000/releaseRate); //make a delay for control the release rate
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            ticketID++;
        }
    }
}