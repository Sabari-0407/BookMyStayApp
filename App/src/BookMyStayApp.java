import java.util.*;

// Reservation (already confirmed booking)
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
}

// Add-On Service (domain object)
class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " : " + cost);
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service '" + service.getServiceName() +
                "' to Reservation ID: " + reservationId);
    }

    // View services for a reservation
    public void viewServices(String reservationId) {

        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.displayService();
        }
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {

        double total = 0.0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main Driver
public class BookMyStayApp {

    public static void main(String[] args) {

        // Sample confirmed reservations (from Use Case 6)
        Reservation r1 = new Reservation("R101", "Alice", "Single");
        Reservation r2 = new Reservation("R102", "Bob", "Suite");

        // Add-On Service Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Available services
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService spa = new AddOnService("Spa", 1500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800);

        // Guest selects services
        manager.addService(r1.getReservationId(), breakfast);
        manager.addService(r1.getReservationId(), spa);

        manager.addService(r2.getReservationId(), airportPickup);

        // View selected services
        manager.viewServices(r1.getReservationId());
        manager.viewServices(r2.getReservationId());

        // Calculate cost
        System.out.println("\nTotal Add-On Cost for " + r1.getGuestName() + ": " +
                manager.calculateTotalCost(r1.getReservationId()));

        System.out.println("Total Add-On Cost for " + r2.getGuestName() + ": " +
                manager.calculateTotalCost(r2.getReservationId()));
    }
}
