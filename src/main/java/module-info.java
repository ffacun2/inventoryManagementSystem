module org.ims {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires java.prefs;


    opens org.ims to javafx.fxml;
    exports org.ims;
    opens org.ims.model to javafx.fxml;
    exports org.ims.model;
    opens org.ims.controller to javafx.fxml;
    exports org.ims.controller;
}