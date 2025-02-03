package org.ims.services;

import org.ims.model.Product;
import org.ims.repository.ProductDAO;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public String[] getColumnsName()
    throws SQLException {
        return productDAO.getColumnNames();
    }

    public List<Product> getAllProducts()
    throws SQLException {
        return productDAO.getAllProduct();
    }

    public List<Product> searchProducts(String attribute)
    throws SQLException {
        return productDAO.searchProducts(attribute);
    }
}
