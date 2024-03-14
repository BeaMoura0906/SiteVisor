package Model.Manager;

import Model.Entity.Category;
import Model.Entity.Site;

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
}
