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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @FXML
    private Map<String, Integer> categoryIdMap = new HashMap<>();
    @FXML
    private Map<String, Integer> subcategoryIdMap = new HashMap<>();
    @FXML
    private Map<String, Integer> taskIdMap = new HashMap<>();

    public EditController(Site site) {
        this.site = site;
    }

    @FXML
    public void initialize() {
        setUpEntitiesChoiceBoxes();
    }

    @FXML
    private void onClickCategoryAddBtn() {

    }

    @FXML
    private void onClickCategoryModifyBtn() {
        int id = this.categoryId;
        String name = this.categoryNameTextField.getText();

        System.out.println("Id : " + id + " Name : " + name);
    }

    @FXML
    private void onClickCategoryDeleteBtn() {

    }

    @FXML
    private void onClickSubcategoryAddBtn() {

    }

    @FXML
    private void onClickSubcategoryModifyBtn() {

    }

    @FXML
    private void onClickSubcategoryDeleteBtn() {

    }

    @FXML
    private void onClickTaskAddBtn() {

    }

    @FXML
    private void onClickTaskModifyBtn() {

    }

    @FXML
    private void onClickTaskDeleteBtn() {

    }

    private void setUpEntitiesChoiceBoxes() {
        List<Category> categoriesBySite = this.categoryManager.getAllCategoriesBySite(this.site);
        List<Subcategory> subcategoriesBySite = this.subcategoryManager.getAllSubcategoriesBySite(this.site);
        List<Task> tasksBySite = this.taskManager.getAllTasksBySite(this.site);

        ObservableList<String> categoryItems = FXCollections.observableArrayList();
        ObservableList<String> subcategoryItems = FXCollections.observableArrayList();
        ObservableList<String> taskItems = FXCollections.observableArrayList();

        for (Category category : categoriesBySite) {
            categoryItems.add(category.getId() + " - " + category.getName());
        }
        for (Subcategory subcategory : subcategoriesBySite) {
            subcategoryItems.add(subcategory.getId() + " - " + subcategory.getName() + " | Catégorie : " + subcategory.getCategory().getName());
        }
        for (Task task : tasksBySite) {
            taskItems.add(task.getId() + " - " + task.getName() + " | Sous-catégorie : " + task.getSubcategory().getName() + " | Categorie : " + task.getSubcategory().getCategory().getName());
        }

        associatedCategoryChoiceBox.setItems(categoryItems);
        associatedSubcategoryChoiceBox.setItems(subcategoryItems);

        categoryItems.add(0, "");
        subcategoryItems.add(0, "");
        taskItems.add(0, "");

        categoryChoiceBox.setItems(categoryItems);
        subcategoryChoiceBox.setItems(subcategoryItems);
        taskChoiceBox.setItems(taskItems);

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

        subcategoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals("")) {
                subcategoryChoiceBox.getSelectionModel().clearSelection();
                this.subcategoryAddBtn.setDisable(false);
                this.subcategoryModifyBtn.setDisable(true);
                this.subcategoryDeleteBtn.setDisable(true);
                this.subcategoryNameTextField.clear();
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

        taskChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals("")) {
                taskChoiceBox.getSelectionModel().clearSelection();
                this.taskAddBtn.setDisable(false);
                this.taskModifyBtn.setDisable(true);
                this.taskDeleteBtn.setDisable(true);
                this.taskNameTextField.clear();
                this.taskDescriptionTextField.clear();
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
