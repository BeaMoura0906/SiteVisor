package com.example.sitevisor.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * PopupController class is used to display a popup window.
 */
public class PopupController {

    /**
     * Properties
     */
    private Stage stage;
    private boolean isDeletionConfirmed = false;

    /**
     * FXML elements
     */
    @FXML
    private Button okBtn;
    @FXML
    private ImageView image;
    @FXML
    private Label messageLabel;

    /**
     * FXML method to initialize
     */
    @FXML
    public void initialize() {

    }

    /**
     * FXML method to handle ok button click
     */
    @FXML
    private void onClickOkBtn() {
        if (okBtn.getText().equals("Supprimer")) {
            this.isDeletionConfirmed = true;
        }

        stage.close();
    }

    /**
     * Method to set the stage
     *
     * @param stage the stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to get the deletion confirmation status
     *
     * @return true if the user confirmed the deletion
     */
    public boolean isDeletionConfirmed() {
        return this.isDeletionConfirmed;
    }

    /**
     * Method to set the view to delete confirmation popup
     */
    public void setViewToDelete(){
        this.okBtn.setText("Supprimer");
        this.okBtn.setStyle("-fx-background-color: #FFC107");

        InputStream inputStream = getClass().getResourceAsStream("/images/danger.png");
        if (inputStream != null) {
            this.image.setImage(new Image(inputStream));
        } else {
            System.out.println("Impossible de charger l'image.");
        }
    }

    /**
     * Method to set the view to message popup with an image and a message
     *
     * @param success true if the message is a success
     * @param message the message to display on the popup
     */
    public void setMessage(boolean success, String message) {

        if (success) {
            InputStream inputStream = getClass().getResourceAsStream("/images/success.png");
            if (inputStream != null) {
                this.image.setImage(new Image(inputStream));
            } else {
                System.out.println("Impossible de charger l'image.");
            }
        } else {
            InputStream inputStream = getClass().getResourceAsStream("/images/error.png");
            if (inputStream != null) {
                this.image.setImage(new Image(inputStream));
            } else {
                System.out.println("Impossible de charger l'image.");
            }
        }

        this.messageLabel.setText(message);
    }

}
