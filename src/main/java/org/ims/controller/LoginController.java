package org.ims.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.ims.model.User;
import org.ims.repository.UserDAO;
import org.ims.services.UserService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField textFieldUser;
    @FXML
    private TextField textFieldPassword;


    private final UserService userService;

    public LoginController() {
        this.userService = new UserService(new UserDAO());
    }


    @FXML
    private void handleLogicClickLogin(){
        User user;
        String username = textFieldUser.getText();
        String password = textFieldPassword.getText();

        try {
            user = userService.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                openMainWindows(user);
            }
            else {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setContentText("Invalid username or password");
//                alert.showAndWait();
                JOptionPane pane = new JOptionPane("Invalid username or password");
                JOptionPane.showMessageDialog(null, pane.getMessage(), "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (SQLException | NullPointerException e) {
            JOptionPane pane = new JOptionPane(e.getMessage());
            JOptionPane.showMessageDialog(null, pane.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void openMainWindows(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ims/view/Main.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();


            stage.setTitle("Inventory System");
            stage.setScene(new Scene(root));
            stage.show();
            MainController mainController = loader.getController();
            mainController.setUserLogued(user);

            Stage loginStage = (Stage) textFieldPassword.getScene().getWindow();
            loginStage.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
