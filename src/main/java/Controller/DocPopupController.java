package Controller;

import Model.Entity.Document;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javafx.embed.swing.SwingFXUtils;

import java.awt.image.BufferedImage;


public class DocPopupController {

    private Stage stage;
    private Document document;
    @FXML
    private VBox docVBox;

    public DocPopupController(Document document) {
        this.document = document;
    }

    @FXML
    public void initialize() {
        showDocument(this.document);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
                System.out.println("image loaded successfully.");
            } else {
                System.out.println("Impossible de charger l'image.");
            }

            docVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
            docVBox.getChildren().clear();
            docVBox.getChildren().add(imageView);

        } else if ( document.getType().equals("pdf") ) {
            try {
                PDDocument doc = PDDocument.load(new File(filePath));
                PDFRenderer renderer = new PDFRenderer(doc);

                ScrollPane scrollPane = new ScrollPane();
                VBox pagesContainer = new VBox(); // Conteneur pour stocker les ImageView de chaque page
                scrollPane.setContent(pagesContainer); // Ajout du conteneur dans la ScrollPane
                scrollPane.setFitToWidth(true); // Ajustement automatique à la largeur de la fenêtre

                for (PDPage page : doc.getPages()) {
                    BufferedImage bufferedImage = renderer.renderImageWithDPI(doc.getPages().indexOf(page), 100); // Réduire la résolution DPI
                    javafx.scene.image.Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setPreserveRatio(true); // Conserver le ratio d'aspect lors du redimensionnement
                    imageView.setFitWidth(600); // Ajustez la largeur de l'image à 600 pixels (par exemple)
                    pagesContainer.getChildren().add(imageView); // Ajout de l'ImageView à pagesContainer
                }

                docVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
                docVBox.getChildren().clear();
                docVBox.getChildren().add(scrollPane); // Ajout de la ScrollPane au docVBox

                doc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }



        }
    }

    private String extractResourcePath(String absolutePath) {
        int index = absolutePath.indexOf("resources/");
        if (index != -1) {
            System.out.println(absolutePath.substring(index + "resources/".length() - 1));
            return absolutePath.substring(index + "resources/".length() - 1);
        } else {
            System.err.println("Chemin absolu incorrect. Impossible d'extraire le chemin des ressources.");
            return "";
        }
    }
}
