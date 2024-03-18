package Controller;

import Model.Entity.Category;
import Model.Entity.Site;
import Model.Entity.Subcategory;
import Model.Entity.Task;
import Model.Manager.CategoryManager;
import Model.Manager.SiteManager;
import Model.Manager.SubcategoryManager;
import Model.Manager.TaskManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.List;

public class EditController {

    private Site site;
    private SiteManager siteManager = new SiteManager();
    private CategoryManager categoryManager = new CategoryManager();
    private SubcategoryManager subcategoryManager = new SubcategoryManager();
    private TaskManager taskManager = new TaskManager();

    private Integer categoryId;
    private Integer subcategoryId;
    private Integer taskId;

    @FXML
    private ChoiceBox categoryChoiceBox;
    @FXML
    private TextField categoryNameTextField;
    @FXML
    private Button categoryAddBtn;
    @FXML
    private Button categoryModifyBtn;
    @FXML
    private Button categoryDeleteBtn;
    @FXML
    private ChoiceBox subcategoryChoiceBox;
    @FXML
    private ChoiceBox associatedCategoryChoiceBox;
    @FXML
    private TextField subcategoryNameTextField;
    @FXML
    private Button subcategoryAddBtn;
    @FXML
    private Button subcategoryModifyBtn;
    @FXML
    private Button subcategoryDeleteBtn;
    @FXML
    private ChoiceBox taskChoiceBox;
    @FXML
    private ChoiceBox associatedSubcategoryChoiceBox;
    @FXML
    private TextField taskNameTextField;
    @FXML
    private TextField taskDescriptionTextField;
    @FXML
    private Button taskAddBtn;
    @FXML
    private Button taskModifyBtn;
    @FXML
    private Button taskDeleteBtn;

    public EditController(Site site) {
        this.site = site;
    }

    // Ajouter le blocage des mises à jour s'il manque des champs avec pop up warning
    // Ajouter pop up succes en cas de réussite

    @FXML
    public void initialize() {
        setUpCategoryChoiceBox();
        setUpSubcategoryChoiceBox();
        setUpTaskChoiceBox();
    }

    @FXML
    private void onClickCategoryAddBtn() {
        String name = this.categoryNameTextField.getText();

        Category category = new Category(0 ,name, this.site);
        boolean isInserted = this.categoryManager.insertCategory(category);

        if (isInserted) {
            initialize();
            System.out.println("Category created successfully");
        } else {
            System.out.println("Failed to create category");
        }
    }

    @FXML
    private void onClickCategoryModifyBtn() {
        int id = this.categoryId;
        String name = this.categoryNameTextField.getText();

        Category category = new Category(id, name, this.site);
        boolean isUpdated = this.categoryManager.updateCategory(category);

        if (isUpdated) {
            initialize();
            System.out.println("Category updated successfully");
        } else {
            System.out.println("Failed to update category");
        }
    }

    @FXML
    private void onClickCategoryDeleteBtn() {
        int id = this.categoryId;

        boolean isDeleted = this.categoryManager.deleteCategory(id);

        if (isDeleted) {
            initialize();
            System.out.println("Category deleted successfully");
        } else {
            System.out.println("Failed to delete category");
        }
    }

    @FXML
    private void onClickSubcategoryAddBtn() {
        String name = this.subcategoryNameTextField.getText();

        String associatedCategoryChoiceBoxValue = this.associatedCategoryChoiceBox.getSelectionModel().getSelectedItem().toString();
        String[] split = associatedCategoryChoiceBoxValue.split(" - ");
        int associatedCategoryId = Integer.parseInt(split[0]);

        Category associatedCategory = this.categoryManager.getCategoryById(associatedCategoryId);

        Subcategory subcategory = new Subcategory(0, name, associatedCategory);

        boolean isInserted = this.subcategoryManager.insertSubcategory(subcategory);

        if (isInserted) {
            initialize();
            System.out.println("Subcategory created successfully");
        } else {
            System.out.println("Failed to create subcategory");
        }
    }

