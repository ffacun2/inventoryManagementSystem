package org.ims.services;

import org.ims.model.Purchase;
import org.ims.model.PurchaseInfo;
import org.ims.model.Supplier;
import org.ims.model.User;
import org.ims.repository.PurchaseDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PurchaseService {

    private final PurchaseDAO purchaseDAO;

    public PurchaseService(PurchaseDAO purchaseDAO) {
        this.purchaseDAO = purchaseDAO;
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




}
