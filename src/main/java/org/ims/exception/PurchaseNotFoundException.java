package org.ims.exception;

public class PurchaseNotFoundException extends Exception {
    public PurchaseNotFoundException(String msj) {
        super(msj);
    }
    public PurchaseNotFoundException(String msj, Exception e) {
        super(msj, e);
    }
}
