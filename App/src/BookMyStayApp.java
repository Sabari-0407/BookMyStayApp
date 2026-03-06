/**
 * UseCase1HotelBookingApp
 *
 * This class represents the entry point of the My Stay Hotel Booking Application.
 * It demonstrates how a Java application starts execution using the main() method
 * and prints a welcome message to the console.
 *
 * The purpose of this use case is to establish a predictable startup flow
 * for the application and introduce basic Java concepts such as classes,
 * static methods, and console output.
 *
 * @author Sabarinathan M
 * @version 1.0
 */
public class BookMyStayApp {

    /**
     * Main method – entry point of the Java application.
     * The JVM invokes this method when the program starts.
     *
     * @param args Command-line arguments passed during program execution
     */
    public static void main(String[] args) {

        // Display application welcome message
        System.out.println("====================================");
        System.out.println("      Welcome to My Stay App");
        System.out.println("      Hotel Booking System v1.0");
        System.out.println("====================================");

        // Inform user that the application started successfully
        System.out.println("Application started successfully.");

        // End message
        System.out.println("Thank you for using My Stay App!");
    }
}