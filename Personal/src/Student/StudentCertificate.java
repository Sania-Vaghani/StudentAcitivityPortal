package Student;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import java.util.*;

// Represents a single question in the quiz
class Question {

    static Scanner sc = new Scanner(System.in);
    // Console color codes for output
    final String back = "\u001B[0m";
    final String red = "\u001B[31m";
    final String green = "\033[0;32m";
    final String blue = "\033[0;34m";
    final String greenBold = "\033[0;92m";
    final String yellow = "\033[0;33m";

    String ans; // Correct answer for the question
    static int count = 0; // Number of correct answers

    // Constructor to initialize and ask the question
    Question(int n, String que, String a, String b, String c, String ans) {
        this.ans = ans;
        System.out.println("\n\n\t\t\t\t\t\t\t     " + n + ". " + que);
        System.out.println("\n\t\t\t\t\t\t\t        A. " + a);
        System.out.println("\t\t\t\t\t\t\t        B. " + b);
        System.out.println("\t\t\t\t\t\t\t        C. " + c);

        boolean flag = true;
        // Loop to ensure valid input
        while (flag) {
            System.out.print(blue + "\n\t\t\t\t\t\t     Choose Option  : " + back);
            String choice = sc.nextLine();
            // Check if the choice is valid
            if (choice.equals("A") || choice.equals("B") || choice.equals("C")) {
                if (ans.equals(choice)) {
                    count++; // Increment count for correct answer
                }
                flag = false; // Exit loop
            } else {
                System.out.println(red + "\n\t\t\t\t\t\t     ***  INVALID CHOICE  ***" + back);
            }
        }
    }
}

// A linked list to manage a series of questions
class LinkedList {
    class Node {
        Question q; // Question object
        Node next; // Pointer to the next node

        Node(Question q) {
            this.q = q;
            this.next = null;
        }
    }

    Node first = null; // Start of the linked list

    // Method to insert a question at the end of the list
    void insertAtLast(Question q) {
        Node n = new Node(q);
        if (first == null) {
            first = n; // First node in the list
        } else {
            Node temp = first;
            // Traverse to the end of the list
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = n; // Add new node
        }
    }

    // Method to display all questions in the list (currently does nothing)
    void display() {
        if (first == null) {
            System.out.println("Empty Linked List.");
        } else {
            Node temp = first;
            // Traverse through the list (currently no output)
            while (temp != null) {
                temp = temp.next;
            }
        }
    }
}

// Class for generating a student certificate based on quiz results
public class StudentCertificate extends Question {
    StudentCertificate(int n, String que, String a, String b, String c, String ans) {
        super(n, que, a, b, c, ans);
    }

    // Method to handle the quiz questions and generate a certificate
    public static String certiResult(String name) {
        // Creating quiz questions
        Question q1 = new Question(1, "Which of the following is not a primitive data type in Java?", "int", "String",
                "char", "B");
        Question q2 = new Question(2, "Which keyword is used to define a class in Java?", "class", "object", "define",
                "A");
        Question q3 = new Question(3, "Which of the following is a type of database model?", "Hierarchical",
                "Sequential", "Tree-based", "A");
        Question q4 = new Question(4, "In SQL, which command is used to delete a table from the database?",
                "REMOVE TABLE", "DROP TABLE", "DELETE TABLE", "B");
        Question q5 = new Question(5, "Which of the following SQL commands is used to retrieve data from a database?",
                "GET", "FETCH", "SELECT", "C");
        Question q6 = new Question(6,
                "Which data structure is best suited for implementing a FIFO (First-In-First-Out) order?", "Stack",
                "Queue", "Tree", "B");
        Question q7 = new Question(7, "In a binary tree, the number of children a node can have is:", "1", "2", "3",
                "B");
        Question q8 = new Question(8, "Which of the following is not a characteristic of a linked list?",
                "Elements are stored in contiguous memory locations", "Each element points to the next", "Dynamic size",
                "A");
        Question q9 = new Question(9, "Which method is used to start a thread in Java?", "run()", "start()", "begin()",
                "B");
        Question q10 = new Question(10, "Which of the following is the correct way to declare an array in Java?",
                "int[] arr = new int(5);", "int arr[] = new int[5];", "array int[5] = new int[];", "B");

        // Creating a linked list to store questions
        LinkedList l1 = new LinkedList();
        l1.insertAtLast(q1);
        l1.insertAtLast(q2);
        l1.insertAtLast(q3);
        l1.insertAtLast(q4);
        l1.insertAtLast(q5);
        l1.insertAtLast(q6);
        l1.insertAtLast(q7);
        l1.insertAtLast(q8);
        l1.insertAtLast(q9);
        l1.insertAtLast(q10);
        l1.display(); // Display questions (currently does nothing)

        // Generate and return the result based on the quiz performance
        return generatePieChart(name);
    }

