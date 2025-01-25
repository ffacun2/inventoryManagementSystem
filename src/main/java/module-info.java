module org.ims {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.ims to javafx.fxml;
    exports org.ims;
    opens org.ims.model to javafx.fxml;
    exports org.ims.model;
    opens org.ims.controller to javafx.fxml;
    exports org.ims.controller;
    opens org.ims.img to javafx.fxml;

}