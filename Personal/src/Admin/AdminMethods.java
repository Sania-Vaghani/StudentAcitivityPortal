package Admin;

import java.sql.*;
import java.util.*;

import SAPMain.Connect;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AdminMethods {

    static Scanner sc = new Scanner(System.in);

    // ANSI color codes for text formatting
    final String back = "\u001B[0m";
    final String red = "\u001B[31m";
    final String green = "\033[0;32m";
    final String blue = "\033[0;34m";
    final String greenBold = "\033[0;92m";
    final String yellow = "\033[0;33m";

    // Method to add a new admin with email and password to a file
    public void addAdmin(String email, String pass) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("AdminLogin.txt", true));
        bw.newLine();
        bw.write(email + " " + pass);
        bw.close();
    }

    // Method to generate an email based on name and birthdate
    String generateEmail(String name, String birth) {
        String[] word = birth.split("-");
        String[] word1 = name.split("_");
        String email = word1[0].toLowerCase() + word[0] + word[1] + "@gmail.com";
        return email;
    }

    // Method to get a random mentor from a predefined list
    String getRandomMentor() {
        ArrayList<String> mentor = new ArrayList<>();
        mentor.add("Krunal_Sir");
        mentor.add("Isha_Mam");
        mentor.add("Chintan_Sir");
        mentor.add("Meghna_Mam");
        int index = (int) (Math.random() * mentor.size());
        return mentor.get(index);
    }

    // Method to validate birthdate format
    boolean validBirthdate(String date) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(date, formater);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println(red + "\n\t\t\t\t\t\t     ********  Invalid Format Enter Valid Format  ********" + back);
            return false;
        }
    }

    // Method to validate mobile number
    boolean validNumber(String mob) {
        boolean b = true;
        if (mob.length() == 10 && (mob.startsWith("9") || mob.startsWith("7") || mob.startsWith("8"))) {
            for (char c : mob.toCharArray()) {
                if (Character.isDigit(c)) {
                    b = true;
                } else {
                    b = false;
                    break;
                }
            }
            if (b) {
                return true;
            } else {
                System.out.println(red + "\n\t\t\t\t\t\t     *****  MOBILE_NUMBER SHOULD BE IN INTEGER  *****" + back);
                return false;
            }
        } else {
            System.out.println(red
                    + "\n\t\t\t\t\t\t     *****  MOBILE_NUMBER LENGTH SHOULD BE OF 10 DIGITS AND START WITH 9,8,7  *****"
                    + back);
            return false;
        }
    }

    // Method to validate the format of the name (should contain underscore
    // separating name and surname)
    boolean checkName(String name) {
        boolean b = false;
        boolean b1 = false;
        for (char c : name.toCharArray()) {
            if (!Character.isDigit(c)) {
                b = true;
                String[] word = name.split("_");
                if (word.length > 1) {
                    b1 = true;
                }
            } else {
                b = false;
                break;
            }
        }
        if (b) {
            if (b1) {
                return true;
            } else {
                System.out.println(
                        red + "\n\t\t\t\t\t\t     *****  NAME AND SURNAME SHOULD BE SEPARATED BY UNDERSCORE( _ )  *****"
                                + back);
                return false;
            }
        } else {
            System.out.println(red + "\n\t\t\t\t\t\t     *****  NAME SHOULD BE IN STRING  *****" + back);
            return false;
        }
    }

    // Method to check if a roll number exists in the database and display details
    // if it does
    boolean checkRoll(int rn) throws Exception {
        String s = "SELECT * from student where rollno = ?";
        Connection con = Connect.Check();
        PreparedStatement pst = con.prepareStatement(s);
        pst.setInt(1, rn);
        ResultSet rs = pst.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                System.out.println("\n\t\t\t\t\t\t     Roll Number : " + rn);
                System.out.println("\t\t\t\t\t\t     Name : " + rs.getString("name"));
                System.out.println("\t\t\t\t\t\t     Email-id : " + rs.getString("emailid"));
                System.out.println("\t\t\t\t\t\t     Mobile Number : " + rs.getString("mobnum"));
                System.out.println("\t\t\t\t\t\t     Branch : " + rs.getString("branch"));
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    // Method to check if an image path is valid and the file exists
    boolean checkImage(String image) {
        boolean b = true;
        String[] word = image.split("//");
        if (word.length < 2) {
            System.out.println(red + "\n\t\t\t\t\t\t     *****  GIVE PROPER PATH WITH // FORMAT  *****" + back);
            b = false;
            return false;
        } else {
            File f = new File(image);
            return f.exists();
        }
    }

    // Method to get the number of rows in the student table of the database
    int rowCountDatabase(Connection con) throws Exception {
        Statement st = con.createStatement();
        String sql12 = "SELECT count(*) from student ";
        ResultSet rs12 = st.executeQuery(sql12);
        rs12.next(); // Move the cursor to the first row of the result set
        int row_count = rs12.getInt(1);
        return row_count;
    }

    // Method to display lists of placement eligible students and students needing
    // practice
    void placementList() throws Exception {
        boolean flag = true;
        int ch = 0;
        while (flag) {
            try {
                // Display menu options for placement list
                System.out.println(
                        yellow + "\n\t\t\t\t\t\t     Press [1] to Get List of Placement Eligible Students." + back);
                System.out.println(
                        yellow + "\t\t\t\t\t\t     Press [2] to Get List of Students Who Need Practice." + back);
                System.out.println(yellow + "\t\t\t\t\t\t     Press [3] to Exit." + back);
                System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Choice : " + back);
                ch = sc.nextInt();
            } catch (Exception e) {
                System.out.println(red + "\n\t\t\t\t\t\t     ***  ENTER INTEGER INPUT  ***" + back);
                sc.nextLine(); // Clear the buffer
            }

            switch (ch) {
                case 1:
                    // Display list of placement eligible students
                    BufferedReader br = new BufferedReader(new FileReader("Placement_Eligible_Student.txt"));
                    String s1;
                    System.out.println("\n\t\t\t\t\t\t----------------------------------------------");
                    System.out.println(greenBold + "\t\t\t\t\t\t     ROLLNO\tNAME\t\t  BRANCH  GRADE" + back);
                    System.out.println("\t\t\t\t\t\t------------------------------------------------");
                    while ((s1 = br.readLine()) != null) {
                        String[] word = s1.split(" ");
                        System.out.println(
                                "\t\t\t\t\t\t     " + word[0] + "\t\t" + word[1] + "\t  " + word[2] + "\t  " + word[3]);
                    }
                    br.close(); // Close BufferedReader
                    break;
                case 2:
                    // Display list of students needing practice
                    BufferedReader br1 = new BufferedReader(new FileReader("PractiseBatch_Student.txt"));
                    String s2;
                    System.out.println("\n\t\t\t\t\t\t-----------------------------------------------");
                    System.out.println(greenBold + "\t\t\t\t\t\t     ROLLNO\tNAME\t\tBRANCH\tGRADE" + back);
                    System.out.println("\t\t\t\t\t\t-----------------------------------------------");
                    while ((s2 = br1.readLine()) != null) {
                        String[] word = s2.split(" ");
                        System.out.println(
                                "\t\t\t\t\t\t     " + word[0] + "\t\t" + word[1] + "\t" + word[2] + "\t" + word[3]);
                    }
                    br1.close(); // Close BufferedReader
                    break;
                case 3:
                    // Exit the placement list menu
                    flag = false;
                    break;
                default:
                    System.out.println(red + "\n\t\t\t\t\t\t     *****  INVALID INPUT  *****" + back);
                    break;
            }
        }
    }

    

    // Method to display all student details from the database
    void displayAllDetails() throws Exception {
        Connection con = Connect.Check();
        String sql = "SELECT * from student";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        System.out.println(
                "\n\t\t\t\t\t----------------------------------------------------------------------------------------------------------------------------");
        System.out.println(greenBold
                + String.format("\t\t\t\t\t   %-10s %-15s %-30s %-15s %-10s %-10s %-10s     %-10s", "ROLLNO", "NAME",
                        "EMAIL-ID", "MOBILE_NUMBER", "BRANCH", "FEE_PAID", "FEE_PENDING", "BIRTHDATE")
                + back);
        System.out.println(
                "\t\t\t\t\t----------------------------------------------------------------------------------------------------------------------------");

        while (rs.next()) {
            System.out.println(String.format(
                    "\t\t\t\t\t   %-10d %-15s %-30s %-15s %-10s %-10.2f %-10.2f       %-10s",
                    rs.getInt(1),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getDouble(7),
                    rs.getDouble(8),
                    rs.getString(9)));
        }
    }
}
