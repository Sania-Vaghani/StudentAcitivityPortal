package Student;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.*;

public class StudentIdCard extends JFrame {

    // Constructor to create the ID card frame using student data from ResultSet
    public StudentIdCard(ResultSet rs) throws Exception {

        // Set the frame icon
        ImageIcon frameIcon = new ImageIcon("D:\\JAVA LJ\\JAVA SWING\\lj1.png"); // Path to your icon image
        setIconImage(frameIcon.getImage());

        setTitle("Student ID Card"); // Title of the frame
        setSize(600, 300); // Set the size of the frame (more rectangular)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        setLocationRelativeTo(null); // Center the frame on the screen

        // Create the main panel with a horizontal box layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS)); // Horizontal layout
        mainPanel.setBackground(Color.WHITE); // Set background color

        // Panel for displaying student photo
        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(Color.WHITE);
        photoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the photo

        // Read student photo from database and save it as a file
        File f = new File("D:\\JAVA LJ\\JAVA SWING\\s1.png"); // File path to save the photo
        FileOutputStream fos = new FileOutputStream(f);

        Blob b = rs.getBlob("studentPhoto"); // Get the photo blob from ResultSet
        byte[] b1 = b.getBytes(1, (int) b.length()); // Convert blob to byte array
        fos.write(b1); // Write byte array to file
        fos.close(); // Close file output stream

        // Load and scale the student photo
        ImageIcon icon = new ImageIcon("D:\\JAVA LJ\\JAVA SWING\\s1.png"); // Load the saved photo
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Scale image to 150x150 pixels
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImg)); // Create label for the photo

        photoPanel.add(imageLabel); // Add photo label to photo panel

        // Panel for displaying student information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1, 10, 10)); // Grid layout with 4 rows and 1 column
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Consistent padding

        // Create labels for student information
        JLabel nameLabel = new JLabel("Name := " + rs.getString("name"), JLabel.LEFT);
        JLabel branchLabel = new JLabel("Branch := " + rs.getString("branch"), JLabel.LEFT);
        JLabel rollLabel = new JLabel("Roll No := " + rs.getString("rollno"), JLabel.LEFT);
        JLabel mobileLabel = new JLabel("Mobile := " + rs.getString("mobnum"), JLabel.LEFT);

        // Apply font styling to labels
        Font font = new Font("Poppins", Font.BOLD, 16);
        nameLabel.setFont(font);
        branchLabel.setFont(font);
        rollLabel.setFont(font);
        mobileLabel.setFont(font);

        // Add labels to the info panel
        infoPanel.add(nameLabel);
        infoPanel.add(branchLabel);
        infoPanel.add(rollLabel);
        infoPanel.add(mobileLabel);

        // Add photo panel and info panel to the main panel
        mainPanel.add(photoPanel);
        mainPanel.add(infoPanel);

        // Add the main panel to the frame
        add(mainPanel);
    }

    // Static method to create and display the ID card window
    static void idCard(ResultSet rs) {
        SwingUtilities.invokeLater(() -> {
            try {
                StudentIdCard idCard = new StudentIdCard(rs); // Create a new ID card
                idCard.setVisible(true); // Make the frame visible
            } catch (Exception e) {
                e.printStackTrace(); // Print any exceptions
            }
        });
    }
}
