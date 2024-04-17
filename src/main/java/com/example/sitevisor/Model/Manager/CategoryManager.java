package com.example.sitevisor.Model.Manager;

import com.example.sitevisor.Model.Entity.Category;
import com.example.sitevisor.Model.Entity.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class CategoryManager {

    private Connection connection;

    public CategoryManager() {
        // Get a connection instance from the Manager class
        this.connection = Manager.getInstance().getConnection();
    }

    /**
     * Retrieves all categories associated with a given site.
     *
     * @param  site  the site for which to retrieve categories
     * @return       a list of Category objects representing the categories
     */
    public List<Category> getAllCategoriesBySite(Site site) {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE site_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, site.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Category category = new Category(id, name, site);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return the Category object representing the category
     */
    public Category getCategoryById(int id) {
        Category category = null;
        String query = "SELECT * FROM categories WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int categoryId = resultSet.getInt("id");
                String categoryName = resultSet.getString("name");
                category = new Category(categoryId, categoryName, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return category;
    }

    /**
     * Inserts a new category into the database.
     *
     * @param category the category to be inserted
     * @return a boolean value indicating whether the category was inserted successfully
     */
    public boolean insertCategory(Category category) {
        boolean isInserted = false;
        String query = "INSERT INTO categories (name, site_id) VALUES (?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.getName());
            statement.setInt(2, category.getSite().getId());
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
     * Updates an existing category in the database.
     *
     * @param category the category to be updated
     * @return a boolean value indicating whether the category was updated successfully
     */
    public boolean updateCategory(Category category) {
        boolean isUpdated = false;
        String query = "UPDATE categories SET name = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
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
     * Deletes a category from the database.
     *
     * @param categoryId the ID of the category to be deleted
     * @return a boolean value indicating whether the category was deleted successfully
     */
    public boolean deleteCategory(int categoryId) {
        boolean isDeleted = false;
        String query = "DELETE FROM categories WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, categoryId);
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
