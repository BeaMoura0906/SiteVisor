/**
 * Module for SiteVisor application. It requires all JavaFX modules, pdfbox, java.sql, java.desktop, org.kordamp.bootstrapfx.core and org.controlsfx.controls. It opens all FXML files and opens and exports all controllers and entities classes.
 */
module com.example.sitevisor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    requires org.apache.pdfbox;


    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens com.example.sitevisor to javafx.fxml;
    exports com.example.sitevisor;
    exports com.example.sitevisor.Controller;
    opens com.example.sitevisor.Controller to javafx.fxml;
    opens com.example.sitevisor.Model.Entity to javafx.base;
}