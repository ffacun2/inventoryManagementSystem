package org.ims.repository;

import org.ims.db.DataBaseConnection;
import org.ims.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO{


    /**
     * Method to create a list of all products from the database, storing them in hashmap
     * by id. Each product will have a unique id, and name, price, stock, description and category.
     * @return hashmap of all products
     */
    public List<Product> getAllProduct()
    throws SQLException {
        List<Product> products;
        Product product;

        try (Connection conn = DataBaseConnection.getConnection()){
            String query = "SELECT * FROM products";
            try (PreparedStatement pstm = conn.prepareStatement(query)){
                try(ResultSet rs = pstm.executeQuery()){
                     products = new ArrayList<>();
                    while(rs.next()){
                        product = new Product();
                        product.setId(rs.getLong("id"));
                        product.setName(rs.getString("name"));
                        product.setStock(rs.getInt("stock"));
                        product.setPrice(rs.getDouble("price"));
                        product.setDescription(rs.getString("description"));
                        product.setCategory(rs.getString("category"));
                        products.add(product);
                    }
                }
            }
        }
        return products;
    }


    /**
     * This method searches a product where some attribute matches the given word
     * @return sets of products with the given word
     */
    public List<Product> searchProducts(String attribute)
    throws  SQLException{
        ArrayList<Product> products;
        String query = "SELECT * FROM products WHERE"+
                "CAST(id as String) LIKE ? OR "+
                "name LIKE ? OR + "+
                "description LIKE ? OR"+
                "category LIKE ?";
        try( Connection conn = DataBaseConnection.getConnection();
            PreparedStatement pstm = conn.prepareStatement(query)) {
                pstm.setString(1, "%"+attribute+"%");
                pstm.setString(2, "%"+attribute+"%");
                pstm.setString(3, "%"+attribute+"%");
                pstm.setString(4, "%"+attribute+"%");
                try(ResultSet rs = pstm.executeQuery()){
                    products = new ArrayList<>();
                    while(rs.next()){
                        Product product = new Product();
                        product.setId(rs.getLong(1));
                        product.setName(rs.getString(2));
                        product.setStock(rs.getInt(3));
                        product.setDescription(rs.getString(4));
                        product.setCategory(rs.getString(5));
                        products.add(product);
                    }
                }
            }
        return products;
    }

    /**
     * This method creates a new Product and add it to the list of productos in the database.
     * @Return Boolean value indicating whether the product is already in the list of products in the database.
     */
    public boolean createProduct(Product product)
    throws SQLException {
        String query = "INSERT INTO products (name, price, stock, description, category) VALUES (?,?,?,?,?)";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement pstm = conn.prepareStatement(query)) {
                pstm.setString(1, product.getName());
                pstm.setDouble(2, product.getPrice());
                pstm.setInt(3, product.getStock());
                pstm.setString(4, product.getDescription());
                pstm.setString(5, product.getCategory());
                pstm.executeUpdate();
                return true;
            }
    }

    /**
     * This method updates an existing Product in the database.
     * @Return Boolean value indicating whether the product is already in the list of products in the database.
     */
    public boolean updateProduct(Product product)
    throws SQLException  {
        String query = "UPDATE products SET name=?, price=?, stock=?, description=?, category=? WHERE id=?";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement pstm = conn.prepareStatement(query)) {
                pstm.setString(1, product.getName());
                pstm.setDouble(2, product.getPrice());
                pstm.setInt(3, product.getStock());
                pstm.setString(4, product.getDescription());
                pstm.setString(5, product.getCategory());
                pstm.setLong(6, product.getId());
                pstm.executeUpdate();
                return true;
            }
    }

    /**
     * This method deletes a Product from the database.
     * @Return Boolean value indicating whether the product is already in the list of products in the database.
     */
    public boolean deleteProduct(long id)
    throws SQLException  {
        boolean deleted;
        String query = "DELETE FROM products WHERE id=?";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement pstm = conn.prepareStatement(query))
            {
                pstm.setLong(1, id);
                pstm.executeUpdate();
                return true;
            }
    }


    /**
     * This method is called when you want to search a product by id
     */
    public Product getProductById(long id)
    throws SQLException {
        Product product = null;
        String query = "SELECT * FROM products WHERE id=?";
        
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement pstm = conn.prepareStatement(query)) {
                pstm.setLong(1, id);
                try(ResultSet rs = pstm.executeQuery()){
                    if(rs.next()){
                        product = new Product();
                        product.setId(rs.getLong(1));
                        product.setName(rs.getString(2));
                        product.setStock(rs.getInt(3));
                        product.setDescription(rs.getString(4));
                        product.setCategory(rs.getString(5));
                    }
                }
            }
        return product;
    }

    /**
     * update the stock product when the purchase is received
     * @param id the id of product to update
     * @param quantity the quantity of the product to update
     * @throws SQLException if an SQLException occurs during the update process
     */
    public void updateStock(Long id, int quantity)
    throws SQLException {
        try(Connection conn = DataBaseConnection.getConnection()) {
            String query = "UPDATE products SET stock=stock+? WHERE id=?";
            try (PreparedStatement pstm = conn.prepareStatement(query)) {
                pstm.setInt(1, quantity);
                pstm.setLong(2, id);
                pstm.executeUpdate();
            }
        }
    }


    /**
     * Returns the names of the columnes of the products in the database
     * @return array list of column names
     * @throws SQLException occurs when no columns are available in the database
     */
    public String[] getColumnNames()
    throws SQLException {
        String query = "SELECT * FROM products LIMIT 1";

        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement prsm = conn.prepareStatement(query);
            ResultSet rs = prsm.executeQuery())
        {
            String[] columnNames = new String[rs.getMetaData().getColumnCount()];
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                columnNames[i] = rs.getMetaData().getColumnName(i+1);
            }
            return columnNames;
        }
    }

}
