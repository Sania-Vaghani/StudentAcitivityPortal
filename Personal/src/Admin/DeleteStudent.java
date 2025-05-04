package Admin;

import java.util.*;
import java.sql.*;

import SAPMain.Connect;

public class DeleteStudent {

    static Scanner sc = new Scanner(System.in);

    // ANSI color codes for text formatting
    final static String Back = "\u001B[0m";
    final static String Red = "\u001B[31m";
    final static String Green = "\033[0;32m";
    final static String Blue = "\033[0;34m";

    // Stack to keep track of deleted students
    static Stack studentStack = new Stack();

    // Method to delete a student from the database
    static void delStudent() throws Exception {
        int rollno;
        boolean flag = true;
        Connection con = Connect.Check(); // Establish database connection

        while (flag) {
            try {
                // Prompt user to enter the roll number of the student to be deleted
                System.out.print(Blue +
                        "\n\t\t\t\t\t\tEnter Roll Number of Student whom You Want to Delete : "
                        + Back);
                rollno = sc.nextInt();

                // Prepare SQL query to check if the student exists
                String s = "SELECT * from student where rollno = ?";
                PreparedStatement ps = con.prepareStatement(s);
                ps.setInt(1, rollno);
                ResultSet rs = ps.executeQuery();

                // If the student exists
                if (rs.isBeforeFirst()) {
                    if (rs.next()) {
                        // Create a Student object with the details
                        if (rollno == rs.getInt("rollno")) {
                            Student ob = new Student(rollno, rs.getString("name"), rs.getString("emailid"),
                                    rs.getString("mobnum"), rs.getString("branch"),
                                    rs.getDouble("feePaid"), rs.getDouble("feePending"), rs.getString("birthdate"));
                            studentStack.push(ob); // Push to stack of deleted students

                            // Prepare and execute SQL queries to delete student from various tables
                            String ds1 = "DELETE from student where rollno = ? ";
                            PreparedStatement ps1 = con.prepareStatement(ds1);
                            ps1.setInt(1, rollno);
                            ps1.executeUpdate();

                            String ds2 = "DELETE from result where rollno = ? ";
                            PreparedStatement ps2 = con.prepareStatement(ds2);
                            ps2.setInt(1, rollno);
                            ps2.executeUpdate();

                            String ds3 = "DELETE from hostel where rollno = ? ";
                            PreparedStatement ps3 = con.prepareStatement(ds3);
                            ps3.setInt(1, rollno);
                            ps3.executeUpdate();

                            String ds4 = "DELETE from finalresult where rollno = ? ";
                            PreparedStatement ps4 = con.prepareStatement(ds4);
                            ps4.setInt(1, rollno);
                            ps4.executeUpdate();

                            String ds5 = "DELETE from attendence where rollno = ? ";
                            PreparedStatement ps5 = con.prepareStatement(ds5);
                            ps5.setInt(1, rollno);
                            ps5.executeUpdate();

                            System.out.println(Green + "\n\t\t\t\t\t\t     Data Deleted Successfully" + Back);
                            flag = false; // Exit loop after successful deletion
                        }
                    }
                } else {
                    // If the roll number does not exist
                    System.out.println(Red + "\n\t\t\t\t\t\t     ***** ROLL NUMBER DOES NOT EXSIST *****" + Back);
                }
            } catch (Exception e) {
                // Handle invalid input
                System.out.println(Red + "\n\t\t\t\t\t\t     ***** ENTER VALID ROLL NUMBER *****" + Back);
                sc.nextLine(); // Clear the buffer
            }
        }
    }

    // Method to display the history of deleted students
    static void displayDeletedHistory() {
        studentStack.display(); // Call the display method of Stack class
    }
}

// Class representing a Student
class Student {
    int rollno;
    String name, emailid, mob, branch, birthdate;
    double feePaid, feePending;

    // Constructor to initialize Student object
    public Student(int rollno, String name, String emailid, String mob, String branch, double feePaid,
            double feePending, String birthdate) {
        this.rollno = rollno;
        this.name = name;
        this.emailid = emailid;
        this.mob = mob;
        this.branch = branch;
        this.birthdate = birthdate;
        this.feePaid = feePaid;
        this.feePending = feePending;
    }
}

// Stack class to manage deleted students
class Stack {
    // Node class representing an element in the stack
    class Node {
        Student s;
        Node next;

        Node(Student s) {
            this.s = s;
            this.next = null;
        }
    }

    Node top = null; // Top of the stack

    // Method to push a Student object onto the stack
    void push(Student s) {
        Node n = new Node(s);
        if (top == null) {
            top = n;
        } else {
            n.next = top;
            top = n;
        }
    }

    // Method to display all students in the stack
    void display() {
        if (top == null) {
            System.out.println("\u001B[31m" + "\n\t\t\t\t\t\t     Empty History." + "\u001B[0m");
        } else {
            Node temp = top;
            while (temp != null) {
                System.out.print("\n\t\t\t\t\t\t     Roll Number := " + temp.s.rollno);
                System.out.print("\n\t\t\t\t\t\t     Name := " + temp.s.name);
                System.out.print("\n\t\t\t\t\t\t     Email-id := " + temp.s.emailid);
                System.out.print("\n\t\t\t\t\t\t     Mobile Number := " + temp.s.mob);
                System.out.print("\n\t\t\t\t\t\t     Branch := " + temp.s.branch);
                System.out.print("\n\t\t\t\t\t\t     Fee Paid := " + temp.s.feePaid);
                System.out.print("\n\t\t\t\t\t\t     Fee Pending := " + temp.s.feePending);
                System.out.print("\n\t\t\t\t\t\t     Birthdate := " + temp.s.birthdate);
                System.out.println();
                temp = temp.next; // Move to the next node in the stack
            }
        }
    }
}
