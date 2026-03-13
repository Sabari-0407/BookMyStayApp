/**
        * UseCase3InventorySetup
        *
        * Demonstrates centralized room inventory management using a HashMap.
        * Room availability is managed in a single source of truth with
        * controlled methods for retrieval and updates.
        *
        * Version: 3.1
        * Author: YourName
        */

import java.util.HashMap;
import java.util.Map;

class RoomInventory {
    private Map<String, Integer> availability;

    // Constructor initializes inventory with given room types and counts
    public RoomInventory(Map<String, Integer> initialInventory) {
        availability = new HashMap<>(initialInventory);
    }

    // Get availability for a given room type
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    // Update availability for a given room type (positive or negative delta)
    public boolean updateAvailability(String roomType, int delta) {
        int current = availability.getOrDefault(roomType, 0);
        int updated = current + delta;
        if (updated < 0) {
            // Cannot have negative availability
            return false;
        }
        availability.put(roomType, updated);
        return true;
    }

    // Display current inventory state
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("Hotel Booking System - Inventory Setup");
        System.out.println("Version: 3.1");
        System.out.println("======================================\n");

        // Initialize inventory with room types and counts
        Map<String, Integer> initialInventory = new HashMap<>();
        initialInventory.put("Single", 10);
        initialInventory.put("Double", 5);
        initialInventory.put("Suite", 2);

        RoomInventory inventory = new RoomInventory(initialInventory);

        // Display initial inventory
        inventory.displayInventory();

        System.out.println("\nUpdating inventory...");
        // Book 3 single rooms (reduce availability)
        if (inventory.updateAvailability("Single", -3)) {
            System.out.println("3 Single rooms booked.");
        } else {
            System.out.println("Failed to book Single rooms.");
        }

        // Add 1 Suite room (increase availability)
        if (inventory.updateAvailability("Suite", 1)) {
            System.out.println("1 Suite room added.");
        } else {
            System.out.println("Failed to update Suite rooms.");
        }

        // Attempt to book more Double rooms than available
        if (!inventory.updateAvailability("Double", -10)) {
            System.out.println("Cannot book 10 Double rooms: Not enough availability.");
        }

        // Display updated inventory
        System.out.println();
        inventory.displayInventory();

        System.out.println("\n======================================");
        System.out.println("End of Inventory Management Demo");
        System.out.println("======================================");
    }
}