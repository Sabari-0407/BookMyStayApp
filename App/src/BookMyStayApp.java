import java.util.*;

// Reservation (booking request)
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int change) {
        if (inventory.containsKey(type)) {
            int updated = inventory.get(type) + change;
            if (updated >= 0) {
                inventory.put(type, updated);
            }
        }
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // dequeue
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service (Allocation Logic)
class BookingService {

    private RoomInventory inventory;

    // Track all allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds;

    // Track room IDs per room type
    private HashMap<String, Set<String>> roomAllocations;

    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    public void processBookings(BookingRequestQueue queue) {

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String type = request.getRoomType();

            System.out.println("\nProcessing request for " + request.getGuestName());

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId;
                do {
                    roomId = type.substring(0, 1).toUpperCase() + roomCounter++;
                } while (allocatedRoomIds.contains(roomId));

                // Add to global set
                allocatedRoomIds.add(roomId);

                // Map room type to allocated IDs
                roomAllocations.putIfAbsent(type, new HashSet<>());
                roomAllocations.get(type).add(roomId);

                // Update inventory immediately
                inventory.updateAvailability(type, -1);

                // Confirm reservation
                System.out.println("Booking CONFIRMED for " + request.getGuestName());
                System.out.println("Room Type: " + type + " | Room ID: " + roomId);

            } else {
                System.out.println("Booking FAILED for " + request.getGuestName() +
                        " (No availability for " + type + ")");
            }
        }
    }

    public void displayAllocations() {
        System.out.println("\nRoom Allocations:");
        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Main Driver
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);
        inventory.addRoomType("Suite", 1);

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Single")); // should fail
        queue.addRequest(new Reservation("David", "Suite"));

        // Booking service
        BookingService bookingService = new BookingService(inventory);

        // Process all requests (FIFO)
        bookingService.processBookings(queue);

        // Display final allocations
        bookingService.displayAllocations();

        // Display updated inventory
        inventory.displayInventory();
    }
}