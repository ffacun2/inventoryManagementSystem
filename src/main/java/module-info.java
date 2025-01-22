module org.ims.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.ims to javafx.fxml;
    exports org.ims;
    exports org.ims.view;
    opens org.ims.view to javafx.fxml;
}