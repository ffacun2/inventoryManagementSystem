package org.ims.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ims.model.SaleInfo;
import org.ims.repository.SaleDAO;
import org.ims.services.SaleService;

import javax.swing.*;
import java.sql.SQLException;

public class SalePanelController {

    @FXML
    private TableView<SaleInfo> saleTable;
    @FXML
    private ObservableList<SaleInfo> saleList = FXCollections.observableArrayList();
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField quantityTextField;

    private final SaleService saleService;

    public SalePanelController() {
        this.saleService = new SaleService(new SaleDAO());
    }

    @FXML
    protected void initialize() throws SQLException {
        loadColumnsSales();
        saleTable.setItems(saleList);
    }

    private void loadColumnsSales() {
        String[] columns = {"code","name","description","quantity","price"};
        for (String name: columns) {
            TableColumn<SaleInfo,String> column = new TableColumn<>(name);
            column.setCellValueFactory(new PropertyValueFactory<>(name));
            saleTable.getColumns().add(column);
        }
    }

    @FXML
    protected void addSale()
    throws SQLException {
        saleList.add(new SaleInfo());
    }

    protected void deleteSaleSelected() {
        SaleInfo sale = saleTable.getSelectionModel().getSelectedItem();
        if (sale != null ) {
            saleList.remove(sale);
            clearField();
        }
        else {
            JOptionPane.showMessageDialog(null,"You must select a product to delete");
        }
    }

    protected void handleMouseClickTable () {
        SaleInfo sale = saleTable.getSelectionModel().getSelectedItem();
        if (sale!= null ) {
            codeTextField.setText(String.valueOf(sale.getProduct().getId()));
            quantityTextField.setText(String.valueOf(sale.getQuantity()));
        }
    }


    private void clearField () {
        codeTextField.clear();
        quantityTextField.clear();
        saleTable.getSelectionModel().clearSelection();
    }

}
