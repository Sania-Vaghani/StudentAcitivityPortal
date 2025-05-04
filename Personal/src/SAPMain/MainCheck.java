package SAPMain;

import java.util.*;

public class MainCheck {
    static Scanner sc = new Scanner(System.in);
    final static String back = "\u001B[0m";
    final static String red = "\u001B[31m";
    final static String blue = "\u001B[34m";

    // Method to check if the email is valid
    boolean emailCheck(String email) {
        // Check if the email contains "@" and ends with "gmail.com"
        if (email.contains("@") && email.endsWith("gmail.com")) {
            boolean hasLetter = false;
            boolean hasDigit = false;

            // Iterate over each character in the email string
            for (char c : email.toCharArray()) {
                if (Character.isLetter(c)) {
                    hasLetter = true;
                }
                if (Character.isDigit(c)) {
                    hasDigit = true;
                }
            }

            // Check if the email contains both letters and digits
            if (hasLetter && hasDigit) {
                return true;
            }
        }
        return false;
    }

    // Method to check if the password is valid
    boolean passCheck(String pass) {
        boolean hasLetter = false;
        boolean hasDigit = false;

        // Iterate over each character in the password string
        for (char c : pass.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        // Check if the password contains both letters and digits
        if (hasLetter && hasDigit) {
            return true;
        }

        return false;
    }

    // Method to generate a random character
    static char getRandomChar() {
        return (char) ('A' + (int) (Math.random() * 26));
    }

    // Method to generate a random number
    static int getRandomNumber() {
        return (int) (Math.random() * 10);
    }

    // Method to generate a CAPTCHA string
    static String generateCapcha() {
        int num1 = getRandomNumber();
        int num2 = getRandomNumber();
        char c1 = getRandomChar();
        char c2 = getRandomChar();
        char c3 = getRandomChar();
        String captcha = "" + c1 + num1 + c2 + num2 + c3;
        return captcha;
    }

    // Method to check CAPTCHA entered by user
    public static boolean checkCaptch() {
        
        String c = generateCapcha();
        System.out.println("\n\t\t\t\t\t\t---------------------------------------------");
        System.out.println(red + "\t\t\t\t\t\t\t  Generated Captcha := " + back + c);
        System.out.println("\t\t\t\t\t\t---------------------------------------------");
        System.out.print(blue + "\t\t\t\t\t\t     Enter Captcha : " + back);
        String s = sc.nextLine();
        return s.equals(c); // Return true if CAPTCHA matches, false otherwise
    }
}
