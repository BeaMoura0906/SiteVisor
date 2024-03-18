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

    /**
     * Retrieves all tasks associated with a given site.
     *
     * @param site the site for which to retrieve tasks
     * @return a list of Task objects representing the tasks
     */
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

    /**
     * Retrieves all tasks associated with a given subcategory.
     *
     * @param subcategory the subcategory for which to retrieve tasks
     * @return a list of Task objects representing the tasks
     */
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

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return the Task object representing the task
     */
    public Task getTaskById(int id) {
        Task task = null;
        String query = "SELECT t.id, t.name, t.description, s.id, s.name, c.id, c.name FROM tasks t INNER JOIN subcategories s ON t.subcategory_id = s.id INNER JOIN categories c ON s.category_id = c.id WHERE t.id = ? ";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if( resultSet.next() ) {
                int taskId = resultSet.getInt("t.id");
                String taskName = resultSet.getString("t.name");
                String taskDescription = resultSet.getString("t.description");
                int subcategoryId = resultSet.getInt("s.id");
                String subcategoryName = resultSet.getString("s.name");
                int categoryId = resultSet.getInt("c.id");
                String categoryName = resultSet.getString("c.name");

                Category category = new Category(categoryId, categoryName, null);

                Subcategory subcategory = new Subcategory(subcategoryId, subcategoryName, category);

                task = new Task(taskId, taskName, taskDescription, subcategory);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return task;
    }

    /**
     * Inserts a new task into the database.
     *
     * @param task the task to be inserted
     * @return a boolean value indicating whether the task was inserted successfully
     */
    public boolean insertTask(Task task) {
        boolean isInserted = false;
        String query = "INSERT INTO tasks (name, description, subcategory_id) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getSubcategory().getId());
            statement.executeUpdate();

            if(statement.getUpdateCount() > 0) {
                isInserted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isInserted;
    }

    /**
     * Updates an existing task in the database.
     *
     * @param task the task to be updated
     * @return a boolean value indicating whether the task was updated successfully
     */
    public boolean updateTask(Task task) {
        boolean isUpdated = false;
        String query = "UPDATE tasks SET name = ?, description = ?, subcategory_id = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getSubcategory().getId());
            statement.setInt(4, task.getId());
            statement.executeUpdate();

            if(statement.getUpdateCount() > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isUpdated;
    }

    /**
     * Deletes a task from the database.
     *
     * @param taskId the ID of the task to be deleted
     * @return a boolean value indicating whether the task was deleted successfully
     */
    public boolean deleteTask(int taskId) {
        boolean isDeleted = false;
        String query = "DELETE FROM tasks WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, taskId);
            statement.executeUpdate();

            if(statement.getUpdateCount() > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isDeleted;
    }
}
