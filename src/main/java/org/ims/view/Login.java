package org.ims.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private TextField textFieldUser;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Button buttonLogin;

    @FXML
    private void handleLogicClickLogin(){
        String user = textFieldUser.getText();
        String password = textFieldPassword.getText();

        System.out.println("User: " + user+" password: " + password);
    }
}
