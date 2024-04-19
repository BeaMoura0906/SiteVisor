package com.example.sitevisor.Controller;

import com.example.sitevisor.Model.Entity.Category;
import com.example.sitevisor.Model.Entity.Site;
import com.example.sitevisor.Model.Entity.Subcategory;
import com.example.sitevisor.Model.Entity.Task;
import com.example.sitevisor.Model.Manager.CategoryManager;
import com.example.sitevisor.Model.Manager.SiteManager;
import com.example.sitevisor.Model.Manager.SubcategoryManager;
import com.example.sitevisor.Model.Manager.TaskManager;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SiteController {
    /**
     * Properties
     */
    private Site site;

    /**
     * FXML elements
     */
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
    private HBox tasksHBox;
    @FXML
    private Tab editTab;
    @FXML
    private Tab docTab;
    @FXML
    private VBox siteVBox;
    @FXML
    private Button printBtn;

    /**
     * Instances of managers
     */
    SiteManager siteManager = new SiteManager();
    CategoryManager categoryManager = new CategoryManager();
    SubcategoryManager subcategoryManager = new SubcategoryManager();
    TaskManager taskManager = new TaskManager();

    /**
     * SiteController constructor that receives a site as parameter and sets it as the current site in the controller instance.
     *
     * @param site The selected site.
     */
    public SiteController(Site site) {
        this.site = site;
    }

    /**
     * FXML method that initializes the controller. This method calls the onChangeSiteTab FXML method.
     */
    @FXML
    public void initialize() {
        onChangeSiteTab();
    }

    /**
     * FXML method that updates the site labels and progress bar. This method also calls the showTasksPane method to show the tasks for the selected site.
     */
    @FXML
    private void onChangeSiteTab() {
        this.site = this.siteManager.getSiteById(this.site.getId());

        setSiteLabels(this.site);
        updateProgressBar(this.site);
        this.tasksHBox.getChildren().clear();
        showTasksPane(this.site);
    }

    /**
     * FXML method that changes the content when the edit tab is clicked. It loads the edit form view and sets it as the content of the edit tab.
     */
    @FXML
    private void onChangeEditTab() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sitevisor/edit-view.fxml"));
        EditController editController = new EditController(this.site);
        loader.setController(editController);
        try {
            this.editTab.setContent(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editController.initialize();

    }

    /**
     * FXML method that changes the content when the doc tab is clicked. It loads the doc view and sets it as the content of the doc tab.
     */
    @FXML
    private void onChangeDocTab() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sitevisor/doc-view.fxml"));
        DocController docController = new DocController(this.site);
        loader.setController(docController);
        try {
            this.docTab.setContent(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        docController.initialize();
    }

    /**
     * FXML method that prints the screenshot of the site and the organized tasks and saves it as a PDF file.
     */
    @FXML
    private void onClickPrintBtn() {
        Scene scene = this.printBtn.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.setFullScreen(true);

        this.printBtn.setVisible(false);

        WritableImage image = this.siteVBox.snapshot(new SnapshotParameters(), null);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage(new PDRectangle((float) image.getWidth(), (float) image.getHeight()));
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                byte[] imageBytes = baos.toByteArray();

                PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageBytes, "image");

                contentStream.drawImage(pdImage, (float) 0, (float) 0, (float) image.getWidth(), (float) image.getHeight());
                contentStream.close();

                document.save(file);

                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.printBtn.setVisible(true);

        stage.setFullScreen(false);
    }

    /**
     * Method that sets the site labels.
     *
     * @param site The selected site.
     */
    private void setSiteLabels(Site site) {
        idSiteLabel.setText("#" + String.valueOf(site.getId()));
        nameSiteLabel.setText(site.getName());
        typeSiteLabel.setText(site.getType());
        clientSiteLabel.setText(site.getClient());
        addressSiteLabel.setText(site.getAddress());
        startDateSiteLabel.setText(site.getStartDate());
        endDateSiteLabel.setText(site.getEndDate());
    }

    /**
     * Method that updates the progress bar based on the start date and end date of the site and the current date.
     *
     * @param site The selected site.
     */
    private void updateProgressBar(Site site) {
        LocalDate startLocalDate = LocalDate.parse(site.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocalDate = LocalDate.parse(site.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate currentDate = LocalDate.now();
        long totalDays = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        long elapsedDays = ChronoUnit.DAYS.between(startLocalDate, currentDate);
        double progress = (double) elapsedDays / totalDays;

        progressBar.setProgress(progress);
    }

    /**
     * Method that shows the tasks pane for the selected site organized by categories and subcategories.
     *
     * @param site The selected site.
     */
    private void showTasksPane(Site site) {
        // Add categories to tasks pane
        List<Category> categoriesList = categoryManager.getAllCategoriesBySite(site);

        for (Category category : categoriesList) {
            // Create category container for each category
            VBox categoryVBox = new VBox();
            categoryVBox.setSpacing(10);
            categoryVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
            if (this.tasksHBox.getChildren().size() == 0) {
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
            this.tasksHBox.getChildren().add(categoryVBox);

            // Add subcategories to subcategories container
            List<Subcategory> subcategoriesList = subcategoryManager.getAllSubcategoriesByCategory(category);

            for (Subcategory subcategory : subcategoriesList) {
                // Create subcategory container for each subcategory
                VBox subcategoryVBox = new VBox();
                subcategoryVBox.setSpacing(10);
                subcategoryVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
                if (subcategoriesHBox.getChildren().size() != 0) {
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
                    taskLabel.setPadding(new javafx.geometry.Insets(5, 10, 5, 10));

                    tasksGridPane.addRow(tasksGridPane.getRowCount(), taskLabel);
                }
            }
        }

    }
}
