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

    private Parent products;
    private Parent users;
    private Parent suppliers;
    private Parent purchase;
    private Parent inventory;

    private User userLogued;


    public void setUserLogued (User userLogued) {
        this.userLogued = userLogued;
        userLabel.setText(userLogued.getUsername() + " ("+userLogued.getRole()+")");
    }



    public void showProduct ()
    throws IOException {
        if ( products == null ) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ims/view/ProductPanel.fxml"));
            products = loader.load();
        }
        borderPane.setCenter(products);
    }

    public void showUsers()
        throws IOException {
        if ( users == null ) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ims/view/UserPanel.fxml"));
            users = loader.load();
        }
        borderPane.setCenter(users);
    }

    public void showSuppliers()
    throws IOException {
        if (suppliers == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ims/view/SupplierPanel.fxml"));
            suppliers = loader.load();
        }
        borderPane.setCenter(suppliers);
    }

    public void showPurchase()
    throws IOException {
        if (purchase == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ims/view/PurchasePanel.fxml"));
            purchase = loader.load();
        }
        borderPane.setCenter(purchase);
    }

    public void showInventory()
    throws IOException {
        if (inventory == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ims/view/InventoryPanel.fxml"));
            inventory = loader.load();
        }
        borderPane.setCenter(inventory);
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

            products = null;
            purchase = null;
            users = null;
            suppliers = null;
            inventory = null;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
