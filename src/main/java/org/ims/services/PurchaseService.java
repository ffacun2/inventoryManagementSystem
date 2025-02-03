package org.ims.services;

import org.ims.exception.PurchaseNotFoundException;
import org.ims.model.Purchase;
import org.ims.model.PurchaseInfo;
import org.ims.model.Supplier;
import org.ims.model.User;
import org.ims.repository.ProductDAO;
import org.ims.repository.PurchaseDAO;
import org.ims.utils.StatePurchase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PurchaseService {

    private final PurchaseDAO purchaseDAO;
    private final ProductDAO productDAO;

    public PurchaseService(PurchaseDAO purchaseDAO, ProductDAO productDAO) {
        this.purchaseDAO = purchaseDAO;
        this.productDAO = productDAO;
    }

    public boolean createPurchase(User user, Supplier supplier, List<PurchaseInfo> infoList)
    throws SQLException {
        if (user == null || supplier == null || infoList.isEmpty())
            throw new IllegalArgumentException("Invalid purchase data");

        double total = infoList.stream().mapToDouble(PurchaseInfo::getSubTotal).sum();
        Purchase purchase = new Purchase(user,supplier, LocalDate.now(),infoList,total);

        return purchaseDAO.createPurchase(purchase);
    }

    public List<Purchase> getAllPurchase()
    throws SQLException {
        return purchaseDAO.getAllPurchase();
    }

    public List<PurchaseInfo> getPurchaseInfoByPurchaseId(Long purchaseId)
    throws SQLException {
        return purchaseDAO.getPurchaseInfoByPurchaseId(purchaseId);
    }


    public void updatePurchaseState(long purchaseId, StatePurchase state)
    throws SQLException {
        purchaseDAO.updatePurchase(purchaseId,state);
    }

    /**
     * By selecting a purchase as received , the products will be added to the list of products
     * @param purchaseId the purchase identifier
     * @throws SQLException if an error occurs during processing the purchase request
     * @throws PurchaseNotFoundException If the purchase was not found in the database
     */
    public void purchaseReceived (long purchaseId)
    throws SQLException,
    PurchaseNotFoundException,
    IllegalStateException {
        Purchase purchase = purchaseDAO.getPurchaseById(purchaseId);
    //If for any reason the purchase is null
        if (purchase == null)
            throw new PurchaseNotFoundException("Purchase not found");
    //add purchase products only if the purchase is pending
        if (!StatePurchase.PENDING.equals(purchase.getState()))
            throw new IllegalStateException("Purchase state not is pending");

        List<PurchaseInfo> products = purchaseDAO.getPurchaseInfoByPurchaseId(purchaseId);

        for (PurchaseInfo product : products) {
            productDAO.updateStock(product.getProduct().getId(), product.getQuantity());
        }

        updatePurchaseState(purchaseId,StatePurchase.RECEIVED);
    }


}
