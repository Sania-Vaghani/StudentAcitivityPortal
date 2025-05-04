package Student;

import SAPMain.Connect;
import SAPMain.MThread;
import java.sql.*;
import java.util.*;
import java.io.*;

public class Student {
    
    // ANSI escape codes for colored output in the terminal
    final String back = "\u001B[0m";
    final String red = "\u001B[31m";
    final String green = "\033[0;32m";
    final String blue = "\033[0;34m";
    final String greenBold = "\033[0;92m";
    final String yellow = "\033[0;33m";

    // HashMap to store email and password pairs for student login
    HashMap<String, String> hm = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    // Method to handle student login and provide menu options
    public void checkStudentLogin() throws Exception {

        // Establish a connection to the database
        Connection con = Connect.Check();

        // Prompt for email and password input
        System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Email-id : " + back);
        String user_email = sc.nextLine();
        System.out.print(blue + "\t\t\t\t\t\t     Enter Your Password : " + back);
        String user_pass = sc.nextLine();

        // Read student login credentials from a file and store them in the HashMap
        BufferedReader br = new BufferedReader(new FileReader("D:\\PROJECT\\INDIVIDUAL\\INDIVIDUAL\\StudentLogin.txt"));
        String x;
        while ((x = br.readLine()) != null) {
            String word[] = x.split(" ");
            hm.put(word[0], word[1]);
        }
        br.close();

        // Check if the provided email and password match the stored credentials
        if (hm.containsKey(user_email) && user_pass.equals(hm.get(user_email))) {
            boolean flag = true;
            int user_entry = 0;
            new MThread(); // Start a new thread for additional functionalities

            // Display menu options and handle user input
            while (flag) {
                System.out.println(
                        "\n\t\t\t\t\t\t-------------------------------------------------------------------");
                System.out.println("\033[0;35m" +
                        "\t\t\t\t\t\t\t       SELECT YOUR CHOICE FROM THE GIVEN LIST" + back);
                System.out.println(
                        "\t\t\t\t\t\t-------------------------------------------------------------------");
                try {
                    System.out.println(yellow + "\n\t\t\t\t\t\t     Press [1] to Get Student Profile." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [2] to See Fee Installments." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [3] to See Status of Hostel Registration." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [4] to Get Student-ID Card." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [5] to Join Coursera and Generate Certificate." + back);
                    System.out.println(red + "\t\t\t\t\t\t     Press [6] to Exit." + back);
                    System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Choice : " + back);
                    user_entry = sc.nextInt();
                } catch (Exception e) {
                    System.out.println(red + "\n\t\t\t\t\t\t     ***  ENTER INTEGER INPUT  ***" + back);
                    sc.nextLine(); // Clear the buffer
                    continue;
                }

                // Handle user menu selection
                switch (user_entry) {
                    case 1:
                        try {
                            // Get student profile details
                            System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Roll Number : " + back);
                            int roll = sc.nextInt();
                            String sql = "SELECT name,emailid,mobnum,branch FROM student WHERE rollno = ?";
                            PreparedStatement pst = con.prepareStatement(sql);
                            pst.setInt(1, roll);
                            ResultSet rs = pst.executeQuery();
                            if (rs.isBeforeFirst()) {
                                while (rs.next()) {
                                    System.out.println("\n\t\t\t\t\t\t     Roll Number : " + roll);
                                    System.out.println("\t\t\t\t\t\t     Name : " + rs.getString("name"));
                                    System.out.println("\t\t\t\t\t\t     Email-id : " + rs.getString("emailid"));
                                    System.out.println("\t\t\t\t\t\t     Mobile Number : " + rs.getString("mobnum"));
                                    System.out.println("\t\t\t\t\t\t     Branch : " + rs.getString("branch"));
                                }
                            } else {
                                System.out.println(red + "\n\t\t\t\t\t\t     ***  ROLL NUMBER DOES NOT EXIST  ***" + back);
                            }
                        } catch (Exception e) {
                            System.out.println(red + "\n\t\t\t\t\t\t     ***  INVALID ROLL NUMBER  ***" + back);
                            sc.nextLine(); // Clear the buffer
                        }
                        break;
                    case 2:
                        try {
                            // Check fee payment status
                            System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Roll Number : " + back);
                            int roll1 = sc.nextInt();
                            String sql1 = "SELECT name,feePaid,feePending,rollno FROM student WHERE rollno = ?";
                            PreparedStatement pst1 = con.prepareStatement(sql1);
                            pst1.setInt(1, roll1);
                            ResultSet rs1 = pst1.executeQuery();
                            if (rs1.isBeforeFirst()) {
                                while (rs1.next()) {
                                    System.out.println("\n\t\t\t\t\t\t     Roll Number  :\t" + roll1);
                                    System.out.println("\t\t\t\t\t\t     Name\t  :\t" + rs1.getString("name"));
                                    System.out.println("\t\t\t\t\t\t     Fee Paid\t  :\t" + (int) rs1.getDouble("feePaid")
                                            + " out of 95000");
                                    System.out.println("\t\t\t\t\t\t     Fee Pending  :\t" + (int) rs1.getDouble("feePending")
                                            + " out of 95000");
                                }
                            } else {
                                System.out.println(red + "\n\t\t\t\t\t\t     ***  ROLL NUMBER DOES NOT EXIST  ***" + back);
                            }
                        } catch (Exception e) {
                            System.out.println(red + "\n\t\t\t\t\t\t     ***  INVALID ROLL NUMBER  ***" + back);
                            sc.nextLine(); // Clear the buffer
                        }
                        break;
                    case 3:
                        try {
                            // Check hostel registration status
                            System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Roll Number : " + back);
                            int roll1 = sc.nextInt();
                            String sql1 = "SELECT name,hostel,rollno FROM hostel WHERE rollno = ?";
                            PreparedStatement pst1 = con.prepareStatement(sql1);
                            pst1.setInt(1, roll1);
                            ResultSet rs1 = pst1.executeQuery();
                            if (rs1.isBeforeFirst()) {
                                while (rs1.next()) {
                                    System.out.println("\n\t\t\t\t\t\t     Roll Number      :\t" + roll1);
                                    System.out.println("\t\t\t\t\t\t     Name\t      :\t" + rs1.getString("name"));
                                    System.out.println("\t\t\t\t\t\t     Hostel Status    :\t"
                                            + (rs1.getBoolean("hostel") ? "Yes" : "No"));
                                }
                            } else {
                                System.out.println(red + "\n\t\t\t\t\t\t     ***  ROLL NUMBER DOES NOT EXIST  ***" + back);
                            }
                        } catch (Exception e) {
                            System.out.println(red + "\n\t\t\t\t\t\t     ***  INVALID ROLL NUMBER  ***" + back);
                            sc.nextLine(); // Clear the buffer
                        }
                        break;
                    case 4:
                        try {
                            // Generate student ID card
                            System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Roll Number : " + back);
                            int rn = sc.nextInt();
                            String sn = "SELECT * FROM student WHERE rollno = ?";
                            PreparedStatement ps = con.prepareStatement(sn);
                            ps.setInt(1, rn);
                            ResultSet rs2 = ps.executeQuery();
                            if (rs2.isBeforeFirst()) {
                                if (rs2.next()) {
                                    StudentIdCard.idCard(rs2); // Call method to generate ID card
                                }
                            } else {
                                System.out.println(red + "\n\t\t\t\t\t\t     ***  ROLL NUMBER NOT FOUND  ***");
                            }
                        } catch (Exception e) {
                            System.out.println(red + "\n\t\t\t\t\t\t     ***  INVALID ROLL NUMBER  ***");
                            sc.nextLine(); // Clear the buffer
                        }
                        break;
                    case 5:
                        try {
                            // Join Coursera and generate certificate
                            System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Roll Number : " + back);
                            int roll1 = sc.nextInt();
                            String s = "SELECT * FROM result WHERE rollno = ?";
                            PreparedStatement pst = con.prepareStatement(s);
                            pst.setInt(1, roll1);
                            ResultSet rs = pst.executeQuery();
                            String s1 = "SELECT rollno FROM finalResult WHERE rollno = ?";
                            PreparedStatement pst2 = con.prepareStatement(s1);
                            pst2.setInt(1, roll1);
                            ResultSet rs1 = pst2.executeQuery();
                            if (!rs1.isBeforeFirst()) {
                                if (rs.next()) {
                                    int roll = rs.getInt(1);
                                    String name = rs.getString(2);
                                    String branch = rs.getString(3);
                                    String grade1 = rs.getString(4);

                                    System.out.println("\033[0;32m"
                                            + "\n\n\t\t\t\t\t\t\t\t\t          ~^.^~ COURSERA EXAM ~^.^~ " + "\u001B[0m");
                                    String grade2 = StudentCertificate.certiResult(name); // Generate certificate
                                    System.out.println("\n\t\t\t\t\t\t     YOUR GRADE := " + grade2);

                                    String sql = "INSERT INTO finalResult VALUES (?,?,?,?)";
                                    PreparedStatement pst1 = con.prepareStatement(sql);
                                    pst1.setInt(1, roll);
                                    pst1.setString(2, name);
                                    pst1.setString(3, branch);
                                    pst1.setString(4, grade1 + grade2);
                                    pst1.executeUpdate();
                                } else {
                                    System.out.println(red + "\n\t\t\t\t\t\t     *****  INVALID ROLL NUMBER  *****" + back);
                                }
                            } else {
                                System.out.println(red +
                                        "\n\t\t\t\t\t\t     *****  YOU HAVE ALREADY APPEARED FOR COURSERA CERTIFICATE  *****" + back);
                            }
                        } catch (Exception e) {
                            System.out.println(red + "\n\t\t\t\t\t\t     *****  INVALID INPUT  *****" + back);
                            sc.nextLine(); // Clear the buffer
                        }
                        break;
                    case 6:
                        // Exit the program
                        flag = false;
                        break;
                    default:
                        System.out.println(red + "\n\t\t\t\t\t\t     *****  INVALID INPUT  *****" + back);
                        break;
                }
            }
        } else {
            System.out.println(red + "\n\t\t\t\t\t\t     *****  INVALID CREDENTIALS  *****" + back);
        }
    }

