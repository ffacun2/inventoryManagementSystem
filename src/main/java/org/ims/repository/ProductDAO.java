package org.ims.repository;

import org.ims.db.DataBaseConnection;
import org.ims.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

public class ProductDAO{


    /**
     * Method to create a list of all products from the database, storing them in hashmap
     * by id. Each product will have a unique id, and name, price, stock, description and category.
     * @return hashmap of all products
     */
    public HashMap<Long,Product> getProduct()
    throws SQLException {
        HashMap<Long, Product> products = null;
        Product product = null;

        try (Connection conn = DataBaseConnection.getConnection()){
            String query = "SELECT (id,name,price,stock,description,category) FROM products";
            try (PreparedStatement pstm = conn.prepareStatement(query)){
                try(ResultSet rs = pstm.executeQuery()){
                     products = new HashMap<>();
                    while(rs.next()){
                        product = new Product();
                        product.setId(rs.getLong(1));
                        product.setName(rs.getString(2));
                        product.setQuantity(rs.getInt(3));
                        product.setDescription(rs.getString(4));
                        product.setCategory(rs.getString(5));
                        products.put(rs.getLong(1),product);
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
    public HashMap<Long,Product> searchProducts(String attribute)
    throws  SQLException{
        HashMap<Long,Product> products = null;
        try( Connection conn = DataBaseConnection.getConnection())
        {
            String query = "SELECT * FROM products WHERE"+
                    "CAST(id as String) LIKE ? OR "+
                    "name LIKE ? OR + "+
                    "description LIKE ? OR"+
                    "category LIKE ?";
            try (PreparedStatement pstm = conn.prepareStatement(query))
            {
                pstm.setString(1, "%"+attribute+"%");
                pstm.setString(2, "%"+attribute+"%");
                pstm.setString(3, "%"+attribute+"%");
                pstm.setString(4, "%"+attribute+"%");
                try(ResultSet rs = pstm.executeQuery()){
                    products = new HashMap<>();
                    while(rs.next()){
                        Product product = new Product();
                        product.setId(rs.getLong(1));
                        product.setName(rs.getString(2));
                        product.setQuantity(rs.getInt(3));
                        product.setDescription(rs.getString(4));
                        product.setCategory(rs.getString(5));
                        products.put(rs.getLong(1),product);
                    }
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
        boolean created = false;
        try(Connection conn = DataBaseConnection.getConnection())
        {
            String query = "INSERT INTO products (name, price, stock, description, category) VALUES (?,?,?,?,?)";
            try (PreparedStatement pstm = conn.prepareStatement(query))
            {
                pstm.setString(1, product.getName());
                pstm.setDouble(2, product.getPrice());
                pstm.setInt(3, product.getQuantity());
                pstm.setString(4, product.getDescription());
                pstm.setString(5, product.getCategory());
                pstm.executeUpdate();
                created = true;
            }
        }
        return created;
    }

    /**
     * This method updates an existing Product in the database.
     * @Return Boolean value indicating whether the product is already in the list of products in the database.
     */
    public boolean updateProduct(Product product)
    throws SQLException  {
        boolean updated = false;
        try(Connection conn = DataBaseConnection.getConnection())
        {
            String query = "UPDATE products SET name=?, price=?, stock=?, description=?, category=? WHERE id=?";
            try (PreparedStatement pstm = conn.prepareStatement(query))
            {
                pstm.setString(1, product.getName());
                pstm.setDouble(2, product.getPrice());
                pstm.setInt(3, product.getQuantity());
                pstm.setString(4, product.getDescription());
                pstm.setString(5, product.getCategory());
                pstm.setLong(6, product.getId());
                pstm.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    /**
     * This method deletes a Product from the database.
     * @Return Boolean value indicating whether the product is already in the list of products in the database.
     */
    public boolean deleteProduct(long id)
    throws SQLException  {
        boolean deleted = false;
        try(Connection conn = DataBaseConnection.getConnection())
        {
            String query = "DELETE FROM products WHERE id=?";
            try (PreparedStatement pstm = conn.prepareStatement(query))
            {
                pstm.setLong(1, id);
                pstm.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }

    /**
     * This method is called when an order arrives and the stock of a product is updated.
     */
    public void updateInventory(Long id, int quantityChange)
    throws SQLException {
        try(Connection conn = DataBaseConnection.getConnection())
        {
            String query = "UPDATE products SET stock=stock+? WHERE id=?";
            try (PreparedStatement pstm = conn.prepareStatement(query))
            {
                pstm.setInt(1, quantityChange);
                pstm.setLong(2, id);
                pstm.executeUpdate();
            }
        }
    }

    /**
     * This method is called when you want to search a product by id
     */
    public Product getProductById(long id)
    throws SQLException {
        Product product = null;
        try(Connection conn = DataBaseConnection.getConnection())
        {
            String query = "SELECT * FROM products WHERE id=?";
            try (PreparedStatement pstm = conn.prepareStatement(query))
            {
                pstm.setLong(1, id);
                try(ResultSet rs = pstm.executeQuery()){
                    if(rs.next()){
                        product = new Product();
                        product.setId(rs.getLong(1));
                        product.setName(rs.getString(2));
                        product.setQuantity(rs.getInt(3));
                        product.setDescription(rs.getString(4));
                        product.setCategory(rs.getString(5));
                    }
                }
            }
        }
        return product;
    }

}
