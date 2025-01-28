package org.ims.exception;

public class SupplierNotFoundException extends Exception {
    public SupplierNotFoundException(String msj) {
        super(msj);
    }

    public SupplierNotFoundException(String msj, Exception e) {
        super(msj, e);
    }
}
