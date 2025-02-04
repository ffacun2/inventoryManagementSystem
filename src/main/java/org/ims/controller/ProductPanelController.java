package org.ims.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ims.model.Product;
import org.ims.repository.ProductDAO;
import org.ims.services.ProductService;

import javax.swing.*;
import java.sql.SQLException;

public class ProductPanelController {

    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TextField searchProduct;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField costTextField;
    @FXML
    private TextArea descriptionTextField;

    private final ProductService productService;


    public ProductPanelController () {
        this.productService = new ProductService(new ProductDAO());
    }


    @FXML
    protected void initialize() {
        loadColumns();
        loadProductTable();
    }

    protected void loadColumns() {
        String[] columns = {"id", "name", "category", "price", "description"};
        for (String name: columns) {
            TableColumn<Product,String> column = new TableColumn<>(name);
            column.setCellValueFactory(new PropertyValueFactory<>(name));
            productTableView.getColumns().add(column);
        }
    }

    protected void loadProductTable() {
        try {
            productTableView.setItems(
                    FXCollections.observableArrayList(
                            productService.getAllProducts()
                    )
            );
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    protected void handleMouseClicked() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct!=null) {
            nameTextField.setText(selectedProduct.getName());
            categoryTextField.setText(selectedProduct.getCategory());
            priceTextField.setText(String.valueOf(selectedProduct.getPrice()));
            descriptionTextField.setText(selectedProduct.getDescription());
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
        productTableView.setItems(filteredData);
    }

    @FXML
    protected void updateProduct() {
        Product productSelected = productTableView.getSelectionModel().getSelectedItem();
        Product product = productService.validateProduct(
                nameTextField.getText(),
                priceTextField.getText(),
                categoryTextField.getText(),
                descriptionTextField.getText()
        );
        try {
            productService.updateProduct(productSelected.getId(),product);
            loadProductTable();
            clearFields();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    protected void deleteProduct() {
        try {
            long productId = productTableView.getSelectionModel().getSelectedItem().getId();
            productService.deleteProduct(productId);
            loadProductTable();
            clearFields();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    protected void clearFields() {
        nameTextField.clear();
        categoryTextField.clear();
        priceTextField.clear();
        descriptionTextField.clear();
        productTableView.getSelectionModel().clearSelection();
    }
}
