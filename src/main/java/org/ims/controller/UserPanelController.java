package org.ims.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.ims.exception.InvalidImputDataException;
import org.ims.exception.UserNotFoundException;
import org.ims.model.User;
import org.ims.repository.UserDAO;
import org.ims.services.UserService;

import javafx.scene.input.MouseEvent;

import java.io.PrintStream;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.Optional;


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
    private ChoiceBox<String> checkBoxRole;
    @FXML
    private TableView<User> userTable;
    @FXML
    private ImageView icon;
    @FXML
    private TextField textFieldSearch;

    private Image showImage;
    private Image hideImage;
    private boolean showHidePassword;

    private final UserService userService;


    public UserPanelController (){
        this.userService = new UserService(new UserDAO());
    }

    @FXML
    protected void initialize(){
        //predefined properties for types of users
        checkBoxRole.getItems().addAll("Administrador","Usuario");
        checkBoxRole.setValue("Usuario");

        passwordField.textProperty().bindBidirectional(textFieldPassword.textProperty());

        try {
            //password visibility toggle button
            this.showImage = new Image(getClass().getResource("/org/ims/img/eye-icon.png").toExternalForm());
            this.hideImage = new Image(getClass().getResource("/org/ims/img/eye-blind-icon.png").toExternalForm());
            showhidePassword();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //load columns names for users table
        loadColumnNames();
        //load the users of the database
        loadTable();
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

    /**
     * Loads the names of the database columns in the tableview
     */
    protected void loadColumnNames() {
        try {
            for (String colum : userService.getColumnNames()) {
                TableColumn<User,String> column = new TableColumn<>(colum);
                column.setCellValueFactory(new PropertyValueFactory<>(colum));
                userTable.getColumns().add(column);
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error loading column names");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.err.println(e.getMessage());
        }
    }

    /**
     * Loads the users located in the database and saves them to the tableview
     */
    protected void loadTable() {
        try {
            userTable.setItems(
                    FXCollections.observableArrayList(
                            userService.getAllUsers()
                    )
            );
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error loading users");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Locates the user in the database and reloads the tableview. The user is validated before locating
     */
    @FXML
    protected void addUser(){
        if (userTable.getSelectionModel().isEmpty()) {
            User user = validateAttribute(
                    textFieldUser.getText(),
                    passwordField.getText(),
                    textFieldEmail.getText(),
                    checkBoxRole.getValue(),
                    textFieldName.getText(),
                    textFieldLastName.getText(),
                    textFieldDNI.getText()
            );
            if (user != null){
                try {
                    userService.createUser(user);
                    showAlert("User created successfully", Alert.AlertType.INFORMATION);
                    clearFields();
                    loadTable();
                }
                catch (SQLException e) {
                    showAlert(e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        }
    }

    /**
     * Validates the arguments in the log section and then operates on them
     * @param username the username to validate
     * @param password the password to validate
     * @param email the email to validate
     * @param role the role to validate
     * @param name the name to validate
     * @param lastName the last name to validate
     * @param DNI   the DNI to validate
     * @return the user with the arguments validated
     */
    protected User validateAttribute(String username, String password, String email, String role, String name, String lastName, String DNI){
        try {
            return userService.validateUser(username, password, email, role, name, lastName, DNI);
        }
        catch(InvalidImputDataException e){
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }


    /**
     * Delete the user selected in the tableview.
     */
    @FXML
    protected void deleteUser() {
        if (!userTable.getSelectionModel().getSelectedItems().isEmpty())
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleting user");
                alert.setHeaderText("Are you sure that you want to delete this user?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    userService.deleteUser(userTable.getSelectionModel().getSelectedItem());
                    loadTable();
                    clearFields();
                }
            }
            catch (SQLException e) {
                showAlert(e.getMessage(), Alert.AlertType.ERROR);
            }
    }

    @FXML
    protected void updateUser() {
        if (!userTable.getSelectionModel().getSelectedItems().isEmpty()) {
            User user = validateAttribute(
                    textFieldUser.getText(),
                    passwordField.getText(),
                    textFieldEmail.getText(),
                    checkBoxRole.getValue(),
                    textFieldName.getText(),
                    textFieldLastName.getText(),
                    textFieldDNI.getText()
            );
            if (user != null) {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Updating user");
                    alert.setHeaderText("Are you sure that you want to update this user?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        userService.updateUser(userTable.getSelectionModel().getSelectedItem().getId(), user);
                        loadTable();
                    }
                }
                catch(SQLException e) {
                    showAlert(e.getLocalizedMessage(), Alert.AlertType.ERROR);
                }
                catch(UserNotFoundException e) {
                showAlert("User not found", Alert.AlertType.ERROR);
                }
            }
        }
    }


    /**
     * Toggle the visibility of the password field.
     */
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

    /**
     * Filters the users list by attributes provided in the search field;
     */
    @FXML
    protected void search() {
        String searchText = textFieldSearch.getText().toLowerCase();
        ObservableList<User> filteredData = FXCollections.observableArrayList();

        try {
            for (User user : userService.getAllUsers()) {
                if (user.getUsername().toLowerCase().contains(searchText) ||
                        user.getEmail().toLowerCase().contains(searchText) ||
                        user.getRole().toLowerCase().contains(searchText) ||
                        user.getName().toLowerCase().contains(searchText) ||
                        user.getLastname().toLowerCase().contains(searchText) ||
                        String.valueOf(user.getDni()).contains(searchText))
                {
                    filteredData.add(user);
                }
            }
        }
        catch (SQLException e) {
            showAlert("An error occurred while searching", Alert.AlertType.ERROR);
        }
        userTable.setItems(filteredData);
    }


    /**
     * Handles the exceptions that occur when trying to access the database and doing operations
     * @param e object with information about the exception that occurred during the operation.
     */
    private void handleExceptionsDataBase(SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        if (e instanceof SQLIntegrityConstraintViolationException) {
            alert.setHeaderText("Violation of uniqueness or integrity constraint");
        }
        else if (e instanceof SQLSyntaxErrorException) {
            alert.setHeaderText("Invalid SQL syntax");
        }
        else if (e instanceof SQLDataException) {
            alert.setHeaderText("Invalid data provided");
        }
        else {
            alert.setHeaderText("An error occurred while connecting to the database");
            alert.setContentText(e.getMessage());
        }
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    @FXML
    private void handleClickMouseEvent(MouseEvent mouseEvent) {
        User user = userTable.getSelectionModel().getSelectedItem();
        if (user!= null) {
            textFieldUser.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            textFieldEmail.setText(user.getEmail());
            checkBoxRole.setValue(user.getRole());
            textFieldName.setText(user.getName());
            textFieldLastName.setText(user.getLastname());
            textFieldDNI.setText(String.valueOf(user.getDni()));
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
//        alert.setContentText(message);
        // Crear un TextArea para el contenido expandible
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        // Configurar un tamaño preferido para el TextArea
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        // Agregar el TextArea al contenido expandible de la alerta
        VBox expandableContent = new VBox();
        expandableContent.getChildren().add(textArea);
        alert.getDialogPane().setExpandableContent(expandableContent);
        alert.getDialogPane().setExpanded(true);

        // Mostrar la alerta
        alert.showAndWait();
    }

}
