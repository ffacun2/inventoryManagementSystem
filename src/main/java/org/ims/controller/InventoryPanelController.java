package org.ims.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ims.model.Product;
import org.ims.repository.ProductDAO;
import org.ims.services.ProductService;

import javax.swing.*;
import java.sql.SQLException;

public class InventoryPanelController {

    @FXML
    private TextField searchProduct;
    @FXML
    private TableView<Product> productTable;

    private final ProductService productService;

    public InventoryPanelController() {
        this.productService = new ProductService(new ProductDAO());
    }

    @FXML
    protected void initialize()
    throws SQLException {
        loadColumnsNames();
        loadProductTable();
    }

    protected void loadColumnsNames()
    throws SQLException {
        try {
            for(String columnName : productService.getColumnsName()) {
                TableColumn<Product,String> column = new TableColumn<Product,String>(columnName);
                column.setCellValueFactory(new PropertyValueFactory<>(columnName));
                productTable.getColumns().add(column);
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void loadProductTable()
    throws SQLException {
        try {
            productTable.setItems(
                    FXCollections.observableArrayList(
                            productService.getAllProducts()
                    )
            );
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    protected void searchProductsTable() {
        String searchText = searchProduct.getText().toLowerCase();
        ObservableList<Product> filteredData = FXCollections.observableArrayList();

        try {
            for (Product product : productService.getAllProducts()) {
                if (product.getName().toLowerCase().contains(searchText) ||
                        product.getDescription().toLowerCase().contains(searchText) ||
                        String.valueOf(product.getPrice()).contains(searchText) ||
                        String.valueOf(product.getStock()).contains(searchText) ||
                        product.getCategory().toLowerCase().contains(searchText))
                {
                    filteredData.add(product);
                }
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        productTable.setItems(filteredData);
    }


    @FXML
    protected void createProduct () {
    }

    @FXML
    protected void updateProduct() {
    }

    @FXML
    protected void deleteProduct() {
    }

    @FXML
    protected void clearFields() {
    }

}
