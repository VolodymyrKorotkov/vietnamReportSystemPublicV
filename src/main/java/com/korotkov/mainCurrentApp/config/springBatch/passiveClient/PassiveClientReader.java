package com.korotkov.mainCurrentApp.config.springBatch.passiveClient;

import com.korotkov.creditCRM.model.clients.exportForUploadingNewLeads.ExportPassiveClients;
import com.korotkov.creditCRM.service.clientCRM.ClientCRMService;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@JobScope
@Component
public class PassiveClientReader implements ItemReader<ExportPassiveClients> {
    private ClientCRMService clientCRMService;
    private int nextItemIndex;
    private List<ExportPassiveClients> clientsList;

    @Autowired
    public void setClientCRMService(ClientCRMService clientCRMService) {
        this.clientCRMService = clientCRMService;
    }


    @PostConstruct
    private void initialize() {
        int[] passiveDays = new int[] {1,10,15,25,30,45,60,80,100,120,150,180,200,235,270,300,330,365,400,500};
        List<LocalDate> dateList = new ArrayList<>();
        for (int a : passiveDays) {
            dateList.add(LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(a));
        }
        clientsList = clientCRMService.getPassiveClientsListForVicidial(dateList);
        nextItemIndex = 0;
    }


    @Override
    public ExportPassiveClients read() throws Exception {
        ExportPassiveClients nextClient = null;
        if (nextItemIndex < clientsList.size()) {
            nextClient = clientsList.get(nextItemIndex);
            nextItemIndex++;
        } else {
            nextItemIndex = 0;
        }
        return nextClient;
    }


}
