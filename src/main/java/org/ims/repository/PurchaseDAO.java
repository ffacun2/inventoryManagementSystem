package org.ims.repository;

import org.ims.db.DataBaseConnection;
import org.ims.model.*;
import org.ims.utils.StatePurchase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {


   public boolean createPurchase(Purchase purchase)
   throws SQLException {
       String sqlPurchase = "INSERT INTO purchases (user_id, supplier_id, date, state, total) VALUES (?,?,?,?,?)";
       String sqlPurchaseInfo = "INSERT INTO purchaseInfo (purchase_id, product_id, price, quantity) VALUES (?,?,?,?)";

       try (Connection conn = DataBaseConnection.getConnection()) {
           try (PreparedStatement stmtPurchase = conn.prepareStatement(sqlPurchase);
                PreparedStatement stmtPurchaseInfo = conn.prepareStatement(sqlPurchaseInfo))
           {

               stmtPurchase.setLong(1, purchase.getUser().getId());
               stmtPurchase.setLong(2, purchase.getSupplier().getId());
               stmtPurchase.setDate(3, Date.valueOf(purchase.getDate()));
               stmtPurchase.setString(4, purchase.getState().toString());
               stmtPurchase.setDouble(5, purchase.getTotal());

               stmtPurchase.executeUpdate();

               ResultSet rs = stmtPurchase.getGeneratedKeys();
               if (!rs.next())
                   throw new SQLException("Creating purchase failed, no ID obtained.");

               long purchaseId = rs.getLong(1);

               for (PurchaseInfo info : purchase.getPurchaseInfo())
               {
                   stmtPurchaseInfo.setLong(1, purchaseId);
                   stmtPurchaseInfo.setLong(2, info.getProduct().getId());
                   stmtPurchaseInfo.setDouble(3, info.getUnitPrice());
                   stmtPurchaseInfo.setInt(4, info.getQuantity());
                   stmtPurchaseInfo.executeUpdate();
               }
               return true;
           }
       }
   }

    /**
     * Queries the database for all purchases and returns them
     * @return a list of purchase
     * @throws SQLException
     */
   public List<Purchase> getAllPurchase ()
   throws SQLException {
       List<Purchase> purchases = new ArrayList<>();

       String sql = """
               SELECT
                    c.id,
                    c.user_id,
                    u.name AS username,
                    u.lastname AS userlastname,
                    c.supplier_id,
                    s.name AS suppliername,
                    s.lastname AS supplierlastname,
                    c.date,
                    c.totalcost,
                    c.state
                FROM purchases c
                JOIN users u ON c.user_id = u.id
                JOIN suppliers s ON c.supplier_id = s.id
               """;

       try (Connection conn = DataBaseConnection.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql)) {

           while (rs.next()) {
               User user = new User();
               user.setId(rs.getLong("user_id"));
               user.setName(rs.getString("username"));
               user.setLastname(rs.getString("userlastname"));

               Supplier supplier = new Supplier();
               supplier.setId(rs.getLong("supplier_id"));
               supplier.setName(rs.getString("suppliername"));
               supplier.setLastname(rs.getString("supplierlastname"));

               Purchase purchase = new Purchase(
                       rs.getLong("id"),
                       user,
                       supplier,
                       rs.getDate("date").toLocalDate(),
                       new ArrayList<>(),
                       rs.getDouble("totalcost"),
                       StatePurchase.valueOf(rs.getString("state"))
               );
                purchases.add(purchase);
           }
       }
       return purchases;
   }

    /**
     * Queries the database for all info purchase with the given purchase id. This returns the purchase
     * information with the product chosen by the user, quantity and price.
     * @param purchaseId
     * @return the purchase information with the product chosen by the user, quantity and price
     * @throws SQLException
     */
   public List<PurchaseInfo> getPurchaseInfoByPurchaseId(Long purchaseId)
   throws SQLException {
       List<PurchaseInfo> infoList = new ArrayList<>();
       String query = """
               SELECT
                    c.product_id,
                    p.name AS productname,
                    c.unitprice,
                    c.quantity,
                    c.id
                FROM
                    purchase_info c
                JOIN
                    products p ON c.product_id = p.id
                WHERE
                    c.purchase_id = ?
               """;

       try (Connection conn = DataBaseConnection.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(query)) {
           pstmt.setLong(1, purchaseId);
           ResultSet rs = pstmt.executeQuery();

           while (rs.next()) {
               Product product = new Product();
               product.setId(rs.getLong("product_id"));
               product.setName(rs.getString("productname"));

               PurchaseInfo info = new PurchaseInfo(
                       rs.getLong("id"),
                       product,
                       rs.getInt("quantity"),
                       rs.getDouble("unitprice")
               );
               infoList.add(info);
           }
       }
       return infoList;
   }





}
