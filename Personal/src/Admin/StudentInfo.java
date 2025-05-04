package Admin;

import javax.swing.*;

import SAPMain.Connect;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentInfo {

    // Method to create and display the student result window
    static void resultStudent() throws Exception {

        // Create a new JFrame container for the window
        JFrame frame = new JFrame("Student Result");
        frame.setLayout(new FlowLayout()); // Use FlowLayout for simple component arrangement

        // Create and add a label and text field for roll number input
        JLabel rollNoLabel = new JLabel("Roll No : ");
        JTextField rollNoField = new JTextField(15); // Text field for roll number with width of 15 columns
        frame.add(rollNoLabel);
        frame.add(rollNoField);

        // Create and add a button to show the student result
        JButton showButton = new JButton("Show Result");
        frame.add(showButton);

        // Set an icon image for the window
        ImageIcon image = new ImageIcon("D:\\JAVA LJ\\JAVA SWING\\lj1.png");
        frame.setIconImage(image.getImage());

        // Set the frame size, close operation, and position on screen
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Establish a connection to the database
        Connection con = Connect.Check();

        // Add an ActionListener to the button
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the roll number from the text field
                String rollNo = rollNoField.getText();
                try {
                    // SQL query to get student information based on roll number
                    String sql = "SELECT name, grade, branch FROM finalResult WHERE rollno = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, rollNo);

                    // Execute the query and get the result set
                    ResultSet rs = pst.executeQuery();

                    // Check if the result set contains data
                    if (rs.isBeforeFirst()) {
                        rs.next(); // Move cursor to the first row
                        String name = rs.getString("name");
                        String grade = rs.getString("grade");
                        String branch = rs.getString("branch");

                        // Create and display a dialog with the student information
                        JOptionPane optionPane = new JOptionPane(
                                "Name : " + name + "\n" +
                                        "Grade : " + grade + "\n" +
                                        "Roll No : " + rollNo + "\n" +
                                        "Branch : " + branch,
                                JOptionPane.INFORMATION_MESSAGE);
                        JDialog dialog = optionPane.createDialog(frame, "Student Result");
                        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
                        dialog.setVisible(true);
                    } else {
                        // Show an error message if no record is found
                        JOptionPane.showMessageDialog(frame, "No Record Found as Certification Course is Pending",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    // Handle SQL exceptions and show an error message
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database connection error", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
