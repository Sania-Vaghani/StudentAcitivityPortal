package SAPMain;

import java.sql.*;

public class Connect {

    // Method to establish a connection to the MySQL database
    public static Connection Check() throws Exception {
        // Database URL, including the protocol, server address, port, and database name
        String dburl = "jdbc:mysql://localhost:3306/student_activity_portol";
        String dbuser = "root";
        String dbpass = "";

        // Establish a connection to the database
        Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);

        // Check if the connection was successful
        if (con != null) {
            // Return the connection object if successful
            return con;
        } else {
            // Return null if the connection could not be established
            return null;
        }
    }
}
