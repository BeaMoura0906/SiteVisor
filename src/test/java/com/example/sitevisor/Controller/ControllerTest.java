package com.example.sitevisor.Controller;

import com.example.sitevisor.Model.Entity.Site;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;

import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(ApplicationExtension.class)
public class ControllerTest {

    private Controller controller;

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sitevisor/app-view.fxml"));
        Parent mainNode = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Test
    public void testOnChangeSitesTab(FxRobot robot) {
        robot.clickOn("#tabSites");
        WaitForAsyncUtils.waitForFxEvents();
        TableView<Site> tableView = robot.lookup("#tableSites").queryAs(TableView.class);
        assertFalse(tableView.getItems().isEmpty(), "The table should not be empty after tab change");
    }

    @Test
    public void testOnClickSearchButton(FxRobot robot) {
        robot.clickOn("#nameSearch").write("SiteTest");
        robot.clickOn("#searchButton");
        WaitForAsyncUtils.waitForFxEvents();
        TableView<Site> tableView = robot.lookup("#tableSites").queryAs(TableView.class);
        assertEquals(1, tableView.getItems().size(), "The table should contain exactly one item");
    }
}
