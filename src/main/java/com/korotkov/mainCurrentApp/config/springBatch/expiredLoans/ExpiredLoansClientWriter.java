package com.korotkov.mainCurrentApp.config.springBatch.expiredLoans;

import com.korotkov.creditCRM.model.clients.ExportClientsWithExpiredForVicidial;
import com.korotkov.mainCurrentApp.api.vicidial.VicidialApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpiredLoansClientWriter implements ItemWriter<Object> {
    private VicidialApiService vicidialApiService;

    @Autowired
    public void setVicidialApiService(VicidialApiService vicidialApiService) {
        this.vicidialApiService = vicidialApiService;
    }

    @Override
    public void write(List<? extends Object> list) throws Exception {
        for(Object o : list) {
            ExportClientsWithExpiredForVicidial client = (ExportClientsWithExpiredForVicidial) o;
            if (client.isOwn()) {
                vicidialApiService.addNewLeadExpiredDebt(client.getPhone(), 1500, client.getFullName(),
                        client.getRelationshipName(), "DebtLoans Call to Client",
                        client.getDaysOverdue() + " days overdue", "1500", client.getCurrentDebt().toString(),
                        client.getBankAccount(), client.getDocumentNumber(), "Bank: " + client.getBankShortName());
            } else {
                vicidialApiService.addNewLeadExpiredDebt(client.getPhone(), 1502, client.getFullName(),
                        client.getRelationshipName(), "Gọi cho Tham chiếu",
                        client.getDaysOverdue() + " days overdue", "1502", client.getCurrentDebt().toString(),
                        client.getBankAccount(), client.getDocumentNumber(), "Bank: " + client.getBankShortName());
            }
        }
    }
}