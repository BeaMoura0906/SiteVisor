package Model.Manager;

import Model.Entity.Site;

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
                int userId = resultSet.getInt("user_id");

                Site site = new Site(id, name, type, client, address, startDate, endDate, userId);
                sites.add(site);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sites;
    }
}
