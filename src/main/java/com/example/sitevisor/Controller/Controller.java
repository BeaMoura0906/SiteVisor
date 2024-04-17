package com.example.sitevisor.Controller;

import com.example.sitevisor.Model.Entity.Site;
import com.example.sitevisor.Model.Manager.SiteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import com.example.sitevisor.util.LoadPopUp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Site> sites;
    private int siteId;

    SiteManager siteManager = new SiteManager();

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
    @FXML
    private ChoiceBox<String> siteChoiceBox;
    @FXML
    private TextField siteNameTextField;
    @FXML
    private TextField siteTypeTextField;
    @FXML
    private TextField siteClientTextField;
    @FXML
    private TextField siteAddressTextField;
    @FXML
    private DatePicker siteStartDatePicker;
    @FXML
    private DatePicker siteEndDatePicker;
    @FXML
    private Button siteAddBtn;
    @FXML
    private Button siteModifyBtn;
    @FXML
    private Button siteDeleteBtn;

    @FXML
    private void onChangeSitesTab() {

        this.sites = this.siteManager.getAllSites();

        setUpTableView();

        tableSites.getItems().clear();
        tableSites.getItems().addAll(sites);

        setRowDoubleClickListener();
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

    @FXML
    private void onChangeEditSiteTab() {
        this.siteNameTextField.clear();
        this.siteTypeTextField.clear();
        this.siteClientTextField.clear();
        this.siteAddressTextField.clear();
        this.siteStartDatePicker.setValue(null);
        this.siteEndDatePicker.setValue(null);

        setUpSiteChoiceBox();
    }

    @FXML
    private void onClickSiteAddBtn() {
        if (siteNameTextField.getText().isEmpty() || siteTypeTextField.getText().isEmpty() || siteClientTextField.getText().isEmpty() || siteAddressTextField.getText().isEmpty() || siteStartDatePicker.getValue() == null || siteEndDatePicker.getValue() == null) {
            boolean success = false;
            String message = "Veuillez renseigner tous les champs !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
            String name = siteNameTextField.getText();
            String type = siteTypeTextField.getText();
            String client = siteClientTextField.getText();
            String address = siteAddressTextField.getText();
            LocalDate startDate = siteStartDatePicker.getValue();
            String startDateFormatted = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = siteEndDatePicker.getValue();
            String endDateFormatted = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // To change with authentication
            int userId = 1;

            Site site = new Site(0, name, type, client, address, startDateFormatted, endDateFormatted, userId);
            boolean isInserted = this.siteManager.insertSite(site);

            if (isInserted) {
                onChangeEditSiteTab();
                boolean success = true;
                String message = "Chantier ajouté avec succès !";
                String title = "SiteVisor | Succès";
                LoadPopUp.loadPopup(success, message, title);
            } else {
                boolean success = false;
                String message = "Impossible d'ajouter le chantier ! Veuillez réessayer.";
                String title = "SiteVisor | Erreur";
                LoadPopUp.loadPopup(success, message, title);
            }
        }
    }

    @FXML
    private void onClickSiteModifyBtn() {
        if( siteChoiceBox.getSelectionModel().isEmpty() || siteNameTextField.getText().isEmpty() || siteTypeTextField.getText().isEmpty() || siteClientTextField.getText().isEmpty() || siteAddressTextField.getText().isEmpty() || siteStartDatePicker.getValue() == null || siteEndDatePicker.getValue() == null) {
            boolean success = false;
            String message = "Veuillez renseigner tous les champs !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
            String name = siteNameTextField.getText();
            String type = siteTypeTextField.getText();
            String client = siteClientTextField.getText();
            String address = siteAddressTextField.getText();
            LocalDate startDate = siteStartDatePicker.getValue();
            String startDateFormatted = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = siteEndDatePicker.getValue();
            String endDateFormatted = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // To change with authentication
            int userId = 1;

            Site site = new Site(siteId, name, type, client, address, startDateFormatted, endDateFormatted, userId);
            boolean isUpdated = this.siteManager.updateSite(site);

            if (isUpdated) {
                onChangeEditSiteTab();
                boolean success = true;
                String message = "Chantier mis à jour avec succès !";
                String title = "SiteVisor | Succès";
                LoadPopUp.loadPopup(success, message, title);
            } else {
                boolean success = false;
                String message = "Impossible de mettre à jour le chantier ! Veuillez réessayer.";
                String title = "SiteVisor | Erreur";
                LoadPopUp.loadPopup(success, message, title);
            }
        }
    }

    @FXML
    private void onClickSiteDeleteBtn() {
        if (siteChoiceBox.getSelectionModel().isEmpty()) {
            boolean success = false;
            String message = "Veuillez sélectionner un chantier !";
            String title = "SiteVisor | Erreur";
            LoadPopUp.loadPopup(success, message, title);
        } else {
            boolean success = false;
            String message = "Voulez-vous vraiment supprimer ce chantier ?";
            String title = "SiteVisor | Suppression";
            PopupController popupController = LoadPopUp.loadPopup(success, message, title);
            if (popupController.isDeletionConfirmed() ) {
                boolean isDeleted = this.siteManager.deleteSite(siteId);
                if (isDeleted) {
                    onChangeEditSiteTab();
                    boolean successDeleted = true;
                    String messageDeleted = "Chantier supprimé avec succès !";
                    String titleDeleted = "SiteVisor | Succès";
                    LoadPopUp.loadPopup(successDeleted, messageDeleted, titleDeleted);
                } else {
                    boolean successDeleted = false;
                    String messageDeleted = "Impossible de supprimer le chantier ! Veuillez réessayer.";
                    String titleDeleted = "SiteVisor | Erreur";
                    LoadPopUp.loadPopup(successDeleted, messageDeleted, titleDeleted);
                }
            } else {
                onChangeEditSiteTab();
            }
        }
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
                    Scene scene = new Scene(root);
                    siteStage.setMinWidth(1000);
                    siteStage.setMinHeight(650);
                    siteStage.setScene(scene);
                    siteStage.show();

                }
            });
            return row;
        });
    }

    private void setUpSiteChoiceBox() {
        this.sites = this.siteManager.getAllSites();

        ObservableList<String> siteItems = FXCollections.observableArrayList();

        for (Site site : this.sites) {
            siteItems.add(site.getId() + " - " + site.getName() + " | " + site.getAddress());
        }

        siteItems.add(0, "");

        siteChoiceBox.setItems(siteItems);

        siteChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals("")) {
                siteChoiceBox.getSelectionModel().clearSelection();
                this.siteAddBtn.setDisable(false);
                this.siteModifyBtn.setDisable(true);
                this.siteDeleteBtn.setDisable(true);
                this.siteNameTextField.clear();
                this.siteTypeTextField.clear();
                this.siteClientTextField.clear();
                this.siteAddressTextField.clear();
                this.siteStartDatePicker.setValue(null);
                this.siteEndDatePicker.setValue(null);
            } else {
                String[] split = newValue.toString().split(" - ");
                this.siteId = Integer.parseInt(split[0]);
                this.siteAddBtn.setDisable(true);
                this.siteModifyBtn.setDisable(false);
                this.siteDeleteBtn.setDisable(false);
                Site selectedSite = this.siteManager.getSiteById(this.siteId);
                this.siteNameTextField.setText(selectedSite.getName());
                this.siteTypeTextField.setText(selectedSite.getType());
                this.siteClientTextField.setText(selectedSite.getClient());
                this.siteAddressTextField.setText(selectedSite.getAddress());
                this.siteStartDatePicker.setValue(LocalDate.parse(selectedSite.getStartDate()));
                this.siteEndDatePicker.setValue(LocalDate.parse(selectedSite.getEndDate()));
            }
        });
    }

}