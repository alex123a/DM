package db.testing.dm;

import java.sql.*;

public class Main {

    static Connection connection;

    public static void main(String[] args) {
        // Create connection to database
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/dmTest",
                    "postgres",
                    "Blexsoftware3");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert user into database
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO users (name, cpr) VALUES (?, ?)"
            );
            insertStatement.setString(1, "John Doe");
            insertStatement.setString(2, "3333333333");
            insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Queru Database for users with CPR X
        PreparedStatement queryStatement = null;
        try {
            queryStatement = connection.prepareStatement("SELECT * FROM users WHERE cpr = ?");
            queryStatement.setString(1, "3333333333");
            ResultSet queryResultSet = queryStatement.executeQuery();
            System.out.println("The following users matched the query: ");
            while (queryResultSet.next()) {
                System.out.println("The users name was "
                        + queryResultSet.getString("name")
                        + " and his CPR number was "
                        + queryResultSet.getString("cpr")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
