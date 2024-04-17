package com.example.sitevisor;

import com.example.sitevisor.Model.Manager.Manager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("SiteVisor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

        // Close the database connection at the end of the application
        Manager.getInstance().closeConnection();
    }
}