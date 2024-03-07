module com.example.sitevisor {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.sitevisor to javafx.fxml;
    exports com.example.sitevisor;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Model.Entity to javafx.base;
}