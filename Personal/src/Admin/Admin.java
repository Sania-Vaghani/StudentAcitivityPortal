package Admin;

import SAPMain.Connect;
import SAPMain.MThread;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Admin {

    // Instance of AdminMethods to use its methods
    static AdminMethods obj = new AdminMethods();

    // ANSI escape codes for colored console output
    final String back = "\u001B[0m";
    final String red = "\u001B[31m";
    final String green = "\033[0;32m";
    final String blue = "\033[0;34m";
    final String greenBold = "\033[0;92m";
    final String yellow = "\033[0;33m";

    // HashMaps for storing data
    HashMap<String, String> hm = new HashMap<>();
    HashMap<String, String> hm1 = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    // Method to check admin login
    public void checkAdminLogin() throws Exception {
        // Establish database connection
        Connection con = Connect.Check();

        // Prompt user for email and password
        System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Email-id : " + back);
        String user_email = sc.nextLine();
        System.out.print(blue + "\t\t\t\t\t\t     Enter Your Password : " + back);
        String user_pass = sc.nextLine();

        // Read admin credentials from file and store them in a HashMap
        BufferedReader br = new BufferedReader(new FileReader("AdminLogin.txt"));
        String x;
        while ((x = br.readLine()) != null) {
            String[] word = x.split(" ");
            hm.put(word[0], word[1]);
        }
        br.close();

        // Check if the entered credentials are correct
        if (hm.containsKey(user_email) && user_pass.equals(hm.get(user_email))) {
            boolean flag = true;
            int user_entry = 0;
            new MThread(); // Start a new thread for background processing

            // Main menu loop
            while (flag) {
                // Display menu options
                System.out.println(
                        "\n\n\t\t\t\t\t\t-------------------------------------------------------------------");
                System.out.println(
                        "\033[0;35m" + "\t\t\t\t\t\t\t       SELECT YOUR CHOICE FROM THE GIVEN LIST" + back);
                System.out.println(
                        "\t\t\t\t\t\t-------------------------------------------------------------------");

                try {
                    // Display options to the user
                    System.out.println(
                            yellow + "\n\t\t\t\t\t\t     Press [1] to Get List of Students by Branch." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [2] to Get Students Grades List." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [3] for Student's Registration." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [4] to Allot Mentor to Each Student." + back);
                    System.out.println(
                            yellow + "\t\t\t\t\t\t     Press [5] to Get Mentor Alloted to Each Student." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [6] to Get Student Attendence Sheet." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [7] for Placement Allotement Sheet." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [8] to Get List of Hostel Students." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [9] to Get Score Card of Student." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [10] to Update the Student Details." + back);
                    System.out.println(yellow + "\t\t\t\t\t\t     Press [11] to Delete the Student Details." + back);
                    System.out.println(red + "\t\t\t\t\t\t     Press [12] to Exit." + back);
                    System.out.print(blue + "\033[0;34m" + "\n\t\t\t\t\t\t     Enter Your Choice : " + back);
                    user_entry = sc.nextInt();
                } catch (Exception e) {
                    // Handle invalid input
                    System.out.println(red + "\n\t\t\t\t\t\t     ***  ENTER INTEGER INPUT  ***" + back);
                    sc.nextLine(); // Clear the buffer
                    continue;
                }

                // Handle user choices
                switch (user_entry) {
                    case 1:
                        // Display all student details
                        obj.displayAllDetails();
                        break;
                    case 2:
                        // Retrieve and display student grades
                        String sql1 = "SELECT * FROM result";
                        PreparedStatement pst1 = con.prepareStatement(sql1);
                        ResultSet rs1 = pst1.executeQuery();
                        System.out.println(
                                "\n\t\t\t\t\t\t ------------------------------------------------");
                        System.out.println(greenBold + "\t\t\t\t\t\t     ROLLNO\tNAME\t\t  BRANCH  GRADE" + back);
                        System.out.println(
                                "\t\t\t\t\t\t ------------------------------------------------");
                        while (rs1.next()) {
                            System.out.println("\t\t\t\t\t\t     " + rs1.getInt(1) + "\t\t" + rs1.getString(2)
                                    + "\t  " + rs1.getString(3) + "\t   " + rs1.getString(4));
                        }
                        break;
                    case 3:
                        // Register a new student
                        boolean f = true;
                        sc.nextLine(); // Clear buffer
                        String u_name = "";

                        // Input and validate student name
                        while (f) {
                            System.out.print(blue
                                    + "\n\t\t\t\t\t\t     Enter Student's Name (FORMAT :: Firstname_Lastname) : "
                                    + back);
                            u_name = sc.nextLine();
                            if (obj.checkName(u_name)) {
                                System.out.println();
                                f = false;
                                break;
                            }
                        }

                        // Input and validate student image path
                        f = true;
                        String u_image = "";
                        while (f) {
                            System.out.print(blue
                                    + "\t\t\t\t\t\t     Enter Path of Student Image(eg --> D://JAVA LJ//JAVA SWING//s1.png) : "
                                    + back);
                            u_image = sc.nextLine();
                            if (obj.checkImage(u_image)) {
                                System.out.println();
                                f = false;
                                break;
                            }
                            System.out.println();
                        }

                        // Input and validate student birthdate and generate email
                        f = true;
                        String u_email = "";
                        String u_birth = "";
                        while (f) {
                            System.out
                                    .print(blue + "\t\t\t\t\t\t     Enter Student's Birthdate(YYYY-MM-DD) : " + back);
                            u_birth = sc.nextLine();
                            if (obj.validBirthdate(u_birth)) {
                                u_email = obj.generateEmail(u_name, u_birth);
                                System.out.println();
                                f = false;
                                break;
                            }
                            System.out.println();
                        }

                        // Input and validate student mobile number
                        f = true;
                        String u_mob = "";
                        while (f) {
                            System.out.print(blue + "\t\t\t\t\t\t     Enter Student's Mobile Number : " + back);
                            u_mob = sc.nextLine();
                            if (obj.validNumber(u_mob)) {
                                System.out.println();
                                f = false;
                                break;
                            }
                        }

                        // Input and validate student branch
                        f = true;
                        String u_branch = "";
                        while (f) {
                            System.out.print(blue + "\t\t\t\t\t\t     Enter Student's Branch (CE/IT/CST) : " + back);
                            u_branch = sc.nextLine();
                            if (u_branch.equals("CE") || u_branch.equals("CST") || u_branch.equals("IT")) {
                                System.out.println();
                                f = false;
                                break;
                            } else {
                                System.out.println(red + "\n\t\t\t\t\t\t     ***** ENTER VALID BRANCH *****" + back);
                                System.out.println();
                            }
                        }

                        // Input and validate student attendance
                        f = true;
                        String u_attend = "";
                        while (f) {
                            System.out.print(
                                    blue + "\t\t\t\t\t\t     Enter Student's Attendence (P for Present and A for Absent) : "
                                            + back);
                            u_attend = sc.nextLine();
                            if (u_attend.equals("P") || u_attend.equals("A")) {
                                System.out.println();
                                f = false;
                                break;
                            } else {
                                System.out.println(red + "\n\t\t\t\t\t\t     ***** ENTER VALID FORMAT *****" + back);
                                System.out.println();
                            }
                        }

                        // Input and validate student grade
                        f = true;
                        String u_grade = "";
                        while (f) {
                            System.out.print(blue + "\t\t\t\t\t\t     Enter Student's Grade (A/B/C/D) : " + back);
                            u_grade = sc.nextLine();
                            if (u_grade.equals("A") || u_grade.equals("B") || u_grade.equals("C")
                                    || u_grade.equals("D")) {
                                System.out.println();
                                f = false;
                                break;
                            } else {
                                System.out.println(red + "\n\t\t\t\t\t\t     ***** ENTER VALID GRADE *****" + back);
                                System.out.println();
                            }
                        }

                        // Input and validate student hostel status
                        f = true;
                        String hos = "";
                        boolean u_hostel = false;
                        while (f) {
                            System.out.print(blue
                                    + "\t\t\t\t\t\t     Enter Student's Hostel Status(true/false) : " + back);
                            hos = sc.nextLine();
                            if (hos.equalsIgnoreCase("true") || hos.equalsIgnoreCase("false")) {
                                u_hostel = Boolean.parseBoolean(hos);
                                System.out.println();
                                f = false;
                                break;
                            } else {
                                System.out.println(red + "\n\t\t\t\t\t\t     ***** ENTER VALID STATUS *****" + back);
                                System.out.println();
                            }
                        }

                        // Input and validate fee details
                        f = true;
                        boolean fch = true;
                        double u_feepay = 0;
                        double pendfee = 0;
                        while (f) {
                            try {
                                while (fch) {
                                    System.out.print(
                                            blue + "\t\t\t\t\t\t     Enter Fee Paid by the Student (out of 95000) : "
                                                    + back);
                                    u_feepay = sc.nextDouble();
                                    sc.nextLine();
                                    if (u_feepay < 95000) {
                                        pendfee = 95000 - u_feepay;
                                        fch = false;
                                    } else {
                                        System.out.println(red + "\n\t\t\t\t\t\t     ***** ENTER VALID FEE *****");
                                    }
                                }
                                f = false;
                            } catch (Exception e) {
                                // Handle invalid fee input
                                System.out.println(red + "\n\t\t\t\t\t\t     ***** ENTER INTEGER ONLY *****" + back);
                                sc.nextLine(); // Clear the buffer
                            }
                        }

                        // Insert student data into the database
                        File f1 = new File(u_image);
                        FileInputStream fis = new FileInputStream(f1);

                        String q1 = "INSERT INTO student (name,studentPhoto,emailid,mobnum,branch,feePaid,feePending,birthdate) VALUES(?,?,?,?,?,?,?,?)";
                        PreparedStatement pst2 = con.prepareStatement(q1);
                        pst2.setString(1, u_name);
                        pst2.setBinaryStream(2, fis);
                        pst2.setString(3, u_email);
                        pst2.setString(4, u_mob);
                        pst2.setString(5, u_branch);
                        pst2.setDouble(6, u_feepay);
                        pst2.setDouble(7, pendfee);
                        pst2.setString(8, u_birth);
                        pst2.executeUpdate();

                        // Insert student result data
                        String q2 = "INSERT INTO result(name,branch,grade) VALUES(?,?,?)";
                        PreparedStatement pst3 = con.prepareStatement(q2);
                        pst3.setString(1, u_name);
                        pst3.setString(2, u_branch);
                        pst3.setString(3, u_grade);
                        pst3.executeUpdate();

                        // Insert student attendance data
                        String q3 = "INSERT INTO attendence(name,mobnum,branch,attendence) VALUES(?,?,?,?)";
                        PreparedStatement pst4 = con.prepareStatement(q3);
                        pst4.setString(1, u_name);
                        pst4.setString(2, u_mob);
                        pst4.setString(3, u_branch);
                        pst4.setString(4, u_attend);
                        pst4.executeUpdate();

                        // Display all student details after registration
                        obj.displayAllDetails();

                        // Insert hostel data if applicable
                        String q4 = "INSERT INTO hostel(name,mobnum,emailid,hostel) VALUES(?,?,?,?)";
                        PreparedStatement pst5 = con.prepareStatement(q4);
                        pst5.setString(1, u_name);
                        pst5.setString(2, u_mob);
                        pst5.setString(3, u_email);
                        pst5.setBoolean(4, u_hostel);
                        pst5.executeUpdate();
                        break;

                    case 4:
                        // Allot mentors to each student and write to a file
                        BufferedWriter bw = new BufferedWriter(new FileWriter("MentorList.txt"));
                        int row_count = obj.rowCountDatabase(con);
                        Statement st = con.createStatement();
                        String sql13 = "SELECT name from student ";
                        ResultSet rs13 = st.executeQuery(sql13);

                        for (int i = 1; i <= row_count; i++) {
                            while (rs13.next()) {
                                String mentor_alloted = obj.getRandomMentor();
                                hm1.put(rs13.getString(1), mentor_alloted);
                                bw.write((rs13.getString(1) + " --> " + mentor_alloted));
                                bw.newLine();
                            }
                        }
                        bw.close();
                        System.out.println("\n\t\t\t\t\t\t        ~^.^~ Mentor Allotment Completed ~^.^~ ");
                        break;
                    case 5:
                        // Display mentor allotment details from the file
                        System.out.println("\n\t\t\t\t\t\t        ~^.^~ Mentor List ~^.^~ ");
                        BufferedReader br11 = new BufferedReader(new FileReader("MentorList.txt"));
                        String q;
                        System.out.println(
                                "\n\t\t\t\t\t\t---------------------------------------------");
                        System.out.println(greenBold + "\t\t\t\t\t\t     ROLLNO\tNAME\t\t   MENTOR " + back);
                        System.out.println(
                                "\t\t\t\t\t\t---------------------------------------------");
                        int i = 1;
                        while ((q = br11.readLine()) != null) {
                            // Split the line into parts and print details
                            String[] word = q.split(" ");
                            System.out.println("\t\t\t\t\t\t     " + i + "\t\t" + word[0] + "\t   " + word[2]);
                            i++;
                        }
                        break;

                    case 6:
                        // Fetch and display attendance details from the database
                        String sql2 = "SELECT * FROM attendence";
                        PreparedStatement pst9 = con.prepareStatement(sql2);
                        ResultSet rs2 = pst9.executeQuery();
                        System.out.println(
                                "\n\t\t\t\t\t\t-------------------------------------------------------------------");
                        System.out.println(greenBold
                                + "\t\t\t\t\t\t     ROLLNO\tNAME\t\t  MOBILE_NUMBER\t   BRANCH   ATTENDENCE" + back);
                        System.out.println(
                                "\t\t\t\t\t\t-------------------------------------------------------------------");
                        while (rs2.next()) {
                            // Print attendance details for each student
                            System.out.println("\t\t\t\t\t\t     " + rs2.getInt(1) + "\t\t" + rs2.getString(2)
                                    + "\t  " + rs2.getString(3) + "\t   " + rs2.getString(4) + "\t    "
                                    + rs2.getString(5));
                        }
                        break;

                    case 7:
                        // Fetch results and categorize students based on grades
                        String sql6 = "SELECT * FROM result";
                        PreparedStatement pst12 = con.prepareStatement(sql6);
                        ResultSet rs6 = pst12.executeQuery();
                        BufferedWriter bw11 = new BufferedWriter(new FileWriter("Placement_Eligible_Student.txt"));
                        BufferedWriter bw12 = new BufferedWriter(new FileWriter("PractiseBatch_Student.txt"));
                        while (rs6.next()) {
                            String s1 = rs6.getString("grade");
                            // Write to appropriate file based on grade
                            if (s1.equals("A") || s1.equals("B")) {
                                bw11.write(
                                        rs6.getString(1) + " " + rs6.getString(2) + " " + rs6.getString(3) + " " + s1);
                                bw11.newLine();
                            } else if (s1.equals("C") || s1.equals("D")) {
                                bw12.write(
                                        rs6.getString(1) + " " + rs6.getString(2) + " " + rs6.getString(3) + " " + s1);
                                bw12.newLine();
                            }
                        }
                        bw11.close();
                        bw12.close();
                        obj.placementList(); // Call method to handle placement list
                        break;

                    case 8:
                        // Fetch and display hostel details from the database
                        String sql3 = "SELECT * FROM hostel";
                        PreparedStatement pst10 = con.prepareStatement(sql3);
                        ResultSet rs3 = pst10.executeQuery();
                        System.out.println(
                                "\n\t\t\t\t\t\t------------------------------------------------------------------------------------------");
                        System.out.println(
                                greenBold + String.format("\t\t\t\t\t\t     %-10s %-15s %-15s %-30s %-10s", "ROLLNO",
                                        "NAME", "MOBILE_NUMBER", "EMAIL_ID", "HOSTEL") + back);
                        System.out.println(
                                "\t\t\t\t\t\t------------------------------------------------------------------------------------------");
                        while (rs3.next()) {
                            // Print hostel details for each student
                            System.out.println(String.format(
                                    "\t\t\t\t\t\t     %-10d %-15s %-15s %-30s %-10s",
                                    rs3.getInt(1),
                                    rs3.getString(2),
                                    rs3.getString(3),
                                    rs3.getString(4),
                                    rs3.getBoolean(5) ? "Yes" : "No" // Convert boolean to "Yes" or "No"
                            ));
                        }
                        break;

                    case 9:
                        // Call method to display result students
                        StudentInfo.resultStudent();
                        break;

                    case 10:
                        // Update student details
                        boolean b = true;
                        boolean b1 = true;
                        int ch = 0;
                        while (b) {
                            System.out.print(blue +
                                    "\n\t\t\t\t\t\tEnter Student Roll Number of Whom you want to Update the Detail of : "
                                    + back);
                            int roll = sc.nextInt();

                            if (obj.checkRoll(roll)) {
                                b = false;
                                while (b1) {
                                    try {
                                        // Display update options
                                        System.out.println(green
                                                + "\n\n\t\t\t\t\t\t        ~^.^~ SELECT ONE TO UPDATE ~^.^~ " + back);
                                        System.out.println(yellow
                                                + "\n\t\t\t\t\t\t     Press [1] to Update Student's Name." + back);
                                        System.out.println(
                                                yellow + "\t\t\t\t\t\t     Press [2] to Update Student's Mobile Number."
                                                        + back);
                                        System.out.println(yellow
                                                + "\t\t\t\t\t\t     Press [3] to Update Student's Branch." + back);
                                        System.out.println(yellow
                                                + "\t\t\t\t\t\t     Press [4] to Update Student's Paid Fee." + back);
                                        System.out.println(yellow
                                                + "\t\t\t\t\t\t     Press [5] to Update Student's Birthdate." + back);
                                        System.out.println(yellow
                                                + "\t\t\t\t\t\t     Press [6] to Update Student's Grade." + back);
                                        System.out.println(
                                                yellow + "\t\t\t\t\t\t     Press [7] to Update Student's Hostel Status."
                                                        + back);
                                        System.out.println(
                                                yellow + "\t\t\t\t\t\t     Press [8] to Update Student's Attendance."
                                                        + back);
                                        System.out.println(red + "\t\t\t\t\t\t     Press [9] to Exit." + back);
                                        System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Choice : " + back);
                                        ch = sc.nextInt();
                                        sc.nextLine(); // Clear the buffer
                                    } catch (Exception e) {
                                        // Handle invalid input
                                        System.out.println(
                                                red + "\n\t\t\t\t\t\t     ***  ENTER INTEGER INPUT  ***" + back);
                                        sc.nextLine(); // Clear the buffer
                                    }

                                    switch (ch) {
                                        case 1:
                                            // Update student's name
                                            String getName = "{call getName(?,?)}";
                                            CallableStatement call1 = con.prepareCall(getName);
                                            call1.setInt(1, roll);
                                            call1.executeQuery();
                                            String oldName = call1.getString(2);

                                            boolean check = true;
                                            String n_name = "";
                                            while (check) {
                                                System.out.print(blue +
                                                        "\n\t\t\t\t\t\tEnter New Name of Student (FORMAT :: Firstname_Lastname) : "
                                                        + back);
                                                n_name = sc.nextLine();
                                                if (obj.checkName(n_name)) {
                                                    check = false; // Valid name entered
                                                    break;
                                                }
                                            }

                                            // Update name in all relevant tables
                                            String s1 = "UPDATE student SET name = ? WHERE rollno = ?";
                                            PreparedStatement p1 = con.prepareStatement(s1);
                                            p1.setString(1, n_name);
                                            p1.setInt(2, roll);
                                            p1.executeUpdate();

                                            String s2 = "UPDATE result SET name = ? WHERE rollno = ?";
                                            PreparedStatement p2 = con.prepareStatement(s2);
                                            p2.setString(1, n_name);
                                            p2.setInt(2, roll);
                                            p2.executeUpdate();

                                            String s3 = "UPDATE hostel SET name = ? WHERE rollno = ?";
                                            PreparedStatement p3 = con.prepareStatement(s3);
                                            p3.setString(1, n_name);
                                            p3.setInt(2, roll);
                                            p3.executeUpdate();

                                            String s4 = "UPDATE finalresult SET name = ? WHERE rollno = ?";
                                            PreparedStatement p4 = con.prepareStatement(s4);
                                            p4.setString(1, n_name);
                                            p4.setInt(2, roll);
                                            p4.executeUpdate();

                                            String s5 = "UPDATE attendence SET name = ? WHERE rollno = ?";
                                            PreparedStatement p5 = con.prepareStatement(s5);
                                            p5.setString(1, n_name);
                                            p5.setInt(2, roll);
                                            p5.executeUpdate();

                                            obj.displayAllDetails(); // Display all details

                                            // Log the name update
                                            String nameupdate = "INSERT INTO updatelogtable(rollno,attributeUpdated,old_value,new_value,updateDate) VALUES (?,?,?,?,CURRENT_DATE)";
                                            PreparedStatement ps1 = con.prepareStatement(nameupdate);
                                            ps1.setInt(1, roll);
                                            ps1.setString(2, "name");
                                            ps1.setString(3, oldName);
                                            ps1.setString(4, n_name);
                                            ps1.executeUpdate();
                                            break;

                                        case 2:
                                            // Update student's mobile number
                                            String getMob = "{call getMobNum(?,?)}";
                                            CallableStatement call2 = con.prepareCall(getMob);
                                            call2.setInt(1, roll);
                                            call2.executeQuery();
                                            String oldMob = call2.getString(2);

                                            boolean mobcheck = true;
                                            String new_mob = "";

                                            while (mobcheck) {
                                                System.out.print(blue
                                                        + "\t\t\t\t\t\t     Enter New Mobile Number of Student : "
                                                        + back);
                                                new_mob = sc.nextLine();
                                                if (obj.validNumber(new_mob)) {
                                                    mobcheck = false; // Valid mobile number entered
                                                    break;
                                                }
                                            }
                                            // Update mobile number in relevant tables
                                            String s8 = "UPDATE student SET mobnum = ? WHERE rollno = ?";
                                            PreparedStatement p8 = con.prepareStatement(s8);
                                            p8.setString(1, new_mob);
                                            p8.setInt(2, roll);
                                            p8.executeUpdate();

                                            String s9 = "UPDATE hostel SET mobnum = ? WHERE rollno = ?";
                                            PreparedStatement p9 = con.prepareStatement(s9);
                                            p9.setString(1, new_mob);
                                            p9.setInt(2, roll);
                                            p9.executeUpdate();

                                            String s10 = "UPDATE attendence SET mobnum = ? WHERE rollno = ?";
                                            PreparedStatement p10 = con.prepareStatement(s10);
                                            p10.setString(1, new_mob);
                                            p10.setInt(2, roll);
                                            p10.executeUpdate();

                                            System.out.print(
                                                    green + "\n\t\t\t\t\t\t     Mobile Number Updated Successfully "
                                                            + back);

                                            // Log the mobile number update
                                            String mobupdate = "INSERT INTO updatelogtable(rollno,attributeUpdated,old_value,new_value,updateDate) VALUES (?,?,?,?,CURRENT_DATE)";
                                            PreparedStatement ps2 = con.prepareStatement(mobupdate);
                                            ps2.setInt(1, roll);
                                            ps2.setString(2, "mobileNumber");
                                            ps2.setString(3, oldMob);
                                            ps2.setString(4, new_mob);
                                            ps2.executeUpdate();
                                            break;

                                        case 3:
                                            // Retrieve the current branch of the student
                                            String getBranch = "{call getBranch(?,?)}";
                                            CallableStatement call3 = con.prepareCall(getBranch);
                                            call3.setInt(1, roll);
                                            call3.executeQuery();
                                            String oldBranch = call3.getString(2);

                                            // Prompt user to enter a new branch and validate input
                                            boolean branchcheck = true;
                                            String new_branch = "";
                                            while (branchcheck) {
                                                System.out.print(
                                                        blue + "\t\t\t\t\t\t     Enter Student's New Branch (CE/IT/CST) : "
                                                                + back);
                                                new_branch = sc.nextLine();
                                                if (new_branch.equals("CE") || new_branch.equals("CST")
                                                        || new_branch.equals("IT")) {
                                                    System.out.println();
                                                    branchcheck = false;
                                                    break;
                                                } else {
                                                    System.out.println(
                                                            red + "\n\t\t\t\t\t\t     ***** ENTER VALID BRANCH *****"
                                                                    + back);
                                                    System.out.println();
                                                }
                                            }

                                            // Update the student's branch in the database
                                            String s13 = "{call updateBranch(?,?)}";
                                            CallableStatement c3 = con.prepareCall(s13);
                                            c3.setString(1, new_branch);
                                            c3.setInt(2, roll);
                                            c3.executeUpdate();

                                            // Update the branch in related tables
                                            String s14 = "UPDATE result SET branch = ? WHERE rollno = ?";
                                            PreparedStatement p11 = con.prepareStatement(s14);
                                            p11.setString(1, new_branch);
                                            p11.setInt(2, roll);
                                            p11.executeUpdate();

                                            String s15 = "UPDATE finalresult SET branch = ? WHERE rollno = ?";
                                            PreparedStatement p12 = con.prepareStatement(s15);
                                            p12.setString(1, new_branch);
                                            p12.setInt(2, roll);
                                            p12.executeUpdate();

                                            String s16 = "UPDATE attendence SET branch = ? WHERE rollno = ?";
                                            PreparedStatement p13 = con.prepareStatement(s16);
                                            p13.setString(1, new_branch);
                                            p13.setInt(2, roll);
                                            p13.executeUpdate();

                                            System.out.print(
                                                    green + "\t\t\t\t\t\t     Branch Updated Successfully " + back);

                                            // Log the branch update
                                            String branchupdate = "INSERT INTO updatelogtable(rollno,attributeUpdated,old_value,new_value,updateDate) VALUES (?,?,?,?,CURRENT_DATE)";
                                            PreparedStatement ps3 = con.prepareStatement(branchupdate);
                                            ps3.setInt(1, roll);
                                            ps3.setString(2, "branch");
                                            ps3.setString(3, oldBranch);
                                            ps3.setString(4, new_branch);
                                            ps3.executeUpdate();
                                            break;

                                        case 4:
                                            // Retrieve the current fee status of the student
                                            String fee = "{call selectPendingFee(?,?,?)}";
                                            CallableStatement c11 = con.prepareCall(fee);
                                            c11.setInt(1, roll);
                                            c11.executeQuery();
                                            double feePending = c11.getDouble(2);
                                            int feePaid = c11.getInt(3);

                                            double new_pend = 0;
                                            double new_paid = 0;
                                            boolean feecheck = true;

                                            // Check if the fee has already been fully paid
                                            if (feePaid < 95000) {
                                                while (feecheck) {
                                                    try {
                                                        System.out.print(blue
                                                                + "\n\t\t\t\t\t\t     Enter Fee Paid by the student : "
                                                                + back);
                                                        new_paid = sc.nextDouble();
                                                        if (new_paid <= feePending) {
                                                            feecheck = false;
                                                            new_pend = feePending - new_paid;
                                                            new_paid = feePaid + new_paid;
                                                            break;
                                                        } else {
                                                            System.out.println(
                                                                    red + "\n\t\t\t\t\t\t\t   ***  ENTER VALID FEE   ***"
                                                                            + back);
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println(
                                                                red + "\n\t\t\t\t\t\t\t   ***  ENTER NUMERIC VALUE ONLY   ***"
                                                                        + back);
                                                        sc.nextLine(); // Clear the buffer
                                                    }
                                                }

                                                // Update fee details in the database
                                                String s12 = "{call updateFeepay(?,?,?)}";
                                                CallableStatement c2 = con.prepareCall(s12);
                                                c2.setDouble(1, new_paid);
                                                c2.setDouble(2, new_pend);
                                                c2.setInt(3, roll);
                                                c2.executeUpdate();

                                                System.out.print(
                                                        green + "\n\t\t\t\t\t\t     Fee Updated Successfully " + back);

                                                // Log the fee updates
                                                String feeupdate = "INSERT INTO updatelogtable(rollno,attributeUpdated,old_value,new_value,updateDate) VALUES (?,?,?,?,CURRENT_DATE)";
                                                PreparedStatement ps4 = con.prepareStatement(feeupdate);
                                                ps4.setInt(1, roll);
                                                ps4.setString(2, "feePending");
                                                ps4.setDouble(3, feePending);
                                                ps4.setDouble(4, new_pend);
                                                ps4.executeUpdate();

                                                String fee2update = "INSERT INTO updatelogtable(rollno,attributeUpdated,old_value,new_value,updateDate) VALUES (?,?,?,?,CURRENT_DATE)";
                                                PreparedStatement ps5 = con.prepareStatement(fee2update);
                                                ps5.setInt(1, roll);
                                                ps5.setString(2, "feePaid");
                                                ps5.setDouble(3, feePaid);
                                                ps5.setDouble(4, new_paid);
                                                ps5.executeUpdate();
                                            } else {
                                                System.out.println(
                                                        green + "\n\t\t\t\t\t\t\t   ***  FEE ALREADY PAID   ***"
                                                                + back);
                                            }
                                            break;

                                        case 5:
                                            // Retrieve the current birthdate of the student
                                            String getBirth = "{call getBirth(?,?)}";
                                            CallableStatement call4 = con.prepareCall(getBirth);
                                            call4.setInt(1, roll);
                                            call4.executeQuery();
                                            String oldBirth = call4.getString(2);

                                            // Prompt user to enter a new birthdate and validate input
                                            boolean birthcheck = true;
                                            String new_birth = "";
                                            while (birthcheck) {
                                                System.out.print(blue
                                                        + "\t\t\t\t\t\t     Enter New Birthdate of Student (YYYY-MM-DD) : "
                                                        + back);
                                                new_birth = sc.nextLine();
                                                if (obj.validBirthdate(new_birth)) {
                                                    System.out.println();
                                                    birthcheck = false;
                                                    break;
                                                }
                                            }

                                            // Update the student's birthdate in the database
                                            String s11 = "{call updateBirthdate(?,?)}";
                                            CallableStatement c1 = con.prepareCall(s11);
                                            c1.setString(1, new_birth);
                                            c1.setInt(2, roll);
                                            c1.executeUpdate();

                                            System.out.print(
                                                    green + "\t\t\t\t\t\t     Birthdate Updated Successfully " + back);

                                            // Log the birthdate update
                                            String birthupdate = "INSERT INTO updatelogtable(rollno,attributeUpdated,old_value,new_value,updateDate) VALUES (?,?,?,?,CURRENT_DATE)";
                                            PreparedStatement ps6 = con.prepareStatement(birthupdate);
                                            ps6.setInt(1, roll);
                                            ps6.setString(2, "birthdate");
                                            ps6.setString(3, oldBirth);
                                            ps6.setString(4, new_birth);
                                            ps6.executeUpdate();
                                            break;

                                        case 6:
                                            // Retrieve the current grade of the student
                                            String getGrade = "{call getGrade(?,?)}";
                                            CallableStatement call5 = con.prepareCall(getGrade);
                                            call5.setInt(1, roll);
                                            call5.executeQuery();
                                            String oldGrade = call5.getString(2);

                                            // Prompt user to enter a new grade and validate input
                                            boolean gradecheck = true;
                                            String new_grade = "";
                                            while (gradecheck) {
                                                System.out.print(blue
                                                        + "\t\t\t\t\t\t     Enter Student's Grade (A/B/C/D) : " + back);
                                                new_grade = sc.nextLine();
                                                if (new_grade.equals("A") || new_grade.equals("B")
                                                        || new_grade.equals("C") || new_grade.equals("D")) {
                                                    System.out.println();
                                                    gradecheck = false;
                                                    break;
                                                } else {
                                                    System.out.println(
                                                            red + "\n\t\t\t\t\t\t     ***** ENTER VALID GRADE *****"
                                                                    + back);
                                                    System.out.println();
                                                }
                                            }

                                            // Update the student's grade in the database
                                            String s17 = "{call updateGrade(?,?)}";
                                            CallableStatement c4 = con.prepareCall(s17);
                                            c4.setString(1, new_grade);
                                            c4.setInt(2, roll);
                                            c4.executeUpdate();

                                            System.out.print(
                                                    green + "\t\t\t\t\t\t     Grade Updated Successfully " + back);

                                            // Log the grade update
                                            String gradeupdate = "INSERT INTO updatelogtable(rollno,attributeUpdated,old_value,new_value,updateDate) VALUES (?,?,?,?,CURRENT_DATE)";
                                            PreparedStatement ps7 = con.prepareStatement(gradeupdate);
                                            ps7.setInt(1, roll);
                                            ps7.setString(2, "grade");
                                            ps7.setString(3, oldGrade);
                                            ps7.setString(4, new_grade);
                                            ps7.executeUpdate();
                                            break;

                                        case 7:
                                            // Retrieve the current hostel status of the student
                                            String getHostel = "{call getHostelStatus(?,?)}";
                                            CallableStatement call6 = con.prepareCall(getHostel);
                                            call6.setInt(1, roll);
                                            call6.executeQuery();
                                            boolean oldHostel = call6.getBoolean(2);

                                            // Prompt user to enter a new hostel status and validate input
                                            boolean hostelcheck = true;
                                            String new_hos = "";
                                            boolean new_h = false;
                                            while (hostelcheck) {
                                                System.out.print(blue
                                                        + "\t\t\t\t\t\t     Enter Student's Hostel Status (true/false) : "
                                                        + back);
                                                new_hos = sc.nextLine();
                                                if (new_hos.equalsIgnoreCase("true")
                                                        || new_hos.equalsIgnoreCase("false")) {
                                                    new_h = Boolean.parseBoolean(new_hos);
                                                    System.out.println();
                                                    hostelcheck = false;
                                                    break;
                                                } else {
                                                    System.out.println(
                                                            red + "\n\t\t\t\t\t\t     ***** ENTER VALID STATUS *****"
                                                                    + back);
                                                    System.out.println();
                                                }
                                            }

                                            // Update the student's hostel status in the database
                                            String s18 = "{call updateHostel(?,?)}";
                                            CallableStatement c5 = con.prepareCall(s18);
                                            c5.setBoolean(1, new_h);
                                            c5.setInt(2, roll);
                                            c5.executeUpdate();

                                            System.out.print(
                                                    green + "\t\t\t\t\t\t     Hostel Status Updated Successfully "
                                                            + back);

                                            // Log the hostel status update
                                            String hosupdate = "INSERT INTO updatelogtable(rollno,attributeUpdated,old_value,new_value,updateDate) VALUES (?,?,?,?,CURRENT_DATE)";
                                            PreparedStatement ps8 = con.prepareStatement(hosupdate);
                                            ps8.setInt(1, roll);
                                            ps8.setString(2, "hostelStatus");
                                            ps8.setBoolean(3, oldHostel);
                                            ps8.setBoolean(4, new_h);
                                            ps8.executeUpdate();
                                            break;

                                        case 8:
                                            // Retrieve the current attendance status of the student
                                            String getAttend = "{call getAttendence(?,?)}";
                                            CallableStatement call7 = con.prepareCall(getAttend);
                                            call7.setInt(1, roll);
                                            call7.executeQuery();
                                            String oldAttend = call7.getString(2);

                                            // Prompt user to enter a new attendance status and validate input
                                            boolean attendcheck = true;
                                            String new_attend = "";
                                            while (attendcheck) {
                                                System.out.print(
                                                        blue + "\t\t\t\t\t\t     Enter Student's Attendance (P for Present and A for Absent) : "
                                                                + back);
                                                new_attend = sc.nextLine();
                                                if (new_attend.equals("P") || new_attend.equals("A")) {
                                                    System.out.println();
                                                    attendcheck = false;
                                                    break;
                                                } else {
                                                    System.out.println(
                                                            red + "\n\t\t\t\t\t\t     ***** ENTER VALID FORMAT *****"
                                                                    + back);
                                                    System.out.println();
                                                }
                                            }

                                            // Update the student's attendance in the database
                                            String s19 = "{call updateAttendence(?,?)}";
                                            CallableStatement c6 = con.prepareCall(s19);
                                            c6.setString(1, new_attend);
                                            c6.setInt(2, roll);
                                            c6.executeUpdate();

                                            System.out.print(
                                                    green + "\t\t\t\t\t\t     Attendance Updated Successfully " + back);

                                            // Log the attendance update
                                            String attendupdate = "INSERT INTO updatelogtable(rollno,attributeUpdated,old_value,new_value,updateDate) VALUES (?,?,?,?,CURRENT_DATE)";
                                            PreparedStatement ps9 = con.prepareStatement(attendupdate);
                                            ps9.setInt(1, roll);
                                            ps9.setString(2, "attendance");
                                            ps9.setString(3, oldAttend);
                                            ps9.setString(4, new_attend);
                                            ps9.executeUpdate();
                                            break;

                                        case 9:
                                            // Exit the loop
                                            b1 = false;
                                            break;

                                        default:
                                            // Handle invalid input
                                            System.out.println(
                                                    red + "\n\t\t\t\t\t\t     *****  INVALID INPUT  *****" + back);
                                            break;
                                    }
                                }
                            } else {
                                // Handle invalid input
                                System.out.println(red + "\n\t\t\t\t\t\t     *****  INVALID ROLL NUMBER  *****" + back);
                            }
                        }
                        break;
                    case 11:
                        boolean del = true; // Flag to control the deletion menu loop
                        int u_ch = 0; // Variable to store user choice
                        while (del) { // Loop until user chooses to exit
                            try {
                                // Display the menu options for deletion
                                System.out.println(
                                        green + "\n\n\t\t\t\t\t\t        ~^.^~ SELECT YOUR CHOICE ~^.^~ " + back);
                                System.out.println(
                                        yellow + "\n\t\t\t\t\t\t     Press [1] to Delete the Student Data." + back);
                                System.out.println(
                                        yellow + "\t\t\t\t\t\t     Press [2] to See the Deleted History." + back);
                                System.out.println(red + "\t\t\t\t\t\t     Press [3] to Exit." + back);
                                System.out.print(blue + "\n\t\t\t\t\t\t     Enter Your Choice : " + back);
                                u_ch = sc.nextInt(); // Read user choice
                                sc.nextLine(); // Consume the newline character

                            } catch (Exception e) { // Handle invalid input
                                System.out.println(red + "\n\t\t\t\t\t\t     ***  ENTER INTEGER INPUT  ***" + back);
                                sc.nextLine(); // Clear the buffer
                            }

                            // Process user choice
                            switch (u_ch) {
                                case 1:
                                    // Call method to delete student data
                                    DeleteStudent.delStudent();
                                    break;
                                case 2:
                                    // Call method to display deleted history
                                    DeleteStudent.displayDeletedHistory();
                                    break;
                                case 3:
                                    // Exit the deletion menu loop
                                    del = false;
                                    break;
                                default:
                                    // Handle invalid choice within the menu
                                    System.out.println(red + "\n\t\t\t\t\t\t     *****  INVALID INPUT  *****" + back);
                                    break;
                            }
                        }
                        break;

                    case 12:
                        // Exit the main loop or program
                        flag = false;
                        break;

                    default:
                        // Handle invalid input
                        System.out.println(red + "\n\t\t\t\t\t\t     *****  INVALID INPUT  *****" + back);
                        break;
                }
            }
        } else {
            // Handle invalid input
            System.out.println(red + "\n\t\t\t\t\t\t     *****  INVALID CREDENTIALS  *****" + back);
        }
    }

}
