package Controller;

import Model.Entity.Document;
import Model.Entity.Site;
import Model.Manager.DocumentManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import util.LoadPopUp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class DocController {

    private Site site;
    private DocumentManager documentManager;
    private File selectedFile;
    @FXML
    private ChoiceBox<Document> docChoiceBox;
    @FXML
    private Button visualizeFileBtn;
    @FXML
    private Button deleteFileBtn;
    @FXML
    private Button loadFileBtn;
    @FXML
    private Label fileLoadedLabel;
    @FXML
    private TextField fileNameTextField;
    @FXML
    private Button addFileBtn;

    public DocController(Site site) {
        this.site = site;
        this.documentManager = new DocumentManager();
    }

    @FXML
    public void initialize() {
        setDocChoiceBox();

        this.docChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.visualizeFileBtn.setDisable(false);
                this.deleteFileBtn.setDisable(false);
            }
        });
    }

    private void setDocChoiceBox() {
        List<Document> documents = this.documentManager.getAllDocumentsBySite(this.site);
        this.docChoiceBox.getItems().clear();
        this.docChoiceBox.getItems().addAll(documents);
    }

    @FXML
    private void onClickVisualizeFileBtn() {

    }

    @FXML
    private void onClickDeleteFileBtn() {

    }

    @FXML
    private void onClickLoadFileBtn() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        this.selectedFile = fileChooser.showOpenDialog(loadFileBtn.getScene().getWindow());
        if (this.selectedFile != null) {
            this.addFileBtn.setDisable(false);
            this.fileLoadedLabel.setVisible(true);
            String selectedFileName = getFileNameWithoutExtensionAndWithoutSpace(this.selectedFile.getName());
            this.fileNameTextField.setText(selectedFileName);
        }
    }

    @FXML
    private void onClickAddFileBtn() {
        String fileName = this.fileNameTextField.getText().trim();
        // Verify if the file name is not empty and if it already exists in the database (not duplicate file with same name)
        if (fileName.isEmpty() || !containsNoSpaceOrExtension(fileName)) {
            boolean success = false;
            String message = "Veuillez entrer un nom de fichier sans espace et sans l'extention (.pdf, .jpg, .jpeg, .png).";
            String title = "SiteVisor | Erreur";
            PopupController popupController = LoadPopUp.loadPopup(success, message, title);
        } else if ( this.documentManager.getDocumentByNameAndSite(fileName, this.site) ) {
            boolean success = false;
            String message = "Ce nom de fichier existe déja pour le chantier selectionné. Merci d'en choisir un autre.";
            String title = "SiteVisor | Erreur";
            PopupController popupController = LoadPopUp.loadPopup(success, message, title);
        } else if (this.selectedFile != null) {
            try {
                String destinationFolder = "src/main/resources/site_docs/site_" + this.site.getId() + "/";
                File destinationDir = new File(destinationFolder);
                if (!destinationDir.exists()) {
                    destinationDir.mkdirs();
                }

                String newFileName = fileName + getFileExtension(selectedFile.getName());

                File newFile = new File(destinationDir, newFileName);
                Files.copy(selectedFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                String type = getTypeOfFile(newFile);
                Document newDocument = new Document(0, fileName, type, destinationFolder + newFileName, this.site);

                boolean isInserted = this.documentManager.insertDocument(newDocument);

                if (newFile.exists() && isInserted) {
                    setDocChoiceBox();
                    this.selectedFile = null;
                    this.fileNameTextField.clear();
                    this.fileLoadedLabel.setVisible(false);
                    boolean success = true;
                    String message = "Le fichier a bien été enregistré.";
                    String title = "SiteVisor | Succès";
                    PopupController popupController = LoadPopUp.loadPopup(success, message, title);
                } else {
                    boolean success = false;
                    String message = "Une erreur est survenue lors de l'enregistrement du fichier.";
                    String title = "SiteVisor | Erreur";
                    PopupController popupController = LoadPopUp.loadPopup(success, message, title);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isImage(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }

    private boolean isPDF(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".pdf");
    }

    public boolean containsNoSpaceOrExtension(String str) {
        if (str.contains(" ")) {
            return false;
        }

        int dotIndex = str.lastIndexOf(".");
        if (dotIndex != -1) {
            return false;
        }

        return true;
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }

    private String getFileNameWithoutExtensionAndWithoutSpace(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            fileName = fileName.substring(0, dotIndex);
        }
        return fileName.replaceAll("\\s", "");
    }

    private String getTypeOfFile(File file) {
        String fileName = file.getName().toLowerCase();
        if ( fileName.endsWith(".pdf") ) {
            return "pdf";
        } else if ( fileName.endsWith(".jpg") ) {
            return "jpg";
        } else if ( fileName.endsWith(".jpeg") ) {
            return "jpeg";
        } else if ( fileName.endsWith(".png") ) {
            return "png";
        }
        return "";
    }
}