    @FXML
    private void onClickSubcategoryModifyBtn() {
        int id = this.subcategoryId;
        String name = this.subcategoryNameTextField.getText();

        String associatedCategoryChoiceBoxValue = this.associatedCategoryChoiceBox.getSelectionModel().getSelectedItem().toString();
        String[] split = associatedCategoryChoiceBoxValue.split(" - ");
        int associatedCategoryId = Integer.parseInt(split[0]);

        Category associatedCategory = this.categoryManager.getCategoryById(associatedCategoryId);

        Subcategory subcategory = new Subcategory(id, name, associatedCategory);

        boolean isUpdated = this.subcategoryManager.updateSubcategory(subcategory);

        if (isUpdated) {
            initialize();
            System.out.println("Subcategory updated successfully");
        } else {
            System.out.println("Failed to update subcategory");
        }
    }

    @FXML
    private void onClickSubcategoryDeleteBtn() {
        int id = this.subcategoryId;

        boolean isDeleted = this.subcategoryManager.deleteSubcategory(id);

        if (isDeleted) {
            initialize();
            System.out.println("Subcategory deleted successfully");
        } else {
            System.out.println("Failed to delete subcategory");
        }
    }

    @FXML
    private void onClickTaskAddBtn() {
        String name = this.taskNameTextField.getText();
        String description = this.taskDescriptionTextField.getText();

        String associatedSubcategoryChoiceBoxValue = this.associatedSubcategoryChoiceBox.getSelectionModel().getSelectedItem().toString();
        String[] split = associatedSubcategoryChoiceBoxValue.split(" - ");
        int subcategoryId = Integer.parseInt(split[0]);

        Subcategory associatedSubcategory = this.subcategoryManager.getSubcategoryById(subcategoryId);

        Task task = new Task(0, name, description, associatedSubcategory);

        boolean isInserted = this.taskManager.insertTask(task);

        if (isInserted) {
            initialize();
            System.out.println("Task created successfully");
        } else {
            System.out.println("Failed to create task");
        }
    }

    @FXML
    private void onClickTaskModifyBtn() {
        int id = this.taskId;
        String name = this.taskNameTextField.getText();
        String description = this.taskDescriptionTextField.getText();

        String associatedSubcategoryChoiceBoxValue = this.associatedSubcategoryChoiceBox.getSelectionModel().getSelectedItem().toString();
        String[] split = associatedSubcategoryChoiceBoxValue.split(" - ");
        int subcategoryId = Integer.parseInt(split[0]);

        Subcategory associatedSubcategory = this.subcategoryManager.getSubcategoryById(subcategoryId);

        Task task = new Task(id, name, description, associatedSubcategory);

        boolean isUpdated = this.taskManager.updateTask(task);

        if (isUpdated) {
            initialize();
            System.out.println("Task updated successfully");
        } else {
            System.out.println("Failed to update task");
        }
    }

    @FXML
    private void onClickTaskDeleteBtn() {
        int id = this.taskId;

        boolean isDeleted = this.taskManager.deleteTask(id);

        if (isDeleted) {
            initialize();
            System.out.println("Task deleted successfully");
        } else {
            System.out.println("Failed to delete task");
        }
    }

