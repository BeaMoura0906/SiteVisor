package com.example.sitevisor;

import com.example.sitevisor.Model.Manager.Manager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

/**
 * App class is the main class of the application.
 */
public class App extends Application {

    /**
     * Method that starts the application.
     *
     * @param stage the primary stage of the application
     * @throws IOException if an error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("SiteVisor");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that starts the application. It closes the database connection at the end of the application.
     *
     * @param args the arguments for the application (ignored)
     */
    public static void main(String[] args) {
        launch();

        // Close the database connection at the end of the application
        Manager.getInstance().closeConnection();
    }
}