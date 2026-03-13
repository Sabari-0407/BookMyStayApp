/**
 * UseCase2RoomInitialization
 *
 * This class demonstrates basic room types for a Hotel Booking application.
 * It introduces object-oriented concepts: abstract classes, inheritance, polymorphism,
 * and encapsulation. Availability is represented using static variables for simplicity.
 *
 * Version: 2.1
 *
 * Author: YourName
 */

abstract class Room {
    // Common attributes
    private int beds;
    private double size; // in square meters
    private double pricePerNight;

    // Constructor
    public Room(int beds, double size, double pricePerNight) {
        this.beds = beds;
        this.size = size;
        this.pricePerNight = pricePerNight;
    }

    // Abstract method to get room type
    public abstract String getRoomType();

    // Concrete method to display room details
    public void displayDetails() {
        System.out.println(getRoomType() + " Room: " + beds + " bed(s), " + size + " sqm, $" + pricePerNight + " per night");
    }
}

// Concrete Room Classes
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 20.0, 75.0);
    }

    @Override
    public String getRoomType() {
        return "Single";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 30.0, 120.0);
    }

    @Override
    public String getRoomType() {
        return "Double";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 50.0, 250.0);
    }

    @Override
    public String getRoomType() {
        return "Suite";
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Hotel Booking System - Room Overview");
        System.out.println("Version: 2.1");
        System.out.println("======================================\n");

        // Static availability
        int availableSingles = 10;
        int availableDoubles = 5;
        int availableSuites = 2;

        // Initialize room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display room details and availability
        singleRoom.displayDetails();
        System.out.println("Available: " + availableSingles + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + availableDoubles + "\n");

        suiteRoom.displayDetails();
        System.out.println("Available: " + availableSuites + "\n");

        System.out.println("======================================");
        System.out.println("End of Room Initialization");
        System.out.println("======================================");
    }
}