    private void setUpCategoryChoiceBox() {
        List<Category> categoriesBySite = this.categoryManager.getAllCategoriesBySite(this.site);
        ObservableList<String> categoryItems = FXCollections.observableArrayList();

        for (Category category : categoriesBySite) {
            categoryItems.add(category.getId() + " - " + category.getName());
        }

        associatedCategoryChoiceBox.setItems(categoryItems);

        categoryItems.add(0, "");

        categoryChoiceBox.setItems(categoryItems);

        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals("")) {
                categoryChoiceBox.getSelectionModel().clearSelection();
                this.categoryAddBtn.setDisable(false);
                this.categoryModifyBtn.setDisable(true);
                this.categoryDeleteBtn.setDisable(true);
                this.categoryNameTextField.clear();
            } else {
                String[] split = newValue.toString().split(" - ");
                this.categoryId = Integer.parseInt(split[0]);
                this.categoryAddBtn.setDisable(true);
                this.categoryModifyBtn.setDisable(false);
                this.categoryDeleteBtn.setDisable(false);
                Category selectedCategory = this.categoryManager.getCategoryById(this.categoryId);
                this.categoryNameTextField.setText(selectedCategory.getName());
            }
        });
    }

    private void setUpSubcategoryChoiceBox() {
        List<Subcategory> subcategoriesBySite = this.subcategoryManager.getAllSubcategoriesBySite(this.site);
        ObservableList<String> subcategoryItems = FXCollections.observableArrayList();

        for (Subcategory subcategory : subcategoriesBySite) {
            subcategoryItems.add(subcategory.getId() + " - " + subcategory.getName() + " | Catégorie : " + subcategory.getCategory().getName());
        }

        associatedSubcategoryChoiceBox.setItems(subcategoryItems);

        subcategoryItems.add(0, "");

        subcategoryChoiceBox.setItems(subcategoryItems);

        subcategoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals("")) {
                subcategoryChoiceBox.getSelectionModel().clearSelection();
                this.subcategoryAddBtn.setDisable(false);
                this.subcategoryModifyBtn.setDisable(true);
                this.subcategoryDeleteBtn.setDisable(true);
                this.subcategoryNameTextField.clear();
                this.associatedCategoryChoiceBox.setValue("");
            } else {
                String[] split = newValue.toString().split(" - ");
                this.subcategoryId = Integer.parseInt(split[0]);
                this.subcategoryAddBtn.setDisable(true);
                this.subcategoryModifyBtn.setDisable(false);
                this.subcategoryDeleteBtn.setDisable(false);
                Subcategory selectedSubcategory = this.subcategoryManager.getSubcategoryById(this.subcategoryId);
                this.subcategoryNameTextField.setText(selectedSubcategory.getName());
                this.associatedCategoryChoiceBox.setValue(selectedSubcategory.getCategory().getId() + " - " + selectedSubcategory.getCategory().getName());
            }
        });
    }

    private void setUpTaskChoiceBox() {
        List<Task> tasksBySite = this.taskManager.getAllTasksBySite(this.site);
        ObservableList<String> taskItems = FXCollections.observableArrayList();

        for (Task task : tasksBySite) {
            taskItems.add(task.getId() + " - " + task.getName() + " | Sous-catégorie : " + task.getSubcategory().getName() + " | Categorie : " + task.getSubcategory().getCategory().getName());
        }

        taskItems.add(0, "");

        taskChoiceBox.setItems(taskItems);

        taskChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals("")) {
                taskChoiceBox.getSelectionModel().clearSelection();
                this.taskAddBtn.setDisable(false);
                this.taskModifyBtn.setDisable(true);
                this.taskDeleteBtn.setDisable(true);
                this.taskNameTextField.clear();
                this.taskDescriptionTextField.clear();
                this.associatedSubcategoryChoiceBox.setValue("");
            } else {
                String[] split = newValue.toString().split(" - ");
                this.taskId = Integer.parseInt(split[0]);
                this.taskAddBtn.setDisable(true);
                this.taskModifyBtn.setDisable(false);
                this.taskDeleteBtn.setDisable(false);
                Task selectedTask = this.taskManager.getTaskById(this.taskId);
                this.taskNameTextField.setText(selectedTask.getName());
                this.taskDescriptionTextField.setText(selectedTask.getDescription());
                this.associatedSubcategoryChoiceBox.setValue(selectedTask.getSubcategory().getId() + " - " + selectedTask.getSubcategory().getName() + " | Categorie : " + selectedTask.getSubcategory().getCategory().getName());
            }
        });
    }
}
