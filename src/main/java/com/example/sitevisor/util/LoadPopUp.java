package com.example.sitevisor.util;

import com.example.sitevisor.Controller.PopupController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadPopUp {

    public static PopupController loadPopup(boolean success, String message, String title) {
        FXMLLoader loader = new FXMLLoader(LoadPopUp.class.getResource("/com/example/sitevisor/popup-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PopupController popupController = loader.getController();

        popupController.setMessage(success, message);

        if ( title.equals("SiteVisor | Suppression") ) {
            popupController.setViewToDelete();
        }

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(root));
        popupStage.setTitle(title);

        popupController.setStage(popupStage);

        popupStage.showAndWait();

        return popupController;
    }

}
