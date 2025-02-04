package org.ims.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.ims.model.User;
import org.ims.repository.UserDAO;
import org.ims.services.LoginService;
import org.ims.services.UserService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField textFieldUser;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private CheckBox checkBoxRemember;


    private final UserService userService;
    private final LoginService loginService;

    public LoginController() {
        this.userService = new UserService(new UserDAO());
        this.loginService = new LoginService(new UserDAO());
    }

    @FXML
    protected void initialize() {
        String userRemembered = loginService.loadCredentials();
        if (!userRemembered.isEmpty()) {
            textFieldUser.setText(userRemembered);
            checkBoxRemember.setSelected(true);
            Platform.runLater(()->textFieldPassword.requestFocus());
        }
        else {
            Platform.runLater(()->textFieldUser.requestFocus());
        }
    }


    @FXML
    private void handleLogicClickLogin(){
        String username = textFieldUser.getText();
        String password = textFieldPassword.getText();

        try {
            User userLogin = loginService.authenticateUser(username,password);
            if (userLogin != null) {
                loginService.saveCredentials(username,checkBoxRemember.isSelected());
                openMainWindows(userLogin);
            }
            else {
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


    @FXML
    protected void handleKeyEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            handleLogicClickLogin();
        }
    }
}
