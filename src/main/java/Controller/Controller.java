package Controller;

import Model.Entity.Site;
import Model.Manager.SiteManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class Controller {
    @FXML
    private TableView<Site> tableSites;

    @FXML
    private TableColumn<Site, Integer> idCol;

    @FXML
    private TableColumn<Site, String> nameCol;

    @FXML
    private TableColumn<Site, String> typeCol;

    @FXML
    private TableColumn<Site, String> clientCol;

    @FXML
    private TableColumn<Site, String> addressCol;

    @FXML
    private TableColumn<Site, String> startDateCol;

    @FXML
    private TableColumn<Site, String> endDateCol;

    @FXML
    private TableColumn<Site, Integer> userIdCol;

    public void initialize() {
        SiteManager siteManager = new SiteManager();
        List<Site> sites = siteManager.getAllSites();

        for (Site site : sites) {
            System.out.println(site.getName());
        }

        setUpTableView();
        tableSites.getItems().addAll(sites);
    }

    private void setUpTableView() {
        TableColumn<Site, Integer> idCol = (TableColumn<Site, Integer>) tableSites.getColumns().get(0);
        TableColumn<Site, String> nameCol = (TableColumn<Site, String>) tableSites.getColumns().get(1);
        TableColumn<Site, String> typeCol = (TableColumn<Site, String>) tableSites.getColumns().get(2);
        TableColumn<Site, String> clientCol = (TableColumn<Site, String>) tableSites.getColumns().get(3);
        TableColumn<Site, String> addressCol = (TableColumn<Site, String>) tableSites.getColumns().get(4);
        TableColumn<Site, String> startDateCol = (TableColumn<Site, String>) tableSites.getColumns().get(5);
        TableColumn<Site, String> endDateCol = (TableColumn<Site, String>) tableSites.getColumns().get(6);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        clientCol.setCellValueFactory(new PropertyValueFactory<>("client"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }
}