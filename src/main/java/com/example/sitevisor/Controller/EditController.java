package com.example.sitevisor.Controller;

import com.example.sitevisor.Model.Entity.Category;
import com.example.sitevisor.Model.Entity.Site;
import com.example.sitevisor.Model.Entity.Subcategory;
import com.example.sitevisor.Model.Entity.Task;
import com.example.sitevisor.Model.Manager.CategoryManager;
import com.example.sitevisor.Model.Manager.SiteManager;
import com.example.sitevisor.Model.Manager.SubcategoryManager;
import com.example.sitevisor.Model.Manager.TaskManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import com.example.sitevisor.util.LoadPopUp;

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

    @FXML
    public void initialize() {
        setUpCategoryChoiceBox();
        setUpSubcategoryChoiceBox();
        setUpTaskChoiceBox();
    }

    @FXML
    private void onClickCategoryAddBtn() {
        if(this.categoryNameTextField.getText().isEmpty()) {
            boolean success = false;
            String message = "Veuillez renseigner tous les champs !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
            String name = this.categoryNameTextField.getText();

            Category category = new Category(0 ,name, this.site);
            boolean isInserted = this.categoryManager.insertCategory(category);

            if (isInserted) {
                initialize();
                boolean success = true;
                String message = "Catégorie ajoutée avec succès !";
                String title = "SiteVisor | Succès";
                LoadPopUp.loadPopup(success, message, title);
            } else {
                boolean success = false;
                String message = "Impossible d'ajouter la catégorie ! Veuillez réessayer.";
                String title = "SiteVisor | Erreur";
                LoadPopUp.loadPopup(success, message, title);
            }
        }
    }

    @FXML
    private void onClickCategoryModifyBtn() {
        if( this.categoryId == null ||  this.categoryNameTextField.getText().isEmpty()) {
            boolean success = false;
            String message = "Veuillez renseigner tous les champs !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
            int id = this.categoryId;
            String name = this.categoryNameTextField.getText();

            Category category = new Category(id, name, this.site);
            boolean isUpdated = this.categoryManager.updateCategory(category);

            if (isUpdated) {
                initialize();
                boolean success = true;
                String message = "Catégorie mise à jour avec succès !";
                String title = "SiteVisor | Succès";
            } else {
                boolean success = false;
                String message = "Impossible de mettre à jour la catégorie ! Veuillez réessayer.";
                String title = "SiteVisor | Erreur";
                LoadPopUp.loadPopup(success, message, title);
            }
        }
    }

    @FXML
    private void onClickCategoryDeleteBtn() {
        if (this.categoryId == null) {
            boolean success = false;
            String message = "Veuillez selectionner une catégorie !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
            boolean success = false;
            String message = "Voulez-vous vraiment supprimer cette catégorie ?";
            String title = "SiteVisor | Suppression";
            PopupController popupController = LoadPopUp.loadPopup(success, message, title);
            if (popupController.isDeletionConfirmed() ) {
                int id = this.categoryId;

                boolean isDeleted = this.categoryManager.deleteCategory(id);

                if (isDeleted) {
                    initialize();
                    boolean successDeleted = true;
                    String messageDeleted = "Catégorie supprimée avec succès !";
                    String titleDeleted = "SiteVisor | Succès";
                    LoadPopUp.loadPopup(successDeleted, messageDeleted, titleDeleted);
                } else {
                    boolean successDeleted = false;
                    String messageDeleted = "Impossible de supprimer la catégorie ! Veuillez réessayer.";
                    String titleDeleted = "SiteVisor | Erreur";
                    LoadPopUp.loadPopup(successDeleted, messageDeleted, titleDeleted);
                }
            } else {
                initialize();
            }
        }
    }

    @FXML
    private void onClickSubcategoryAddBtn() {
        if(this.subcategoryNameTextField.getText().isEmpty()) {
            boolean success = false;
            String message = "Veuillez renseigner tous les champs !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
            String name = this.subcategoryNameTextField.getText();

            String associatedCategoryChoiceBoxValue = this.associatedCategoryChoiceBox.getSelectionModel().getSelectedItem().toString();
            String[] split = associatedCategoryChoiceBoxValue.split(" - ");
            int associatedCategoryId = Integer.parseInt(split[0]);

            Category associatedCategory = this.categoryManager.getCategoryById(associatedCategoryId);

            Subcategory subcategory = new Subcategory(0, name, associatedCategory);

            boolean isInserted = this.subcategoryManager.insertSubcategory(subcategory);

            if (isInserted) {
                initialize();
                boolean success = true;
                String message = "Sous-catégorie ajoutée avec succès !";
                String title = "SiteVisor | Succès";
                LoadPopUp.loadPopup(success, message, title);
            } else {
                boolean success = false;
                String message = "Impossible d'ajouter la sous-catégorie ! Veuillez réessayer.";
                String title = "SiteVisor | Erreur";
                LoadPopUp.loadPopup(success, message, title);
            }
        }
    }

    @FXML
    private void onClickSubcategoryModifyBtn() {
        if( this.subcategoryId == null ||  this.subcategoryNameTextField.getText().isEmpty()) {
            boolean success = false;
            String message = "Veuillez renseigner tous les champs !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
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
                boolean success = true;
                String message = "Sous-catégorie mise à jour avec succès !";
                String title = "SiteVisor | Succès";
                LoadPopUp.loadPopup(success, message, title);
            } else {
                boolean success = false;
                String message = "Impossible de mettre à jour la sous-catégorie ! Veuillez réessayer.";
                String title = "SiteVisor | Erreur";
                LoadPopUp.loadPopup(success, message, title);
            }
        }
    }

    @FXML
    private void onClickSubcategoryDeleteBtn() {
        if(this.subcategoryId == null) {
            boolean success = false;
            String message = "Veuillez sélectionner une sous-catégorie !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
            boolean success = false;
            String message = "Voulez-vous vraiment supprimer cette sous-catégorie ?";
            String title = "SiteVisor | Suppression";
            PopupController popupController = LoadPopUp.loadPopup(success, message, title);
            if ( popupController.isDeletionConfirmed() ) {
                int id = this.subcategoryId;

                boolean isDeleted = this.subcategoryManager.deleteSubcategory(id);

                if (isDeleted) {
                    initialize();
                    boolean successDeleted = true;
                    String messageDeleted = "Sous-catégorie supprimée avec succès !";
                    String titleDeleted = "SiteVisor | Succès";
                    LoadPopUp.loadPopup(successDeleted, messageDeleted, titleDeleted);
                } else {
                    boolean successDeleted = false;
                    String messageDeleted = "Impossible de supprimer la sous-catégorie ! Veuillez réessayer.";
                    String titleDeleted = "SiteVisor | Erreur";
                    LoadPopUp.loadPopup(successDeleted, messageDeleted, titleDeleted);
                }
            } else {
                initialize();
            }
        }
    }

    @FXML
    private void onClickTaskAddBtn() {
        if (this.taskNameTextField.getText().isEmpty() || this.taskDescriptionTextField.getText().isEmpty()) {
            boolean success = false;
            String message = "Veuillez renseigner tous les champs !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
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
                boolean success = true;
                String message = "Tâche ajoutée avec succès !";
                String title = "SiteVisor | Succès";
                LoadPopUp.loadPopup(success, message, title);
            } else {
                boolean success = false;
                String message = "Impossible d'ajouter la tâche ! Veuillez réessayer.";
                String title = "SiteVisor | Erreur";
                LoadPopUp.loadPopup(success, message, title);
            }
        }
    }

    @FXML
    private void onClickTaskModifyBtn() {
        if(this.taskId == null || this.taskNameTextField.getText().isEmpty() || this.taskDescriptionTextField.getText().isEmpty()) {
            boolean success = false;
            String message = "Veuillez renseigner tous les champs !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
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
                boolean success = true;
                String message = "Tâche mise à jour avec succès !";
                String title = "SiteVisor | Succès";
                LoadPopUp.loadPopup(success, message, title);
            } else {
                boolean success = false;
                String message = "Impossible de mettre à jour la tâche ! Veuillez réessayer.";
                String title = "SiteVisor | Erreur";
                LoadPopUp.loadPopup(success, message, title);
            }
        }
    }

    @FXML
    private void onClickTaskDeleteBtn() {
        if(this.taskId == null) {
            boolean success = false;
            String message = "Veuillez sélectionner une tâche !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
            boolean success = false;
            String message = "Voulez-vous vraiment supprimer cette tâche ?";
            String title = "SiteVisor | Suppression";
            PopupController popupController = LoadPopUp.loadPopup(success, message, title);
            if (popupController.isDeletionConfirmed() ) {
                int id = this.taskId;

                boolean isDeleted = this.taskManager.deleteTask(id);

                if (isDeleted) {
                    initialize();
                    boolean successDeleted = true;
                    String messageDeleted = "Tâche supprimée avec succès !";
                    String titleDeleted = "SiteVisor | Succès";
                    LoadPopUp.loadPopup(successDeleted, messageDeleted, titleDeleted);
                } else {
                    boolean successDeleted = false;
                    String messageDeleted = "Impossible de supprimer la tâche ! Veuillez réessayer.";
                    String titleDeleted = "SiteVisor | Erreur";
                    LoadPopUp.loadPopup(successDeleted, messageDeleted, titleDeleted);
                }
            }
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
