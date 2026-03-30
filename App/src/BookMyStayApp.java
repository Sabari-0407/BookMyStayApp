import java.util.*;

// Booking Request class
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking System
class BookingSystem {

    // Shared inventory
    private Map<String, Integer> inventory = new HashMap<>();

    // Shared booking queue
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    public BookingSystem() {
        inventory.put("Deluxe", 2);
    }

    // Add request to queue (synchronized)
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println(request.guestName + " added booking request.");
    }

    // Process booking (critical section)
    public void processBooking() {

        BookingRequest request;

        synchronized (this) {
            if (bookingQueue.isEmpty()) {
                return;
            }
            request = bookingQueue.poll();
        }

        if (request != null) {
            allocateRoom(request);
        }
    }

    // Critical section for allocation
    private void allocateRoom(BookingRequest request) {

        synchronized (this) {

            int available = inventory.getOrDefault(request.roomType, 0);

            if (available > 0) {
                inventory.put(request.roomType, available - 1);

                System.out.println(
                        "Booking confirmed for " + request.guestName +
                                " | Room Type: " + request.roomType +
                                " | Remaining: " + (available - 1)
                );
            } else {
                System.out.println(
                        "Booking failed for " + request.guestName +
                                " | No rooms available"
                );
            }
        }
    }
}

// Worker Thread
class BookingProcessor extends Thread {

    private BookingSystem system;

    public BookingProcessor(BookingSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        while (true) {
            system.processBooking();

            try {
                Thread.sleep(100); // simulate processing delay
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Simulate multiple guest requests
        system.addRequest(new BookingRequest("Guest1", "Deluxe"));
        system.addRequest(new BookingRequest("Guest2", "Deluxe"));
        system.addRequest(new BookingRequest("Guest3", "Deluxe"));
        system.addRequest(new BookingRequest("Guest4", "Deluxe"));

        // Create multiple threads
        BookingProcessor t1 = new BookingProcessor(system);
        BookingProcessor t2 = new BookingProcessor(system);

        t1.start();
        t2.start();

        // Let threads run for a short time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        // Stop threads
        t1.interrupt();
        t2.interrupt();

        System.out.println("\nSimulation completed.");
    }
}