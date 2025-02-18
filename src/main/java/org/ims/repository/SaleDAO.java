package org.ims.repository;

import org.ims.db.DataBaseConnection;
import org.ims.model.Product;
import org.ims.model.Sale;
import org.ims.model.SaleInfo;
import org.ims.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {


    /**
     * Get the sales made
     * @Return a list of sales made
     * @throws SQLException occurs when trying to get sales made in database
     */
    public List<Sale> getAllSales()
    throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String query = """
                SELECT
                    s.id,
                    u.id AS user_id,
                    u.name,
                    u.lastname,
                    s.date,
                    s.total
                    FROM sales s
                    JOIN users u ON s.user_id = u.id
                """;
        try (Connection conn = DataBaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("user_id"));
                user.setName(rs.getString("name"));
                user.setLastname(rs.getString("lastname"));


                Sale sale = new Sale();
                sale.setId(rs.getLong("id"));
                sale.setUser(user);
                sale.setDate(rs.getDate("date"));
                sale.setTotal(rs.getDouble("total"));
                sales.add(sale);
            }
        }
        return sales;
    }

    public List<SaleInfo> getAllSalesInfo()
    throws SQLException {
        List<SaleInfo> salesInfo = new ArrayList<>();
        String query = """
                """;

        try (Connection conn = DataBaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("product_id"));
                product.setName(rs.getString("product_name"));
                product.setDescription(rs.getString("product_description"));

                SaleInfo saleInfo = new SaleInfo();
                saleInfo.setId(rs.getLong("id"));
                saleInfo.setProduct(product);
                saleInfo.setQuantity(rs.getInt("quantity"));
                saleInfo.setUnitPrice(rs.getDouble("unit_price"));



                salesInfo.add(saleInfo);
            }
        }
        return salesInfo;
    }


    public void delateSale()
    throws SQLException {

    }

    public void updateSale()
    throws SQLException {

    }
}
