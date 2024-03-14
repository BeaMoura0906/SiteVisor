package Model.Manager;

import Model.Entity.Category;
import Model.Entity.Site;
import Model.Entity.Subcategory;
import Model.Entity.Task;

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
}
