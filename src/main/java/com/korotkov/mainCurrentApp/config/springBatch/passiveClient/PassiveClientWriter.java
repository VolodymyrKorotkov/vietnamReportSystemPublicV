package com.korotkov.mainCurrentApp.config.springBatch.passiveClient;

import com.korotkov.creditCRM.model.clients.exportForUploadingNewLeads.ExportPassiveClients;
import com.korotkov.mainCurrentApp.api.vicidial.VicidialApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PassiveClientWriter implements ItemWriter<Object> {
    private VicidialApiService vicidialApiService;

    @Autowired
    public void setVicidialApiService(VicidialApiService vicidialApiService) {
        this.vicidialApiService = vicidialApiService;
    }

    @Override
    public void write(List<? extends Object> list) throws Exception  {
        for(Object o : list) {
            ExportPassiveClients passiveClients = (ExportPassiveClients) o;
            vicidialApiService.addNewLead(passiveClients.getPhone(), 1100, passiveClients.getFullName(),
                    "Repeat Client for Repeat Loan", passiveClients.getPassiveDays() + " days ago last issue",
                    "1100");
        }
    }
}