    // Method to generate a pie chart based on the number of correct answers
    public static String generatePieChart(String name) {
        JFrame frame = new JFrame("Quiz Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setSize(500, 500);

        // Set icon image for the frame
        ImageIcon image = new ImageIcon("D:\\JAVA LJ\\JAVA SWING\\lj1.png");
        frame.setIconImage(image.getImage());

        // Add a panel to display the pie chart
        frame.add(new PieChartPanel(count, 10 - count, name));
        frame.setVisible(true);

        // Determine grade based on number of correct answers
        if (count >= 8) {
            return "A";
        } else if (count >= 5 && count < 8) {
            return "B";
        } else if (count < 5 && count > 0) {
            return "C";
        }
        return "null";
    }
}

// Panel for drawing a pie chart to visualize quiz results
class PieChartPanel extends JPanel {
    int correctAnswers; // Number of correct answers
    int incorrectAnswers; // Number of incorrect answers
    String name; // Name of the student

    public PieChartPanel(int correct, int incorrect, String name) {
        this.correctAnswers = correct;
        this.incorrectAnswers = incorrect;
        this.name = name;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smooth rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int total = correctAnswers + incorrectAnswers; // Total number of answers
        int correctAngle = (int) ((double) correctAnswers / total * 360); // Angle for correct answers slice
        int incorrectAngle = 360 - correctAngle; // Angle for incorrect answers slice

        // Draw the pie chart slices with gradient colors
        g2d.setPaint(new LinearGradientPaint(50, 50, 350, 350, new float[] { 0.0f, 1.0f },
                new Color[] { Color.GREEN, Color.DARK_GRAY }));
        g2d.fillArc(50, 50, 300, 300, 0, correctAngle);

        g2d.setPaint(new LinearGradientPaint(50, 50, 350, 350, new float[] { 0.0f, 1.0f },
                new Color[] { Color.RED, Color.DARK_GRAY }));
        g2d.fillArc(50, 50, 300, 300, correctAngle, incorrectAngle);

        // Add labels with percentage
        double correctPercentage = (double) correctAnswers / total * 100;
        double incorrectPercentage = (double) incorrectAnswers / total * 100;

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Poppins", Font.BOLD, 14));

        // Label for correct answers
        int correctX = (int) (200 + 100 * Math.cos(Math.toRadians(correctAngle / 2)));
        int correctY = (int) (200 - 100 * Math.sin(Math.toRadians(correctAngle / 2)));
        g2d.drawString("Correct : " + String.format("%.1f", correctPercentage) + "%", correctX, correctY + 20);

        // Label for incorrect answers
        if (Question.count != 10) {
            int incorrectX = (int) (200 + 100 * Math.cos(Math.toRadians(correctAngle + incorrectAngle / 2)));
            int incorrectY = (int) (200 - 100 * Math.sin(Math.toRadians(correctAngle + incorrectAngle / 2)));
            g2d.drawString("Incorrect : " + String.format("%.1f", incorrectPercentage) + "%", incorrectX - 70,
                    incorrectY - 10);
        }

        // Draw a border around the pie chart
        g2d.setColor(Color.BLACK);
        g2d.draw(new Arc2D.Double(50, 50, 300, 300, 0, 360, Arc2D.OPEN));

        // Draw legend
        g2d.setColor(Color.GREEN);
        g2d.fill(new Rectangle2D.Double(370, 80, 20, 20));
        g2d.setColor(Color.RED);
        g2d.fill(new Rectangle2D.Double(370, 110, 20, 20));

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Poppins", Font.ITALIC, 14));
        g2d.drawString("Correct", 400, 95);
        g2d.drawString("Incorrect", 400, 125);

        // Display total correct and incorrect answers below the pie chart
        g2d.setFont(new Font("Poppins", Font.ITALIC, 16));
        g2d.drawString("Correct Answers : " + correctAnswers, 130, 390);
        g2d.drawString("Incorrect Answers : " + incorrectAnswers, 130, 420);

        g2d.setFont(new Font("Poppins", Font.BOLD, 18));
        g2d.drawString("Student Name := " + name, 130, 30);
    }
}
