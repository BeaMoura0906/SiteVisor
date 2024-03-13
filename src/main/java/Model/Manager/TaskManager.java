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

public class TaskManager {

    private Connection connection;

    public TaskManager() {
        // Get a connection instance from the Manager class
        this.connection = Manager.getInstance().getConnection();
    }

    public List<Task> getAllTasksBySite(Site site) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT t.id, t.name, t.description, s.id, s.name, c.id, c.name FROM tasks t INNER JOIN subcategories s ON t.subcategory_id = s.id INNER JOIN categories c ON s.category_id = c.id WHERE c.site_id = ? ";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, site.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int taskId = resultSet.getInt("t.id");
                String taskName = resultSet.getString("t.name");
                String taskDescription = resultSet.getString("t.description");
                int subcategoryId = resultSet.getInt("s.id");
                String subcategoryName = resultSet.getString("s.name");
                int categoryId = resultSet.getInt("c.id");
                String categoryName = resultSet.getString("c.name");

                Category category = new Category(categoryId, categoryName, site);

                Subcategory subcategory = new Subcategory(subcategoryId, subcategoryName, category);

                Task task = new Task(taskId, taskName, taskDescription, subcategory);

                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }

    public List<Task> getAllTasksBySubcategory(Subcategory subcategory) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE subcategory_id = ? ";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, subcategory.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String taskName = resultSet.getString("name");
                String taskDescription = resultSet.getString("description");

                Task task = new Task(taskId, taskName, taskDescription, subcategory);

                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }
}
