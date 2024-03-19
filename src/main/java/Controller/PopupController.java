package Controller;

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

public class PopupController {

    private Stage stage;
    private boolean isDeletionConfirmed = false;
    @FXML
    private Button okBtn;
    @FXML
    private ImageView image;
    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {

    }

    @FXML
    private void onClickOkBtn() {
        if (okBtn.getText().equals("Supprimer")) {
            this.isDeletionConfirmed = true;
        }

        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isDeletionConfirmed() {
        return this.isDeletionConfirmed;
    }

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
