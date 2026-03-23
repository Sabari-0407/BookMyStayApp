import java.util.*;

// Custom Exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation
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

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int change) throws InvalidBookingException {

        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }

        int current = inventory.get(type);
        int updated = current + change;

        if (updated < 0) {
            throw new InvalidBookingException("Cannot reduce inventory below zero for " + type);
        }

        inventory.put(type, updated);
    }
}

// Validator (Fail-Fast checks)
class BookingValidator {

    public static void validate(Reservation reservation, RoomInventory inventory)
            throws InvalidBookingException {

        if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (reservation.getRoomType() == null || reservation.getRoomType().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        if (!inventory.isValidRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException("Room type does not exist: " + reservation.getRoomType());
        }

        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + reservation.getRoomType());
        }
    }
}

// Booking Service (with validation + error handling)
class BookingService {

    private RoomInventory inventory;
    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation reservation) {

        try {
            // Step 1: Validate input (fail-fast)
            BookingValidator.validate(reservation, inventory);

            // Step 2: Generate room ID
            String roomId = reservation.getRoomType().substring(0, 1).toUpperCase() + roomCounter++;

            // Step 3: Update inventory safely
            inventory.updateAvailability(reservation.getRoomType(), -1);

            // Step 4: Confirm booking
            System.out.println("Booking CONFIRMED for " + reservation.getGuestName());
            System.out.println("Room Type: " + reservation.getRoomType() + " | Room ID: " + roomId);

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking FAILED for " + reservation.getGuestName());
            System.out.println("Reason: " + e.getMessage());
        }
    }
}

// Main Driver
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 1);
        inventory.addRoomType("Double", 0);

        // Booking service
        BookingService bookingService = new BookingService(inventory);

        // Test cases (valid + invalid)
        Reservation r1 = new Reservation("Alice", "Single");   // valid
        Reservation r2 = new Reservation("Bob", "Double");     // no availability
        Reservation r3 = new Reservation("", "Single");        // invalid name
        Reservation r4 = new Reservation("Charlie", "Suite");  // invalid type

        // Process bookings
        bookingService.confirmBooking(r1);
        bookingService.confirmBooking(r2);
        bookingService.confirmBooking(r3);
        bookingService.confirmBooking(r4);
    }
}