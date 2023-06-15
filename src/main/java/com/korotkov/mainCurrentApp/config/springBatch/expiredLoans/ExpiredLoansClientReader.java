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
public class ExpiredLoansClientReader implements ItemReader<ExportClientsWithExpiredForVicidial> {
    private ClientCRMService clientCRMService;
    private int nextItemIndex;
    private List<ExportClientsWithExpiredForVicidial> clientList;

    @Autowired
    public void setClientCRMService(ClientCRMService clientCRMService) {
        this.clientCRMService = clientCRMService;
    }


    @PostConstruct
    private void initialize() {
        LocalTime currentTime = LocalTime.now(ZoneId.of(TIME_ZONE));
        boolean own;
//        int[] days;
        List<Integer> daysOverdue = new ArrayList<>();

        if (currentTime.isAfter(LocalTime.of(0,0)) &&
        currentTime.isBefore(LocalTime.of(7,10))) {
            // 6:45
            own = true;
            for (int day = 3; day < 16; day++) {
                daysOverdue.add(day);
            }
        } else if (currentTime.isAfter(LocalTime.of(7,9)) &&
                currentTime.isBefore(LocalTime.of(11,30))) {
            own = true;
            for (int day = 16; day < 300; day += 1) {
                daysOverdue.add(day);
            }
        } else if (currentTime.isAfter(LocalTime.of(11,29)) &&
                currentTime.isBefore(LocalTime.of(20,50))) {
            own = false;
            for (int day = 7; day < 300; day += 2) {
                daysOverdue.add(day);
            }
        } else {
            for (int a = 1; a <= 100; a++) {
                daysOverdue.add(a);
            }
            own = true;
        }

        if (own) {
            clientList = clientCRMService.getExpiredDebtsOwnNumbersForVicidial(daysOverdue);
        } else {
            clientList = clientCRMService.getExpiredDebtsNotOwnNumbersForVicidial(daysOverdue);
        }

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