    // Generate a random character
    char getRandomChar() {
        return (char) ('A' + (int) (Math.random() * 26));
    }

    // Generate a random number
    int getRandomNumber() {
        return (int) (Math.random() * 10);
    }

    // Generate a CAPTCHA string
    String generateCapcha() {
        int num1 = getRandomNumber();
        int num2 = getRandomNumber();
        char c1 = getRandomChar();
        char c2 = getRandomChar();
        char c3 = getRandomChar();
        String captcha = "" + c1 + num1 + c2 + num2 + c3;
        return captcha;
    }

    // Check if the user input matches the generated CAPTCHA
    public boolean checkCaptch() {
        String c = generateCapcha();
        System.out.println(
                "\n\t\t\t\t\t\t\t--------------------------------------------------");
        System.out.println(red + "\t\t\t\t\t\t\t\t  Generated Captcha := " + back + c);
        System.out.println(
                "\t\t\t\t\t\t\t--------------------------------------------------");
        System.out.print(blue + "\t\t\t\t\t\t     Enter Captcha : " + back);
        String s = sc.nextLine();
        return s.equals(c);
    }

    // Add a new student login entry to the file
    public void addStudent(String email, String pass) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\PROJECT\\INDIVIDUAL\\INDIVIDUAL\\StudentLogin.txt", true));
        bw.newLine();
        bw.write(email + " " + pass);
        bw.close();
    }
}
