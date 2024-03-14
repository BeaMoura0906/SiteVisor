package Controller;

import Model.Entity.Site;
import Model.Manager.SiteManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private VBox vBoxTableSites;

    @FXML
    private TableView<Site> tableSites;

    @FXML
    private TableColumn<Site, Integer> idCol;

    @FXML
    private TableColumn<Site, String> nameCol, typeCol, clientCol, addressCol, startDateCol, endDateCol;

    @FXML
    private TextField nameSearch;

    @FXML
    private TextField typeSearch;

    @FXML
    private TextField clientSearch;

    @FXML
    private DatePicker startDateSearch;

    @FXML
    private DatePicker endDateSearch;

    @FXML
    private Button searchButton;

    private List<Site> sites;

    SiteManager siteManager = new SiteManager();

    @FXML
    private void onChangeSitesTab() {

        this.sites = this.siteManager.getAllSites();

        setUpTableView();

        tableSites.getItems().clear();
        tableSites.getItems().addAll(sites);

        setRowDoubleClickListener();
    }

    @FXML
    private void onChangePlanningTab() {

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

        tableSites.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableSites.widthProperty().addListener((obs, oldVal, newVal) -> {
            double totalWidth = newVal.doubleValue();

            idCol.setPrefWidth(totalWidth * 0.05);
            nameCol.setPrefWidth(totalWidth * 0.2);
            typeCol.setPrefWidth(totalWidth * 0.15);
            clientCol.setPrefWidth(totalWidth * 0.15);
            addressCol.setPrefWidth(totalWidth * 0.25);
            startDateCol.setPrefWidth(totalWidth * 0.1);
            endDateCol.setPrefWidth(totalWidth * 0.1);
        });
    }

    private void setRowDoubleClickListener() {
        // Set row double-click listener
        tableSites.setRowFactory(tv -> {
            TableRow<Site> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Site selectedSite = row.getItem();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sitevisor/site-view.fxml"));
                    SiteController siteController = new SiteController(selectedSite);
                    loader.setController(siteController);
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    siteController.initialize();

                    Stage siteStage = new Stage();
                    siteStage.setTitle("SiteVisor | Chantier : " + selectedSite.getName());
                    siteStage.initModality(Modality.APPLICATION_MODAL);
                    siteStage.setScene(new Scene(root));
                    siteStage.show();

                }
            });
            return row;
        });
    }

    @FXML
    private void onClickSearchButton() {
        String name = nameSearch.getText();
        String type = typeSearch.getText();
        String client = clientSearch.getText();
        LocalDate startLocalDate = startDateSearch.getValue();
        LocalDate endLocalDate = endDateSearch.getValue();
        String startDate = null;
        String endDate = null;

        if ( startLocalDate != null ) {
            startDate = startLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if ( endLocalDate != null ) {
            endDate = endLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        List<Site> searchSites = new ArrayList<Site>();

        for (Site site : this.sites) {
            if ((site.getName().equals(name)) ||
                    (site.getType().equals(type)) ||
                    (site.getClient().equals(client)) ||
                    (site.getStartDate().equals(startDate)) ||
                    (site.getEndDate().equals(endDate))) {
                if (!searchSites.contains(site)) {
                    searchSites.add(site);
                }
            }
        }

        tableSites.getItems().setAll(searchSites);

        nameSearch.clear();
        typeSearch.clear();
        clientSearch.clear();
        startDateSearch.setValue(null);
        endDateSearch.setValue(null);
    }


}