package Controller;

import Model.Entity.Category;
import Model.Entity.Site;
import Model.Entity.Subcategory;
import Model.Entity.Task;
import Model.Manager.CategoryManager;
import Model.Manager.SiteManager;
import Model.Manager.SubcategoryManager;
import Model.Manager.TaskManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SiteController {

    @FXML
    private Label idSiteLabel;
    @FXML
    private Label nameSiteLabel;
    @FXML
    private Label typeSiteLabel;
    @FXML
    private Label clientSiteLabel;
    @FXML
    private Label addressSiteLabel;
    @FXML
    private Label startDateSiteLabel;
    @FXML
    private Label endDateSiteLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ScrollPane tasksPane;

    SiteManager siteManager = new SiteManager();
    CategoryManager categoryManager = new CategoryManager();
    SubcategoryManager subcategoryManager = new SubcategoryManager();
    TaskManager taskManager = new TaskManager();

    @FXML
    public void initialize(Site site) {
        Site updatedSite = this.siteManager.getSiteById(site.getId());

        setSiteLabels(updatedSite);
        updateProgressBar(updatedSite);
        showTasksPane(updatedSite);
    }

    private void setSiteLabels(Site site) {
        idSiteLabel.setText("#" + String.valueOf(site.getId()));
        nameSiteLabel.setText(site.getName());
        typeSiteLabel.setText(site.getType());
        clientSiteLabel.setText(site.getClient());
        addressSiteLabel.setText(site.getAddress());
        startDateSiteLabel.setText(site.getStartDate());
        endDateSiteLabel.setText(site.getEndDate());
    }

    private void updateProgressBar(Site site) {
        LocalDate startLocalDate = LocalDate.parse(site.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocalDate = LocalDate.parse(site.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate currentDate = LocalDate.now();
        long totalDays = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        long elapsedDays = ChronoUnit.DAYS.between(startLocalDate, currentDate);
        double progress = (double) elapsedDays / totalDays;

        progressBar.setProgress(progress);
    }

    private void showTasksPane(Site site) {
        // Create tasks pane
        VBox tasksPaneVBox = new VBox();
        tasksPaneVBox.setPadding(new Insets(10, 10, 10, 10));
        tasksPaneVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
        tasksPaneVBox.prefWidthProperty().bind(tasksPane.widthProperty());

        // Create title for tasks pane
        Label tasksPaneTitle = new Label("Liste des tâches");
        tasksPaneTitle.setStyle("-fx-font-size: 18px;");
        tasksPaneTitle.setPadding(new Insets(10, 10, 10, 10));
        tasksPaneTitle.alignmentProperty().setValue(Pos.TOP_CENTER);
        tasksPaneTitle.prefWidthProperty().bind(tasksPane.widthProperty());
        tasksPaneVBox.getChildren().add(tasksPaneTitle);

        // Create tasks container for tasks pane
        HBox tasksHBox = new HBox();
        tasksHBox.setPadding(new Insets(10, 10, 10, 10));
        tasksHBox.alignmentProperty().setValue(Pos.TOP_CENTER);
        tasksHBox.prefWidthProperty().bind(tasksPane.widthProperty());
        tasksPaneVBox.getChildren().add(tasksHBox);

        // Add tasks pane to scroll pane
        this.tasksPane.setContent(tasksPaneVBox);

        // Add categories to tasks pane
        List<Category> categoriesList = categoryManager.getAllCategoriesBySite(site);

        for (Category category : categoriesList) {
            // Create category container for each category
            VBox categoryVBox = new VBox();
            categoryVBox.setSpacing(10);
            categoryVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
            if( tasksHBox.getChildren().size() == 0) {
                categoryVBox.styleProperty().setValue("-fx-border-color: black; -fx-border-width: 0px 1px; -fx-border-style: solid;");
            } else {
                categoryVBox.styleProperty().setValue("-fx-border-color: black; -fx-border-width: 0px 1px 0px 0px; -fx-border-style: solid;");
            }
            // Create category label for each category container
            Label categoryLabel = new Label(category.getName());
            categoryLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            categoryLabel.setPadding(new Insets(10, 10, 10, 10));
            categoryVBox.getChildren().add(categoryLabel);
            // Create subcategories container for each category container
            HBox subcategoriesHBox = new HBox();
            categoryVBox.getChildren().add(subcategoriesHBox);
            tasksHBox.getChildren().add(categoryVBox);

            // Add subcategories to subcategories container
            List<Subcategory> subcategoriesList = subcategoryManager.getAllSubcategoriesByCategory(category);

            for (Subcategory subcategory : subcategoriesList) {
                // Create subcategory container for each subcategory
                VBox subcategoryVBox = new VBox();
                subcategoryVBox.setSpacing(10);
                subcategoryVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
                if( subcategoriesHBox.getChildren().size() != 0) {
                    subcategoryVBox.styleProperty().setValue("-fx-border-color: black; -fx-border-width: 0px 0px 0px 1px; -fx-border-style: solid;");
                }
                // Create subcategory label for each subcategory container
                Label subcategoryLabel = new Label(subcategory.getName());
                subcategoryLabel.setStyle("-fx-font-size: 14px;");
                subcategoryLabel.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
                subcategoryVBox.getChildren().add(subcategoryLabel);
                subcategoriesHBox.getChildren().add(subcategoryVBox);
                // Create tasks container for each subcategory container
                GridPane tasksGridPane = new GridPane();
                subcategoryVBox.getChildren().add(tasksGridPane);

                // Add tasks to tasks container
                List<Task> tasksList = taskManager.getAllTasksBySubcategory(subcategory);

                for (Task task : tasksList) {
                    // Create task label for each task
                    Label taskLabel = new Label("• " + task.getName() + " : " + task.getDescription());
                    taskLabel.setWrapText(true);
                    taskLabel.setMinWidth(120);
                    taskLabel.setPrefWidth(150);
                    taskLabel.setMaxWidth(200);
                    taskLabel.setPadding(new javafx.geometry.Insets(10, 20, 10, 20));

                    tasksGridPane.addRow(tasksGridPane.getRowCount(), taskLabel);
                }
            }
        }

    }

}
