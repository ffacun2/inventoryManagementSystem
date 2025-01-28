package org.ims.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.ims.exception.InvalidImputDataException;
import org.ims.exception.SupplierNotFoundException;
import org.ims.model.Supplier;
import org.ims.repository.SupplierDAO;
import org.ims.services.SupplierService;

import java.sql.SQLException;
import java.util.Optional;

public class SupplierPanelController {

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldAddress;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private TextField textFieldLocation;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private TableView<Supplier> supplierTable;



    private final SupplierService supplierService;


    public SupplierPanelController() {
        this.supplierService = new SupplierService(new SupplierDAO());
    }

    @FXML
    protected void initialize() {
        loadColumns();
        loadTable();
    }

    @FXML
    protected void clearFields(){
        textFieldName.clear();
        textFieldLastName.clear();
        textFieldEmail.clear();
        textFieldAddress.clear();
        textFieldPhone.clear();
        textFieldLocation.clear();
        supplierTable.getSelectionModel().clearSelection();
    }

    protected void loadColumns () {
        try {
            for (String colum : supplierService.getColumnNames()) {
                TableColumn<Supplier, String> column = new TableColumn<>(colum);
                column.setCellValueFactory(new PropertyValueFactory<>(colum));
                supplierTable.getColumns().add(column);
            }
        } catch (Exception e) {
            showAlert("Error loading column names", Alert.AlertType.ERROR);
            System.err.println(e.getMessage());
        }
    }

    protected void loadTable () {
        try {
            supplierTable.setItems(
                    FXCollections.observableArrayList(
                            supplierService.getAllSupplier()
                    )
            );
        } catch (Exception e) {
            showAlert("Error loading suppliers", Alert.AlertType.ERROR);
        }
    }

    /**
     * Filters the suppliers list by attributes provided in the search field;
     */
    @FXML
    protected void search() {
        String searchText = textFieldSearch.getText().toLowerCase();
        ObservableList<Supplier> filteredData = FXCollections.observableArrayList();

        try {
            for (Supplier supplier : supplierService.getAllSupplier()) {
                if (supplier.getName().toLowerCase().contains(searchText) ||
                        supplier.getLastname().toLowerCase().contains(searchText) ||
                        supplier.getEmail().toLowerCase().contains(searchText) ||
                        supplier.getAddress().toLowerCase().contains(searchText) ||
                        supplier.getPhone().toLowerCase().contains(searchText) ||
                        supplier.getLocation().contains(searchText))
                {
                    filteredData.add(supplier);
                }
            }
        }
        catch (Exception e) {
            showAlert("An error occurred while searching", Alert.AlertType.ERROR);
        }
        supplierTable.setItems(filteredData);
    }

    @FXML
    public void handleClickMouseEvent(MouseEvent mouseEvent) {
        Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
        if (supplier!= null) {
            textFieldName.setText(supplier.getName());
            textFieldLastName.setText(supplier.getLastname());
            textFieldEmail.setText(supplier.getEmail());
            textFieldAddress.setText(supplier.getAddress());
            textFieldPhone.setText(supplier.getPhone());
            textFieldLocation.setText(supplier.getLocation());
        }
    }

    @FXML
    protected void createSupplier () {
        if (supplierTable.getSelectionModel().getSelectedItems().isEmpty()) {
            Supplier supplier = validateAttribute(
                    textFieldName.getText(),
                    textFieldLastName.getText(),
                    textFieldEmail.getText(),
                    textFieldAddress.getText(),
                    textFieldPhone.getText(),
                    textFieldLocation.getText()
            );
            if (supplier != null) {
                try {
                    supplierService.createSupplier(supplier);
                    showAlert("Supplier added successfully", Alert.AlertType.INFORMATION);
                    clearFields();
                    loadTable();
                } catch (Exception e) {
                    showAlert(e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        }
    }

    private Supplier validateAttribute(String name, String lastname, String email, String address, String phone, String location) {
        try {
            return supplierService.validateSupplier(name, lastname, email, address, phone, location);
        }
        catch ( InvalidImputDataException e) {
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }


    @FXML
    protected void updateSupplier () {
        Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
        if (supplier!= null) {
            Supplier updatedSupplier = validateAttribute(
                    textFieldName.getText(),
                    textFieldLastName.getText(),
                    textFieldEmail.getText(),
                    textFieldAddress.getText(),
                    textFieldPhone.getText(),
                    textFieldLocation.getText()
            );
            if (updatedSupplier!= null) {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Update Supplier");
                    alert.setContentText("Are you sure you want to update this supplier?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        supplierService.updateSupplier(supplier.getId(), updatedSupplier);
                    }
                    showAlert("Supplier updated successfully", Alert.AlertType.INFORMATION);
                    clearFields();
                    loadTable();
                }
                catch (SupplierNotFoundException e) {
                    showAlert("Supplier not found", Alert.AlertType.INFORMATION);
                }
                catch (SQLException e) {
                    showAlert(e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    protected void deleteSupplier () {
        if (!supplierTable.getSelectionModel().getSelectedItems().isEmpty())
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleting supplier");
                alert.setHeaderText("Are you sure that you want to delete this supplier?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    supplierService.deleteUser(supplierTable.getSelectionModel().getSelectedItem());
                    loadTable();
                    clearFields();
                }
            }
            catch (SQLException e) {
                showAlert(e.getMessage(), Alert.AlertType.ERROR);
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
