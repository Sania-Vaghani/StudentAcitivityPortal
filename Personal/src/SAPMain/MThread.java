package SAPMain;

public class MThread {

    // Constructor of the MThread class
    public MThread() throws Exception {
        // ANSI escape codes for text formatting
        final String Reset = "\u001B[0m"; // Reset text formatting
        final String Blink = "\u001B[5m"; // Blinking text
        final String ClearLine = "\u001B[2K"; // Clear the current line
        final String Red = "\u001B[31m"; // Red text color

        String msg = "\t\t\t\t\t\t      LOADING...";

        // Time interval between message updates (500 milliseconds)
        long interval = 500; // 500 milliseconds = 0.5 seconds

        System.out.println(); // Print an empty line for spacing

        // Loop to display the loading message with a blinking effect
        for (int i = 0; i < 3; i++) {

            // Print the blinking red message
            System.out.print(Blink + Red + msg + Reset + "\r");

            // Pause for the specified interval
            Thread.sleep(interval);

            // Clear the current line
            System.out.print(ClearLine + "\r");

            // Pause again for the specified interval
            Thread.sleep(interval);
        }

        // Clear the console screen and flush the output
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
}
