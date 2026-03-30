import java.util.*;

// Booking class to store booking details
class Booking {
    String bookingId;
    String roomType;
    String roomId;
    boolean isCancelled;

    public Booking(String bookingId, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

// Main class
public class BookMyStayApp {

    // Inventory: Room Type -> Available Count
    private static Map<String, Integer> inventory = new HashMap<>();

    // Room allocation: Room Type -> Stack of Available Room IDs
    private static Map<String, Stack<String>> roomPool = new HashMap<>();

    // Active bookings
    private static Map<String, Booking> bookings = new HashMap<>();

    // Rollback stack (tracks released room IDs)
    private static Stack<String> rollbackStack = new Stack<>();

    public static void main(String[] args) {

        // Initial Setup
        initializeInventory();

        // Create a booking
        createBooking("B101", "Deluxe");

        // Cancel booking
        cancelBooking("B101");

        // Try invalid cancellation
        cancelBooking("B101");
    }

    // Initialize inventory and room pool
    private static void initializeInventory() {
        inventory.put("Deluxe", 2);

        Stack<String> deluxeRooms = new Stack<>();
        deluxeRooms.push("D1");
        deluxeRooms.push("D2");

        roomPool.put("Deluxe", deluxeRooms);
    }

    // Booking creation
    private static void createBooking(String bookingId, String roomType) {

        if (!inventory.containsKey(roomType) || inventory.get(roomType) == 0) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }

        Stack<String> rooms = roomPool.get(roomType);
        String roomId = rooms.pop();

        inventory.put(roomType, inventory.get(roomType) - 1);

        Booking booking = new Booking(bookingId, roomType, roomId);
        bookings.put(bookingId, booking);

        System.out.println("Booking confirmed: " + bookingId + ", Room: " + roomId);
    }

    // Cancellation with rollback
    private static void cancelBooking(String bookingId) {

        System.out.println("\nAttempting cancellation for Booking ID: " + bookingId);

        // Validation
        if (!bookings.containsKey(bookingId)) {
            System.out.println("Cancellation failed: Booking does not exist.");
            return;
        }

        Booking booking = bookings.get(bookingId);

        if (booking.isCancelled) {
            System.out.println("Cancellation failed: Booking already cancelled.");
            return;
        }

        // Step 1: Record room ID in rollback stack
        rollbackStack.push(booking.roomId);

        // Step 2: Restore inventory
        inventory.put(booking.roomType, inventory.get(booking.roomType) + 1);

        // Step 3: Return room to pool
        roomPool.get(booking.roomType).push(booking.roomId);

        // Step 4: Update booking status
        booking.isCancelled = true;

        // Step 5: Log history
        System.out.println("Cancellation successful for Booking ID: " + bookingId);
        System.out.println("Room released: " + booking.roomId);
        System.out.println("Inventory restored for type: " + booking.roomType);
    }
}