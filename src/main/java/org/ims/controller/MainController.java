package org.ims.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.ims.model.User;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Label userLabel;


    private User userLogued;


    public void setUserLogued (User userLogued) {
        this.userLogued = userLogued;
        userLabel.setText(userLogued.getUsername() + " ("+userLogued.getRole()+")");
    }


    public void showProduct ()
    throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ims/view/ProductPanel.fxml"));
        Parent pane = loader.load();
        borderPane.setCenter(pane);
    }

    public void showUsers()
        throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ims/view/UserPanel.fxml"));
        Parent pane = loader.load();
        borderPane.setCenter(pane);
    }

    public void closeMainWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ims/view/Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();

            stage.setTitle("Inventory System");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) borderPane.getScene().getWindow();
            loginStage.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
