package Controller;

import Model.Entity.Site;
import Model.Manager.SiteManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SiteController {

    @FXML
    private Label idSiteLabel;
    @FXML
    private Label nameSiteLabel;
    @FXML
    private Label typeSiteLabel;
    @FXML
    private Label clientSiteLabel;
    @FXML
    private Label addressSiteLabel;
    @FXML
    private Label startDateSiteLabel;
    @FXML
    private Label endDateSiteLabel;
    @FXML
    private ProgressBar progressBar;

    SiteManager siteManager = new SiteManager();



    //Faire TabPane : Chantier / Editer taches / Documents associés

    @FXML
    public void initialize(Site site) {
        site = this.siteManager.getSiteById(site.getId());

        idSiteLabel.setText("#" + String.valueOf(site.getId()));
        nameSiteLabel.setText(site.getName());
        typeSiteLabel.setText(site.getType());
        clientSiteLabel.setText(site.getClient());
        addressSiteLabel.setText(site.getAddress());
        startDateSiteLabel.setText(site.getStartDate());
        endDateSiteLabel.setText(site.getEndDate());

        LocalDate startLocalDate = LocalDate.parse(site.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocalDate = LocalDate.parse(site.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate currentDate = LocalDate.now();
        long totalDays = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        long elapsedDays = ChronoUnit.DAYS.between(startLocalDate, currentDate);
        double progress = (double) elapsedDays / totalDays;

        System.out.println( "TotalDays: " + totalDays);
        System.out.println( "Elapsed time: " + elapsedDays);
        System.out.println( "Progress: " + progress );

        progressBar.setProgress(progress);

    }


}
