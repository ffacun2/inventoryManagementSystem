package org.ims.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ims.exception.PurchaseNotFoundException;
import org.ims.model.Purchase;
import org.ims.model.PurchaseInfo;
import org.ims.repository.ProductDAO;
import org.ims.repository.PurchaseDAO;
import org.ims.services.PurchaseService;
import org.ims.utils.StatePurchase;

import javax.swing.*;
import java.sql.SQLException;

public class PurchasePanelController {

    @FXML
    private TableView<Purchase> purchaseTable;
    @FXML
    private TableView<PurchaseInfo> productTable;

    private final PurchaseService purchaseService;

    public PurchasePanelController() {
        this.purchaseService = new PurchaseService(new PurchaseDAO(),new ProductDAO());
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
            showAlert(e.getMessage(), JOptionPane.ERROR_MESSAGE);
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
            showAlert(e.getMessage(), JOptionPane.ERROR_MESSAGE);
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


    /**
     * By pressing the received button, the list of products for the pending purchase will be added to
     * the stock product and the purchase will be indicated as received
     */
    @FXML
    protected void handleClickReceived () {
        try {
            int rta = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure that you want to mark this purchase as received?",
                    "Marking purchase as received",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (rta == JOptionPane.YES_OPTION) {  // if user click OK
                 purchaseService.purchaseReceived(purchaseTable.getSelectionModel().getSelectedItem().getId());
                 refreshTable();
                showAlert("Purchase marked as received", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (IllegalStateException | PurchaseNotFoundException e) {
            showAlert(e.getMessage(),JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e) {
            showAlert(e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * By pressing the rejected button, the pending purchase will be marked as rejected
     */
    @FXML
    protected void handleClickRejected () {
        try {
            int rta = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure that you want to mark this purchase as rejected?",
                    "Marking purchase as rejected",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (rta == JOptionPane.YES_OPTION) {// if user click OK
                purchaseService.updatePurchaseState(purchaseTable.getSelectionModel().getSelectedItem().getId(), StatePurchase.REJECTED);
                refreshTable();
                showAlert("Purchase marked as rejected", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            showAlert(e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }


    protected void showAlert(String message,int typeError ) {
        JOptionPane.showMessageDialog(null, message, "Information",typeError);
    }



}
