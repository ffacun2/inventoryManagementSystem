package org.ims.repository;

import org.ims.db.DataBaseConnection;
import org.ims.exception.SupplierNotFoundException;
import org.ims.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {


    /**
     * Method to create a new supplier in the database
     * @param supplier object with the attributes validates
     * @return boolean condition to indicate whether the supplier is already in the database
     */
    public boolean createSupplier(Supplier supplier)
    throws SQLException {

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "INSERT INTO suppliers (name, lastname, email, address, phone, location) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement prsm = conn.prepareStatement(query)) {
                prsm.setString(1, supplier.getName());
                prsm.setString(2, supplier.getLastname());
                prsm.setString(3, supplier.getEmail());
                prsm.setString(4, supplier.getAddress());
                prsm.setString(5, supplier.getPhone());
                prsm.setString(6, supplier.getLocation());

                int rowsInserted = prsm.executeUpdate();

                return rowsInserted > 0;
            }
        }
    }


    /**
     * This method returns the names of the columns from the suppliers table.
     * @return Lista of column names
     */
    public String[] getColumnNames()
    throws SQLException {
        String query = "SELECT * FROM suppliers LIMIT 1";

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

    /**
     * This method queries the database to return all registered suppliers.
     * @return all registered suppliers
     */
    public List<Supplier> getAllSuppliers()
    throws SQLException  {
        ArrayList<Supplier> suppliers = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "SELECT * FROM suppliers";
            try (PreparedStatement prsm = conn.prepareStatement(query)) {
                try(ResultSet rs = prsm.executeQuery()) {
                    while(rs.next()) {
                        Supplier supplier = new Supplier();
                        supplier.setId(rs.getLong("id"));
                        supplier.setName(rs.getString("name"));
                        supplier.setLastname(rs.getString("lastname"));
                        supplier.setEmail(rs.getString("email"));
                        supplier.setAddress(rs.getString("address"));
                        supplier.setPhone(rs.getString("phone"));
                        supplier.setLocation(rs.getString("location"));
                        suppliers.add(supplier);
                    }
                }
            }
        }
        return suppliers;
    }

    /**
     * This method queries the database to delete an existing supplier selected in the table through the id.
     * @param supplier the supplier to be deleted selected in the table
     */
    public boolean deleteSupplier(Supplier supplier)
    throws SQLException  {

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "DELETE FROM suppliers WHERE id = ?";
            try (PreparedStatement prsm = conn.prepareStatement(query)) {
                prsm.setLong(1, supplier.getId());
                prsm.executeUpdate();
                return true;
            }
        }
    }

    /**
     * This method queries the database to update an existing supplier's details.
     *
     * @param id the id of the supplier to update
     * @param supplier the updated supplier object
     */
    public boolean updateSupplier(Long id, Supplier supplier)
    throws SQLException,
    SupplierNotFoundException {

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "UPDATE suppliers SET name = ?, lastname =?, email =?, address =?, location =?, phone =? WHERE id = ?";
            try (PreparedStatement prsm = conn.prepareStatement(query)) {
                prsm.setString(1, supplier.getName());
                prsm.setString(2, supplier.getLastname());
                prsm.setString(3, supplier.getEmail());
                prsm.setString(4, supplier.getEmail());
                prsm.setString(5, supplier.getLocation());
                prsm.setString(6, supplier.getPhone());
                prsm.setLong(7,id);

                if (prsm.executeUpdate() == 0 )
                    throw new SupplierNotFoundException("Supplier not found in database");
                return true;
            }
        }
    }

}
