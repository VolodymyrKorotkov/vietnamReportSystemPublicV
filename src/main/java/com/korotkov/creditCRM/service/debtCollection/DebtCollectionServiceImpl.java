package com.korotkov.creditCRM.service.debtCollection;

import com.korotkov.creditCRM.dao.collection.debtCollection.DebtCollectionDAO;
import com.korotkov.creditCRM.service.debtCollection.DebtCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DebtCollectionServiceImpl implements DebtCollectionService {
    private DebtCollectionDAO debtCollectionDAO;

    @Autowired
    public void setDebtCollectionDAO(DebtCollectionDAO debtCollectionDAO) {
        this.debtCollectionDAO = debtCollectionDAO;
    }

    @Override
    @Transactional("transactionManagerCRM")
    public void updatePromisedPaymentsAsExpired() {
        debtCollectionDAO.updatePromisedPaymentsAsExpired();
    }
}
