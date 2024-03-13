package Model.Manager;

import Model.Entity.Category;
import Model.Entity.Subcategory;

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

}
