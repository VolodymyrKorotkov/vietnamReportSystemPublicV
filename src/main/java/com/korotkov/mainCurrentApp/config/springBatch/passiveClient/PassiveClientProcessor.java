package com.korotkov.mainCurrentApp.config.springBatch.passiveClient;

import com.korotkov.creditCRM.model.clients.exportForUploadingNewLeads.ExportPassiveClients;
import com.korotkov.mainCurrentApp.api.vicidial.VicidialApiService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PassiveClientProcessor implements ItemProcessor<ExportPassiveClients, ExportPassiveClients> {
    private VicidialApiService vicidialApiService;

    @Autowired
    public void setVicidialApiService(VicidialApiService vicidialApiService) {
        this.vicidialApiService = vicidialApiService;
    }

    @Override
    public ExportPassiveClients process(ExportPassiveClients passiveClients) {
        vicidialApiService.addNewLead(passiveClients.getPhone(), 7777, passiveClients.getFullName(),
                "Repeat Client for Repeat Loan", passiveClients.getPassiveDays() + " days ago last issue",
                "7777");
        return passiveClients;
    }


}
