package org.ims.repository;

import org.ims.db.DataBaseConnection;
import org.ims.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ProductDAO{


    /**
     * Method to create a list of all products from the database, storing them in hashmap
     * by id. Each product will have a unique id, and name, price, stock, description and category.
     * @return hashmap of all products
     */
    public HashMap<Long,Product> getProduct(){
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
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        catch (SQLException e)
        {
         e.printStackTrace(System.out);
        }
        return products;
    }
}
