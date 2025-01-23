module org.ims {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.ims to javafx.fxml;
    exports org.ims;
    opens org.ims.view to javafx.fxml;
    exports org.ims.view;
}