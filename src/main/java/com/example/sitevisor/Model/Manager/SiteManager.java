package com.example.sitevisor.Model.Manager;

import com.example.sitevisor.Model.Entity.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SiteManager {
    private Connection connection;

    public SiteManager() {
        // Get a connection instance from the Manager class
        this.connection = Manager.getInstance().getConnection();
    }

    /**
     * Retrieve all sites from the database.
     * @return List of Site objects representing all sites in the database.
     */
    public List<Site> getAllSites(){
        List<Site> sites = new ArrayList<>();
        String query = "SELECT * FROM sites";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String client = resultSet.getString("client");
                String address = resultSet.getString("address");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");

                Site site = new Site(id, name, type, client, address, startDate, endDate);
                sites.add(site);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sites;
    }

    /**
     * Retrieve a site from the database based on its ID.
     *
     * @param id The ID of the site to retrieve.
     * @return The Site object representing the site with the given ID.
     */
    public Site getSiteById(int id){
        Site site = null;
        String query = "SELECT * FROM sites WHERE id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String client = resultSet.getString("client");
                String address = resultSet.getString("address");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");

                site = new Site(id, name, type, client, address, startDate, endDate);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return site;
    }

    /**
     * Insert a new site into the database.
     *
     * @param site The site to insert.
     * @return a boolean indicating whether the insert was successful.
     */
    public boolean insertSite(Site site){
        boolean isInserted = false;
        String query = "INSERT INTO sites (name, type, client, address, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, site.getName());
            statement.setString(2, site.getType());
            statement.setString(3, site.getClient());
            statement.setString(4, site.getAddress());
            statement.setString(5, site.getStartDate());
            statement.setString(6, site.getEndDate());

            if (statement.executeUpdate() > 0) {
                isInserted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isInserted;
    }

    /**
     * Update an existing site in the database.
     *
     * @param site The site to update.
     * @return a boolean indicating whether the update was successful.
     */
    public boolean updateSite(Site site){
        boolean isUpdated = false;
        String query = "UPDATE sites SET name=?, type=?, client=?, address=?, start_date=?, end_date=? WHERE id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, site.getName());
            statement.setString(2, site.getType());
            statement.setString(3, site.getClient());
            statement.setString(4, site.getAddress());
            statement.setString(5, site.getStartDate());
            statement.setString(6, site.getEndDate());
            statement.setInt(8, site.getId());

            if (statement.executeUpdate() > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isUpdated;
    }

    /**
     * Delete a site from the database.
     *
     * @param siteId The ID of the site to delete.
     * @return a boolean indicating whether the site was deleted successful.
     */
    public boolean deleteSite(int siteId){
        boolean isDeleted = false;
        String query = "DELETE FROM sites WHERE id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, siteId);

            if (statement.executeUpdate() > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isDeleted;
    }
}
