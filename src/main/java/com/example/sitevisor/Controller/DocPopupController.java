package com.example.sitevisor.Controller;

import com.example.sitevisor.Model.Entity.Document;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.InputStream;

import javafx.embed.swing.SwingFXUtils;
import com.example.sitevisor.util.LoadPopUp;

import java.awt.image.BufferedImage;

/**
 * DocPopupController class is used to display a document in a popup window.
 */
public class DocPopupController {

    /**
     * Properties
     */
    private Stage stage;
    private Document document;

    /**
     * FXML elements
     */
    @FXML
    private VBox docVBox;

    /**
     * DocPopupController constructor that initializes the document to display.
     *
     * @param document the document to display
     */
    public DocPopupController(Document document) {
        this.document = document;
    }

    /**
     * FXML method that initializes the popup window. It loads the document and displays it in the popup window.
     */
    @FXML
    public void initialize() {
        showDocument(this.document);
    }

    /**
     * Method that sets the stage.
     *
     * @param stage the stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method that displays the document in the popup window. It extracts the resource path of the document and displays it in the popup window.
     *
     * @param document the document to display
     */
    public void showDocument(Document document) {
        String filePath = document.getPath();

        if ( document.getType().equals("jpeg") || document.getType().equals("jpg") || document.getType().equals("png") ) {
            ImageView imageView = new ImageView();

            String ressourcePath = extractResourcePath(filePath);

            InputStream inputStream = getClass().getResourceAsStream(ressourcePath);
            if (inputStream != null) {
                Image image = new Image(inputStream);
                double maxWidth = 700;
                double maxHeight = 500;
                double ratio = Math.min(maxWidth / image.getWidth(), maxHeight / image.getHeight());
                imageView.setImage(image);
                imageView.setFitWidth(image.getWidth() * ratio);
                imageView.setFitHeight(image.getHeight() * ratio);
            } else {
                boolean success = false;
                String message = "Le document n'a pas pu etre charge !";
                String title = "SiteVisor | Erreur";
                LoadPopUp.loadPopup(success, message, title);
                this.stage.close();
            }

            docVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
            docVBox.getChildren().clear();
            docVBox.getChildren().add(imageView);

        } else if ( document.getType().equals("pdf") ) {
            try {
                PDDocument doc = PDDocument.load(new File(filePath));
                PDFRenderer renderer = new PDFRenderer(doc);

                ScrollPane scrollPane = new ScrollPane();
                VBox pagesContainer = new VBox();
                scrollPane.setContent(pagesContainer);
                scrollPane.setFitToWidth(true);
                scrollPane.setMaxHeight(750);

                for (PDPage page : doc.getPages()) {
                    BufferedImage bufferedImage = renderer.renderImageWithDPI(doc.getPages().indexOf(page), 100);
                    javafx.scene.image.Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(600);
                    pagesContainer.getChildren().add(imageView);
                }

                docVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
                docVBox.getChildren().clear();
                docVBox.getChildren().add(scrollPane);

                doc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that extracts the resource path of the document.
     *
     * @param absolutePath the absolute path of the document
     * @return the resource path
     */
    private String extractResourcePath(String absolutePath) {
        int index = absolutePath.indexOf("resources/");
        if (index != -1) {
            return absolutePath.substring(index + "resources/".length() - 1);
        } else {
            System.err.println("Chemin absolu incorrect. Impossible d'extraire le chemin des ressources.");
            return "";
        }
    }
}
