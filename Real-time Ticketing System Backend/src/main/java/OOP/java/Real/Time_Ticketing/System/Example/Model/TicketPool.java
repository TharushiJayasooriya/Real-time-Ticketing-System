package OOP.java.Real.Time_Ticketing.System.Example.Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TicketPool {
    private final BlockingQueue<String> ticketQueue;
    private final int maxCapacity;
    private int availableTickets;
    private int processedTickets;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.availableTickets = 0;
        this.processedTickets = 0;
        this.ticketQueue = new ArrayBlockingQueue<>(maxCapacity);
    }

    // Add ticket to the TicketPool
    public synchronized void addTicket(String ticket) throws InterruptedException {
        // If there's space, add the ticket to the queue
        if (ticketQueue.size() < maxCapacity) {
            ticketQueue.put(ticket); // Blocks if queue is full
            availableTickets++;
            System.out.println("Vendor added: " + ticket + " | Available tickets: " + availableTickets);
            notifyAll();
        } else {
            System.out.println("Cannot add ticket: " + ticket + ". Ticket pool is full.");
            wait();
        }
    }

    // Retrieve ticket from the TicketPool
    public synchronized String retrieveTicket() throws InterruptedException {
        if (availableTickets == 0) {
            wait();
        }
        String ticket = ticketQueue.take(); // Blocks if queue is empty
        availableTickets--;
        processedTickets++;
        System.out.println("Customer retrieved: " + ticket + " | Available tickets: " + availableTickets);
        notifyAll();
        return ticket;
    }

    //  available tickets
    public synchronized int getAvailableTickets() {
        return availableTickets;
    }

    //  processed tickets
    public synchronized int getProcessedTickets() {
        return processedTickets;
    }

    //  max capacity of the TicketPool
    public int getMaxCapacity() {
        return maxCapacity;
    }

}