package com.example.sitevisor.Model.Manager;

import com.example.sitevisor.Model.Entity.Category;
import com.example.sitevisor.Model.Entity.Site;
import com.example.sitevisor.Model.Entity.Subcategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubcategoryManager {

    private Connection connection;

    public SubcategoryManager() {
        // Get a connection instance from the Manager class
        this.connection = Manager.getInstance().getConnection();
    }

    /**
     * Retrieves all subcategories associated with a given category
     *
     * @param category the category for which to retrieve subcategories
     * @return a list of Subcategory objects representing the subcategories
     */
    public List<Subcategory> getAllSubcategoriesByCategory(Category category) {
        List<Subcategory> subcategories = new ArrayList<>();
        String query = "SELECT * FROM subcategories WHERE category_id = ?;";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, category.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Subcategory subcategory = new Subcategory(id, name, category);
                subcategories.add(subcategory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subcategories;
    }

    /**
     * Retrieves all subcategories associated with a given site
     *
     * @param site the site for which to retrieve subcategories
     * @return a list of Subcategory objects representing the subcategories
     */
    public List<Subcategory> getAllSubcategoriesBySite(Site site) {
        List<Subcategory> subcategories = new ArrayList<>();
        String query = "SELECT s.id, s.name, c.id, c.name FROM subcategories s INNER JOIN categories c ON s.category_id = c.id WHERE c.site_id = ? ";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, site.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int subcategoryId = resultSet.getInt("s.id");
                String subcategoryName = resultSet.getString("s.name");
                int categoryId = resultSet.getInt("c.id");
                String categoryName = resultSet.getString("c.name");

                Category category = new Category(categoryId, categoryName, site);

                Subcategory subcategory = new Subcategory(subcategoryId, subcategoryName, category);

                subcategories.add(subcategory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subcategories;
    }

    /**
     * Retrieves a subcategory by its ID
     *
     * @param id the ID of the subcategory to retrieve
     * @return the Subcategory object representing the subcategory
     */
    public Subcategory getSubcategoryById(int id) {
        Subcategory subcategory = null;
        String query = "SELECT s.id, s.name, c.id, c.name FROM subcategories s INNER JOIN categories c ON s.category_id = c.id WHERE s.id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int subcategoryId = resultSet.getInt("s.id");
                String subcategoryName = resultSet.getString("s.name");
                int categoryId = resultSet.getInt("c.id");
                String categoryName = resultSet.getString("c.name");

                Category category = new Category(categoryId, categoryName, null);

                subcategory = new Subcategory(subcategoryId, subcategoryName, category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subcategory;
    }

    /**
     * Inserts a new subcategory into the database
     *
     * @param subcategory the subcategory to be inserted
     * @return a boolean indicating whether the insertion was successful
     */
    public boolean insertSubcategory(Subcategory subcategory) {
        boolean isInserted = false;
        String query = "INSERT INTO subcategories (name, category_id) VALUES (?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, subcategory.getName());
            statement.setInt(2, subcategory.getCategory().getId());
            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                isInserted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isInserted;
    }

    /**
     * Updates an existing subcategory in the database
     *
     * @param subcategory the subcategory to be updated
     * @return a boolean indicating whether the update was successful
     */
    public boolean updateSubcategory(Subcategory subcategory) {
        boolean isUpdated = false;
        String query = "UPDATE subcategories SET name = ?, category_id = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, subcategory.getName());
            statement.setInt(2, subcategory.getCategory().getId());
            statement.setInt(3, subcategory.getId());
            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isUpdated;
    }

    /**
     * Deletes a subcategory from the database
     *
     * @param subcategoryId the ID of the subcategory to be deleted
     * @return a boolean indicating whether the deletion was successful
     */
    public boolean deleteSubcategory(int subcategoryId) {
        boolean isDeleted = false;
        String query = "DELETE FROM subcategories WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, subcategoryId);
            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isDeleted;
    }
}
