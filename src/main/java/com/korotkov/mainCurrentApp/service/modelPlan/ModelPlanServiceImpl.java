package com.korotkov.mainCurrentApp.service.modelPlan;

import com.korotkov.mainCurrentApp.config.ConfigConstants;
import com.korotkov.mainCurrentApp.dao.modelPlan.ModelPlanDao;
import com.korotkov.mainCurrentApp.model.ModelPlan;
import com.korotkov.mainCurrentApp.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ModelPlanServiceImpl implements ModelPlanService, ConfigConstants {
    private ModelPlanDao modelPlanDao;

    @Autowired
    public void setModelPlanDao(ModelPlanDao modelPlanDao) {
        this.modelPlanDao = modelPlanDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(ModelPlan modelPlan) {
        modelPlanDao.create(modelPlan);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(ModelPlan modelPlan) {
        modelPlanDao.update(modelPlan);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ModelPlan getById(Long id) {
        return modelPlanDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ModelPlan> getAllModelPlans(int page) {
        return modelPlanDao.getAllModelPlans(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllModelPlans() {
        return modelPlanDao.getCountAllModelPlans();
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updatePlan(Long id, ModelPlan modelPlan, UserAccount userAccount) {
        ModelPlan modelPlanFromDB = modelPlanDao.getById(id);
        modelPlanFromDB.setIssuedAmountNew(modelPlan.getIssuedAmountNew());
        modelPlanFromDB.setIssuedAmountRepeat(modelPlan.getIssuedAmountRepeat());
        modelPlanFromDB.setProlongedAmount(modelPlan.getProlongedAmount());
        modelPlanFromDB.setRepaymentPrincipalAmountNew(modelPlan.getRepaymentPrincipalAmountNew());
        modelPlanFromDB.setRepaymentPrincipalAmountRepeat(modelPlan.getRepaymentPrincipalAmountRepeat());
        modelPlanFromDB.setRepaymentIncomeAmountNew(modelPlan.getRepaymentIncomeAmountNew());
        modelPlanFromDB.setRepaymentIncomeAmountRepeat(modelPlan.getRepaymentIncomeAmountRepeat());
        modelPlanFromDB.setIssuedAmountTotal(modelPlanFromDB.getIssuedAmountNew() +
                modelPlanFromDB.getIssuedAmountRepeat());
        modelPlanFromDB.setContractsAmountTotal(modelPlanFromDB.getIssuedAmountTotal() +
                modelPlanFromDB.getProlongedAmount());
        modelPlanFromDB.setRepaymentPrincipalAmountTotal(modelPlanFromDB.getRepaymentPrincipalAmountNew() +
                modelPlanFromDB.getRepaymentPrincipalAmountRepeat());
        modelPlanFromDB.setRepaymentIncomeAmountTotal(modelPlanFromDB.getRepaymentIncomeAmountNew() +
                modelPlanFromDB.getRepaymentIncomeAmountRepeat());
        modelPlanFromDB.setRepaymentTotalAmountTotal(modelPlanFromDB.getRepaymentIncomeAmountTotal() +
                modelPlanFromDB.getRepaymentPrincipalAmountTotal());
        modelPlanFromDB.setRepaymentTotalAmountNew(modelPlanFromDB.getRepaymentIncomeAmountNew() +
                modelPlanFromDB.getRepaymentPrincipalAmountNew());
        modelPlanFromDB.setRepaymentTotalAmountRepeat(modelPlanFromDB.getRepaymentIncomeAmountRepeat() +
                modelPlanFromDB.getRepaymentPrincipalAmountRepeat());
        modelPlanFromDB.setLastModifiedBy(userAccount);
        modelPlanFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        modelPlanDao.update(modelPlanFromDB);
    }


    @Override
    @Transactional("transactionManagerMain")
    public boolean createNewPlan(ModelPlan modelPlan, UserAccount userAccount) {
        if (modelPlan.getDateMonth() == null) {
            return false;
        }
        LocalDate dateMonth = modelPlan.getDateMonth()
                .withDayOfMonth(modelPlan.getDateMonth().getMonth().length(modelPlan.getDateMonth().isLeapYear()));
        ModelPlan modelPlanFromDB = modelPlanDao.getByDateMonth(dateMonth);
        if (modelPlanFromDB != null) {
            return false;
        }
        ModelPlan modelPlanForSave = new ModelPlan();
        modelPlanForSave.setDateMonth(dateMonth);
        modelPlanForSave.setIssuedAmountNew(modelPlan.getIssuedAmountNew());
        modelPlanForSave.setIssuedAmountRepeat(modelPlan.getIssuedAmountRepeat());
        modelPlanForSave.setProlongedAmount(modelPlan.getProlongedAmount());
        modelPlanForSave.setRepaymentPrincipalAmountNew(modelPlan.getRepaymentPrincipalAmountNew());
        modelPlanForSave.setRepaymentPrincipalAmountRepeat(modelPlan.getRepaymentPrincipalAmountRepeat());
        modelPlanForSave.setRepaymentIncomeAmountNew(modelPlan.getRepaymentIncomeAmountNew());
        modelPlanForSave.setRepaymentIncomeAmountRepeat(modelPlan.getRepaymentIncomeAmountRepeat());
        modelPlanForSave.setIssuedAmountTotal(modelPlanForSave.getIssuedAmountNew() +
                modelPlanForSave.getIssuedAmountRepeat());
        modelPlanForSave.setContractsAmountTotal(modelPlanForSave.getIssuedAmountTotal() +
                modelPlanForSave.getProlongedAmount());
        modelPlanForSave.setRepaymentPrincipalAmountTotal(modelPlanForSave.getRepaymentPrincipalAmountNew() +
                modelPlanForSave.getRepaymentPrincipalAmountRepeat());
        modelPlanForSave.setRepaymentIncomeAmountTotal(modelPlanForSave.getRepaymentIncomeAmountNew() +
                modelPlanForSave.getRepaymentIncomeAmountRepeat());
        modelPlanForSave.setRepaymentTotalAmountTotal(modelPlanForSave.getRepaymentIncomeAmountTotal() +
                modelPlanForSave.getRepaymentPrincipalAmountTotal());
        modelPlanForSave.setRepaymentTotalAmountNew(modelPlanForSave.getRepaymentIncomeAmountNew() +
                modelPlanForSave.getRepaymentPrincipalAmountNew());
        modelPlanForSave.setRepaymentTotalAmountRepeat(modelPlanForSave.getRepaymentIncomeAmountRepeat() +
                modelPlanForSave.getRepaymentPrincipalAmountRepeat());
        modelPlanForSave.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        modelPlanForSave.setCreatedBy(userAccount);
        modelPlanForSave.setLastModifiedBy(userAccount);
        modelPlanForSave.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        modelPlanDao.create(modelPlanForSave);
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getIdCreatedModelByDateMonth(ModelPlan modelPlan) {
        LocalDate dateMonth = modelPlan.getDateMonth()
                .withDayOfMonth(modelPlan.getDateMonth().getMonth().length(modelPlan.getDateMonth().isLeapYear()));
        return modelPlanDao.getByDateMonth(dateMonth).getId();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ModelPlan> getModelPlansWithDates(LocalDate dateFrom, LocalDate dateTo) {
        return modelPlanDao.getModelPlansWithDates(dateFrom, dateTo);
    }
}
