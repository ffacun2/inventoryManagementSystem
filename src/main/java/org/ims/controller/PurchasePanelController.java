package org.ims.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ims.model.Purchase;
import org.ims.model.PurchaseInfo;
import org.ims.repository.PurchaseDAO;
import org.ims.services.PurchaseService;

import java.sql.SQLException;

public class PurchasePanelController {

    @FXML
    private TableView<Purchase> purchaseTable;
    @FXML
    private TableView<PurchaseInfo> productTable;

    private final PurchaseService purchaseService;

    public PurchasePanelController() {
        this.purchaseService = new PurchaseService(new PurchaseDAO());
    }

    @FXML
    protected void initialize() {
        loadColumnPurchases();
        loadColumnProducts();
        loadPurchaseTable();
    }

    /**
     * Settings the purchase columns for the purchase table.
     * Such as id, user name, supplier name, date, total price, state
     */
    protected void loadColumnPurchases() {
        String[] columns = {"id","username","suppliername","date","total","state"};
            for (String columnName : columns) {
                TableColumn<Purchase, String> column = new TableColumn<>(columnName);
                if(columnName.equals("username")) {
                    column.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getUser().getName() +" "+cellData.getValue().getUser().getLastname())
                    );
                }
                else if (columnName.equals("suppliername")) {
                    column.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getSupplier().getName() +" "+cellData.getValue().getSupplier().getLastname())
                    );
                }
                else {
                    column.setCellValueFactory(new PropertyValueFactory<>(columnName));
                }
                    purchaseTable.getColumns().add(column);
            }
    }

    /**
     * Settings the product column for the list of products in the purchase.
     * the columns name are id, name, quantity, unit price
     */
    protected void loadColumnProducts() {
        String[] columns = {"id","name","quantity","unitPrice"};
        for (String columnName : columns) {
            TableColumn<PurchaseInfo, String> column = new TableColumn<>(columnName);
            if(columnName.equals("name")) {
                column.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getProduct().getName())
                );
            }
            else {
                column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            }
            productTable.getColumns().add(column);
        }
    }


    /**
     * Loads the purchase information from the database to the table
     */
    protected void loadPurchaseTable() {
        try {
            purchaseTable.setItems(
                    FXCollections.observableArrayList(
                            purchaseService.getAllPurchase()
                    ));
        }
        catch (SQLException e) {
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * when selecting a purchase from the purchase table , the products from that purchase are loaded.
     * @param purchaseId Id of the purchase selected
     */
    protected void showProductTable(long purchaseId) {
        try {
            productTable.setItems(
                    FXCollections.observableArrayList(
                            purchaseService.getPurchaseInfoByPurchaseId(purchaseId)
                    )
            );
        }
        catch (SQLException e) {
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Handle the action click on the refresh button and obtain the purchase selected in the table.
     * If the purchase is not null then call the refresh method.
     */
    @FXML
    protected void handleClickTable () {
        Purchase purchase = purchaseTable.getSelectionModel().getSelectedItem();
        if(purchase != null) {
            showProductTable(purchase.getId());
        }
    }

    /**
     * refresh the purchase table with de latest moves and clear the product table.
     */
    @FXML
    protected void refreshTable() {
        purchaseTable.getItems().clear();
        loadPurchaseTable();
        productTable.getItems().clear();
    }


    protected void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(message);
        alert.showAndWait();
    }



}
