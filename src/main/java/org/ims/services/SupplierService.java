package org.ims.services;

import org.ims.exception.InvalidImputDataException;
import org.ims.exception.SupplierNotFoundException;
import org.ims.model.Supplier;
import org.ims.repository.SupplierDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierService {

    private final SupplierDAO supplierDAO;

    public SupplierService (SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    /**
     * This method validate the introduced values for creating a new user.
     * @param name the username must be unique and greater than six length and alphanumeric.
     * @param lastname the password must be greater than 6  and less tan 18 length, alphanumeric and at least one special characters.
     * @param email the email must be unique and valid.
     * @param address the role is the permissions
     * @param phone name of the person
     * @param location last name of the person
     */
    public Supplier validateSupplier(String name, String lastname, String email, String address, String phone, String location) {
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            throw new InvalidImputDataException("invalid email.");
        if (name == null || !name.matches("^[A-ZÁÉÍÓÚÑa-záéíóúñ]{2,}(?:\\s[A-ZÁÉÍÓÚÑa-záéíóúñ]{2,})*$"))
            throw new InvalidImputDataException("invalid name");
        if (lastname == null ||!lastname.matches("^[A-ZÁÉÍÓÚÑa-záéíóúñ]{2,}(?:\\s[A-ZÁÉÍÓÚÑa-záéíóúñ]{2,})*$"))
            throw new InvalidImputDataException("invalid lastname");
        if (phone == null ||!phone.matches("^\\+?[0-9]{1,3}?[ -]?(\\(?[0-9]{2,4}\\)?[ -]?[0-9]{4,8})$"))
            throw new InvalidImputDataException("invalid phone number.");
        if (location == null ||!location.matches("^[A-ZÁÉÍÓÚÑa-záéíóúñ]{2,}(?:[\\s'-][A-ZÁÉÍÓÚÑa-záéíóúñ]{2,})*$"))
            throw new InvalidImputDataException("invalid location.");
        if (name.length() < 3 || name.length() > 30)
            throw new InvalidImputDataException("name length must be between 3 and 30.");
        if (lastname.length() < 3 || lastname.length() > 30)
            throw new InvalidImputDataException("lastname length must be between 3 and 30.");
        if (address == null || !address.matches("^[A-ZÁÉÍÓÚÑa-záéíóúñ0-9\\s.,#/-]{5,}$"))
            throw new InvalidImputDataException("address cannot be empty.");

        return new Supplier(name,lastname,email,address,phone,location);
    }

    /**
     * This method returns the names of the columns from the user's table
     * @return lista of String with column names
     */
    public String[] getColumnNames()
    throws SQLException {
        return supplierDAO.getColumnNames();
    }

    /**
     * Calls the method that return a list of all users from the user table for showing
     * in the table view.
     */
    public ArrayList<Supplier> getAllSupplier()
    throws SQLException {
        return supplierDAO.getAllSuppliers();
    }


    /**
     * Calls the method to create a new previously verified user
     * @param supplier with parameters validated
     */
    public boolean createSupplier(Supplier supplier)
    throws SQLException {
        return supplierDAO.createSupplier(supplier);
    }

    /**
     * Calls the method to update an existing user in the database
     *
     * @param id
     * @param supplier with parameters validated
     */
    public boolean updateSupplier(Long id, Supplier supplier)
    throws SQLException,
    SupplierNotFoundException {
        return supplierDAO.updateSupplier(id,supplier);
    }

    /**
     * Calls the method to delete an existing user from the database
     * @param supplier with parameters validated
     */
    public boolean deleteUser(Supplier supplier)
    throws SQLException {
        return supplierDAO.deleteSupplier(supplier);
    }

}
