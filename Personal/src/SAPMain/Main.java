package SAPMain;

import java.util.*;

import Admin.*;
import Student.*;

class SAP {

    // Create instances of AdminMethods and Scanner for user input
    static AdminMethods obj = new AdminMethods();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        // Foreground and background colors for terminal text styling
        final String Back = "\u001B[0m";
        final String Red = "\u001B[31m";
        final String Green = "\033[0;32m";
        final String Blue = "\033[0;34m";
        final String Yellow = "\033[0;33m";

        // Clear the console screen
        System.out.println("\033[H\033[2J");
        System.out.flush();

        // Create instances of Admin and Student
        Admin admin = new Admin();
        Student student = new Student();

        boolean flag = true; // Control flag for the main loop
        int user_input = 0; // User's menu choice
        MainCheck mc = new MainCheck(); // Instance of MainCheck for validation

        while (flag) { // Main loop for user interaction
            // Print the header for the Student Activity Portal
            System.out.println(
                    "\n\n\t\t\t\t\t\t~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~" + Back);
            System.out.println("\033[1;95m" + "\t\t\t\t\t\t\t\t      STUDENT ACTIVITY PORTAL" + Back);
            System.out.println("\t\t\t\t\t\t~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~ ~^.^~");
            try {
                // Display the menu options
                System.out.println(Yellow + "\n\t\t\t\t\t\t     Press [1] if want to Login as Admin." + Back);
                System.out.println(Yellow + "\t\t\t\t\t\t     Press [2] if want to SignUp as Admin." + Back);
                System.out.println(Yellow + "\t\t\t\t\t\t     Press [3] if want to Login as Student." + Back);
                System.out.println(Yellow + "\t\t\t\t\t\t     Press [4] if want to SignUp as Student." + Back);
                System.out.println(Red + "\t\t\t\t\t\t     Press [5] to Exit." + Back);
                System.out.print(Blue + "\n\t\t\t\t\t\t     Enter Your Choice : " + Back);
                user_input = sc.nextInt(); // Get user choice
                sc.nextLine(); // Consume newline character
            } catch (Exception e) {
                // Handle invalid input (non-integer)
                System.out.println(Red + "\n\t\t\t\t\t\t     ***  ENTER INTEGER INPUT  ***" + Back);
                sc.nextLine(); // Clear the buffer
                continue; // Restart the loop
            }

            // Process user input
            switch (user_input) {
                case 1:
                    // Admin login
                    admin.checkAdminLogin();
                    break;
                case 2:
                    // Admin sign-up
                    boolean b1 = true; // Control flag for captcha check
                    boolean emailcheck = true; // Control flag for email validation
                    boolean passcheck = true; // Control flag for password validation
                    String u_email = ""; // Admin email
                    String u_pass = ""; // Admin password

                    // Validate email
                    while (emailcheck) {
                        System.out.print(
                                Blue + "\n\t\t\t\t\t\t     Enter Email-id you want to Add (e.g., xyz12@gmail.com) : "
                                        + Back);
                        u_email = sc.nextLine();
                        if (mc.emailCheck(u_email)) {
                            emailcheck = false; // Exit loop if email is valid
                            break;
                        } else {
                            System.out.println(Red
                                    + "\n\t\t\t\t\t\t     ***  SET VALID EMAIL-ID AT LEAST ONE DIGIT AND ALPHABET  ***");
                        }
                    }

                    // Validate password
                    while (passcheck) {
                        System.out.print(Blue + "\t\t\t\t\t\t     Enter Password you want to Keep : " + Back);
                        u_pass = sc.nextLine();
                        if (mc.passCheck(u_pass)) {
                            passcheck = false; // Exit loop if password is valid
                            break;
                        } else {
                            System.out.println(Red
                                    + "\n\t\t\t\t\t\t     ***  SET VALID PASSWORD WITH AT LEAST ONE DIGIT AND ALPHABET  ***");
                        }
                    }

                    // Check captcha and proceed with admin sign-up
                    while (b1) {
                        if (MainCheck.checkCaptch()) {
                            System.out.println(Green + "\n\t\t\t\t\t\t     ...SIGNUP SUCCESSFUL..." + Back);
                            obj.addAdmin(u_email, u_pass);
                            admin.checkAdminLogin(); // Log in after successful signup
                            b1 = false; // Exit loop
                        } else {
                            System.out.println(Red + "\n\t\t\t\t\t\t     ***  ENTER VALID CAPTCHA  ***" + Back);
                        }
                    }
                    break;
                case 3:
                    // Student login
                    student.checkStudentLogin();
                    break;
                case 4:
                    // Student sign-up
                    boolean b2 = true; // Control flag for captcha check
                    emailcheck = true; // Control flag for email validation
                    passcheck = true; // Control flag for password validation
                    String s_email = ""; // Student email
                    String s_pass = ""; // Student password

                    // Validate email
                    while (emailcheck) {
                        System.out.print(
                                Blue + "\n\t\t\t\t\t\t     Enter Email-id you want to Add (e.g., xyz12@gmail.com) : "
                                        + Back);
                        s_email = sc.nextLine();
                        if (mc.emailCheck(s_email)) {
                            emailcheck = false; // Exit loop if email is valid
                            break;
                        } else {
                            System.out.println(Red
                                    + "\n\t\t\t\t\t\t     ***  SET VALID EMAIL-ID AT LEAST ONE DIGIT AND ALPHABET  ***");
                        }
                    }

                    // Validate password
                    while (passcheck) {
                        System.out.print(Blue + "\t\t\t\t\t\t     Enter Password you want to Keep : " + Back);
                        s_pass = sc.nextLine();
                        if (mc.passCheck(s_pass)) {
                            passcheck = false; // Exit loop if password is valid
                            break;
                        } else {
                            System.out.println(Red
                                    + "\n\t\t\t\t\t\t     ***  SET VALID PASSWORD WITH AT LEAST ONE DIGIT AND ALPHABET  ***");
                            System.out.println();
                        }
                    }

                    // Check captcha and proceed with student sign-up
                    while (b2) {
                        if (student.checkCaptch()) {
                            System.out.println(Green + "\n\t\t\t\t\t\t     ...SIGNUP SUCCESSFUL..." + Back);
                            student.addStudent(s_email, s_pass);
                            student.checkStudentLogin(); // Log in after successful signup
                            b2 = false; // Exit loop
                        } else {
                            System.out.println(Red + "\n\t\t\t\t\t\t\t     ***  ENTER VALID CAPTCHA  ***" + Back);
                        }
                    }
                    break;
                case 5 :
                flag = false;
                System.out.println(Red + "\n\t\t\t\t\t\t\t     ***  EXITING THE CODE  ***" + Back);
                break;
                default:
                    // Handle invalid menu choices
                    System.out.println(Red + "\n\t\t\t\t\t\t     ***  INVALID INPUT  ***" + Back);
                    break;
            }

            // Clear the console screen
            System.out.println("\033[H\033[2J");
            System.out.flush();
        }
    }
}
