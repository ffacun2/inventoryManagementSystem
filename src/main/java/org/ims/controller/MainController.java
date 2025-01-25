package org.ims.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane borderPane;

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
}
