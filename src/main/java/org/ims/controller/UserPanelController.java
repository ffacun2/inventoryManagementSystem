package org.ims.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.ims.exception.DuplicatedUserException;
import org.ims.exception.InvalidImputDataException;
import org.ims.model.User;
import org.ims.repository.UserDAO;
import org.ims.services.UserService;

import javafx.scene.input.MouseEvent;

import java.util.Arrays;


public class UserPanelController {

    @FXML
    private TextField textFieldUser;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldDNI;
    @FXML
    private ChoiceBox checkBoxRole;
    @FXML
    private TableView userTable;
    @FXML
    private ImageView icon;
    @FXML
    private TextField textFieldSearch;

    private Image showImage;
    private Image hideImage;
    private boolean showHidePassword;

    private UserService userService;


    public UserPanelController (){
        this.userService = new UserService(new UserDAO());
    }

    @FXML
    protected void initialize(){
        //predefined properties for types of users
        checkBoxRole.getItems().addAll("Administrador","Usuario");
        checkBoxRole.setValue("Usuario");

        passwordField.textProperty().bindBidirectional(textFieldPassword.textProperty());
        this.showImage = new Image(getClass().getResource("/org/ims/img/eye-icon.png").toExternalForm());
        this.hideImage = new Image(getClass().getResource("/org/ims/img/eye-blind-icon.png").toExternalForm());
        showhidePassword();
        //load columns names for users table
//        loadColumnNames();
        //load the users of the database
//        loadTable();

        String[] columnNames = {"username", "email","role", "name", "lastname", "DNI"};

        for (String colum : columnNames){
            TableColumn<User,String> column = new TableColumn<>(colum);
            column.setCellValueFactory(new PropertyValueFactory<>(colum));
            userTable.getColumns().add(column);
        }

        ObservableList<User> users = FXCollections.observableArrayList(
                    new User(1L,"John123","sdagdgd","ejemplo@gmail.com","usuario","jonh","connor",123456789) ,
                    new User(2L,"Jimmy","12531127","ejemplo2@gmail.com","usuario","Jimmy","connor",894654135),
                    new User(3L,"George","12567","ejemplo3@gmail.com","Administrador","George","connor",123456489),
                    new User(4L,"Abraham","1234567","ejemplo4@gmail.com","usuario","Abraham","connor",134476789),
                    new User(5L,"Lizy","2ff23sa","ejemplo5@gmail.com","Administrador","Lizy","connor",129556789)
            );
        userTable.setItems(users);
    }

    @FXML
    protected void clearFields(){
        textFieldUser.clear();
        passwordField.clear();
        textFieldEmail.clear();
        textFieldName.clear();
        textFieldLastName.clear();
        textFieldDNI.clear();
        userTable.getSelectionModel().clearSelection();
    }

    protected void loadColumnNames(){
        userTable.getColumns().addAll(
                Arrays.stream(userService.getColumnNames())
                    .map(TableColumn::new)
                    .toList()
        );
    }

    protected void loadTable() {
        userTable.setItems(
                FXCollections.observableList(
                        userService.getAllUsers()
                )
        );
    }

    @FXML
    protected void addUser(){
        User user = validateAttribute(
                textFieldUser.getText(),
                passwordField.getText(),
                textFieldEmail.getText(),
                (String) checkBoxRole.getValue(),
                textFieldName.getText(),
                textFieldLastName.getText(),
                textFieldDNI.getText()
        );
        if (user != null){
            //Verifico que no exista otro usuario igual
            // En caso de que exista lanzo alerta para informar
            if( !duplicatedUser(user) ){ // Si no existe uno igual lo creo y agrego a la bd
                userService.createUser(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("User created successfully");
                alert.showAndWait();
                clearFields();
                loadTable();
            }
        }
    }

    protected User validateAttribute(String username, String password, String email, String role, String name, String lastName, String DNI){
        try {
            return userService.validateUser(username, password, email, role, name, lastName, DNI);
        }
        catch(InvalidImputDataException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Data validation failed");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return null;
        }
    }

    protected boolean duplicatedUser(User user) {
        try {
            return true;
        }
        catch (DuplicatedUserException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return false;
        }
    }

    protected void deleteUser() {
        userService.deleteUser((User) userTable.getSelectionModel().getSelectedItem());
        loadTable();
    }

    protected void updateUser() {
        userService.updateUser((User) userTable.getSelectionModel().getSelectedItem());
        loadTable();
    }



    public void handleEventMouseClicked(MouseEvent mouseEvent) {
            User user = (User) userTable.getSelectionModel().getSelectedItem();
            if (user != null) {
                checkBoxRole.setValue(user.getRole());
                textFieldUser.setText(user.getUsername());
                passwordField.setText(user.getPassword());
                textFieldEmail.setText(user.getEmail());
                textFieldName.setText(user.getName());
                textFieldLastName.setText(user.getLastname());
                textFieldDNI.setText(String.valueOf(user.getDNI()));
            }
    }

    @FXML
    protected void showhidePassword() {
        if (showHidePassword) {
            showHidePassword = false;
            icon.setImage(hideImage);
            textFieldPassword.setVisible(true);
            passwordField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        }
        else {
            showHidePassword = true;
            icon.setImage(showImage);
            textFieldPassword.setVisible(false);
            passwordField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
        }
    }

    @FXML
    protected void search() {
        String searchText = textFieldSearch.getText().toLowerCase();
        ObservableList<User> filteredData = FXCollections.observableArrayList();
        for (User user : userService.getAllUsers()) {
            if (user.getUsername().toLowerCase().contains(searchText) ||
                    user.getEmail().toLowerCase().contains(searchText) ||
                    user.getRole().toLowerCase().contains(searchText) ||
                    user.getName().toLowerCase().contains(searchText) ||
                    user.getLastname().toLowerCase().contains(searchText) ||
                    String.valueOf(user.getDNI()).contains(searchText))
            {
                filteredData.add(user);
            }
        }
        userTable.setItems(filteredData);
    }

}
