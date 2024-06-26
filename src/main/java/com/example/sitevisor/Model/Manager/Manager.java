package com.example.sitevisor.Model.Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manager class to manage database connections. This class is a singleton class that can be accessed using the getInstance() method to obtain connections to the database.
 */
public class Manager {

    /**
     * Private static instance of Manager.
     */
    private static Manager instance;
    /**
     * Private connection to the database.
     */
    private Connection connection;

    /**
     * Private constructor to prevent instantiation from outside.
     */
    private Manager() {
        // Code to initialize the database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/site_visor", "root", "");
        } catch (SQLException ex) {
            // Handle any errors
            System.out.println("SQLException : " +ex.getMessage());
            System.out.println("SQLState : " + ex.getSQLState());
            System.out.println("VendorError : " + ex.getErrorCode());
        }
    }

    /**
     * Method to obtain the singleton instance of Manager.
     * @return Singleton instance of Manager.
     */
    public static synchronized Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    /**
     * Method to get the connection to the database.
     * @return Connection object representing the connection to the database.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Method to close the database connection.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle connection closure errors here
        }
    }
}

