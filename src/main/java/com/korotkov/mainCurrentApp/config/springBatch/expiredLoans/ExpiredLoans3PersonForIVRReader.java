package com.korotkov.mainCurrentApp.config.springBatch.expiredLoans;

import com.korotkov.creditCRM.model.clients.ExportClientsWithExpiredForVicidial;
import com.korotkov.creditCRM.service.clientCRM.ClientCRMService;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@JobScope
@Component
public class ExpiredLoans3PersonForIVRReader implements ItemReader<ExportClientsWithExpiredForVicidial> {
    private ClientCRMService clientCRMService;
    private int nextItemIndex;
    private List<ExportClientsWithExpiredForVicidial> clientList;

    @Autowired
    public void setClientCRMService(ClientCRMService clientCRMService) {
        this.clientCRMService = clientCRMService;
    }


    @PostConstruct
    private void initialize() {


        List<Integer> daysOverdue = new ArrayList<>();


        for (int a = 10; a < 300; a += 2) {
            daysOverdue.add(a);
        }

        clientList = clientCRMService.getExpiredDebtsNotOwnNumbersForVicidial(daysOverdue);

        nextItemIndex = 0;
    }


    public ExportClientsWithExpiredForVicidial read() {
        ExportClientsWithExpiredForVicidial nextClient = null;
        if (nextItemIndex < clientList.size()) {
            nextClient = clientList.get(nextItemIndex);
            nextItemIndex++;
        } else {
            nextItemIndex = 0;
        }
        return nextClient;
    }

}
