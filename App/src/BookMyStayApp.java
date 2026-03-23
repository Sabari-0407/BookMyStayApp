import java.util.*;

// Reservation (confirmed booking)
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Added to history: " + reservation.getReservationId());
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }

    // Display all bookings
    public void displayHistory() {
        System.out.println("\nBooking History:\n");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            r.display();
        }
    }
}

// Reporting Service (read-only analysis)
class BookingReportService {

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\nBooking Summary Report:\n");

        if (reservations.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        Map<String, Integer> roomTypeCount = new HashMap<>();

        // Count bookings per room type
        for (Reservation r : reservations) {
            String type = r.getRoomType();
            roomTypeCount.put(type, roomTypeCount.getOrDefault(type, 0) + 1);
        }

        // Display report
        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() +
                    " | Total Bookings: " + entry.getValue());
        }

        System.out.println("\nTotal Reservations: " + reservations.size());
    }
}

// Main Driver
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from Use Case 6)
        Reservation r1 = new Reservation("R101", "Alice", "Single");
        Reservation r2 = new Reservation("R102", "Bob", "Suite");
        Reservation r3 = new Reservation("R103", "Charlie", "Single");

        // Store in history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin views full booking history
        history.displayHistory();

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateSummary(history.getAllReservations());
    }
}
