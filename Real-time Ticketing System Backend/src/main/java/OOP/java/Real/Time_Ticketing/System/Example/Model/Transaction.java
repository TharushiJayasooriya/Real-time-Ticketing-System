package OOP.java.Real.Time_Ticketing.System.Example.Model;

import java.util.Scanner;
import org.json.simple.JSONObject;
import java.util.concurrent.locks.ReentrantLock;

public class Transaction {
    private static boolean simulationIsRunning = false;
    private static TicketPool ticketPool;
    public static Thread vendorThread;
    public static Thread customerThread;
    private static final ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {
        clearScreen();

        // Welcome message
        System.out.println("*** Welcome to the Real-Time Ticketing System ***");

        // Load or create configurations
        JSONObject config = loadConfigs();
        System.out.println("Configurations: " + config.toString());

        // Extract configuration values
        long totalTickets = getLongValue(config, "totalTickets");
        long ticketReleaseRate = getLongValue(config, "ticketReleaseRate");
        long customerRetrievalRate = getLongValue(config, "customerRetrievalRate");
        long maxTicketCapacity = getLongValue(config, "maxTicketCapacity");

        // Start the simulation
        if (startSimulation((int) totalTickets, (int) ticketReleaseRate, (int) customerRetrievalRate, (int) maxTicketCapacity)) {
            System.out.println("Simulation started successfully.");
        } else {
            System.out.println("Failed to start the simulation.");
        }

    }
    private static JSONObject loadConfigs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to load configurations? (Y/N): ");
        String choice = scanner.nextLine().trim().toUpperCase();

        if (choice.equals("Y")) {
            JSONObject config = ConfigManager.loadConfigs();
            System.out.println("Loaded configurations: " + config.toString());
            return config;
        } else if (choice.equals("N")) {
            System.out.println("Creating a new configuration...");
            return ConfigManager.createNewConfiguration();
        } else {
            System.out.println("Invalid choice. Exiting.");
            System.exit(0);
        }
        return null;
    }

    private static boolean startSimulation(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        if (simulationIsRunning) {
            System.out.println("Simulation already running.");
            return false;
        }

        try {
            simulationIsRunning = true;
            ticketPool = new TicketPool(maxTicketCapacity);

            // Prefill TicketPool
            int ticketsToAdd = Math.min(totalTickets, maxTicketCapacity);
            System.out.println("Prefilling ticket pool with " + ticketsToAdd + " tickets.");
            for (int i = 1; i <= ticketsToAdd; i++) {
                ticketPool.addTicket("Ticket-" + i);
            }

            // Create and start threads
            Vendor vendor = new Vendor(ticketPool, ticketReleaseRate);
            Customer customer = new Customer(ticketPool, customerRetrievalRate);

            vendorThread = new Thread(vendor);
            customerThread = new Thread(customer);

            vendorThread.start();
            customerThread.start();

            System.out.println("Simulation started.");
            return true;
        } catch (Exception e) {
            simulationIsRunning = false;
            System.err.println("Error starting simulation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static void stopSimulation(boolean simulationIsRunning) {
        if (!Transaction.simulationIsRunning) {
            System.out.println("Simulation is not running.");
            return;
        }

        Transaction.simulationIsRunning = false;

        if (vendorThread != null && vendorThread.isAlive()) vendorThread.interrupt();
        if (customerThread != null && customerThread.isAlive()) customerThread.interrupt();

        try {
            if (vendorThread != null) vendorThread.join();
            if (customerThread != null) customerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error stopping simulation: " + e.getMessage());
        }

        System.out.println("Simulation stopped.");
        ticketPool = null;
    }

    private static String getStatus() {
        lock.lock();
        try {
            if (ticketPool == null) {
                return "Ticket Pool not found.";
            }
            return String.format(
                    "Tickets Available: %d\nTickets Processed: %d\nMax Capacity: %d\nSimulation Running: %b",
                    ticketPool.getAvailableTickets(),
                    ticketPool.getProcessedTickets(),
                    ticketPool.getMaxCapacity(),
                    simulationIsRunning
            );
        } finally {
            lock.unlock();
        }
    }

    private static void clearScreen() {
        System.out.print("\u001B[H\u001B[2J");
        System.out.flush();
    }

    //casting long values to integer values
    private static long getLongValue(JSONObject configuration, String key) {
        Object value = configuration.get(key);
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        } else if (value instanceof Long) {
            return (Long) value;
        }
        throw new IllegalArgumentException("Invalid key: " + key + ", expected integer or long.");
    }
}