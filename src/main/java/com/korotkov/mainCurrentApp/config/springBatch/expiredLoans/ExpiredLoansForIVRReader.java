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
public class ExpiredLoansForIVRReader implements ItemReader<ExportClientsWithExpiredForVicidial> {
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
        List<Integer> daysOverdue = new ArrayList<>();
        int listId;

        if (currentTime.isAfter(LocalTime.of(0,0)) &&
                currentTime.isBefore(LocalTime.of(7,15))) {
            // 6:55
            for (int a = 31; a <= 50; a++) {
                daysOverdue.add(a);
            }
            listId = 205;
        } else if (currentTime.isAfter(LocalTime.of(7,14)) &&
                currentTime.isBefore(LocalTime.of(7,30))) {
            // 7:17
            for (int a = 51; a <= 200; a++) {
                daysOverdue.add(a);
            }
            listId = 206;
        }  else if (currentTime.isAfter(LocalTime.of(7,29)) &&
                currentTime.isBefore(LocalTime.of(7,45))) {
            // 7:35
            for (int a = 7; a <= 500; a++) {
                daysOverdue.add(a);
            }
            listId = 207;
        } else if (currentTime.isAfter(LocalTime.of(7,44)) &&
        currentTime.isBefore(LocalTime.of(8,15))) {
            // 8:00
            for (int a = 6; a <= 15; a++) {
                daysOverdue.add(a);
            }
            listId = 203;
        } else if (currentTime.isAfter(LocalTime.of(8,14)) &&
                currentTime.isBefore(LocalTime.of(8,45))) {
            // 08:30
            for (int a = 16; a <= 30; a++) {
                daysOverdue.add(a);
            }
            listId = 204;
        } else if (currentTime.isAfter(LocalTime.of(8,44)) &&
                currentTime.isBefore(LocalTime.of(9, 5))) {
            // 8:55
            for (int a = 6; a <= 15; a++) {
                daysOverdue.add(a);
            }
            listId = 203;
        } else if (currentTime.isAfter(LocalTime.of(9, 4)) &&
                currentTime.isBefore(LocalTime.of(9,35))) {
            // 9:20
            daysOverdue.add(1);
            listId = 208;
        }  else if (currentTime.isAfter(LocalTime.of(9,34)) &&
                currentTime.isBefore(LocalTime.of(10,15))) {
            // 10:05
            daysOverdue.add(2);
            listId = 209;
        } else if (currentTime.isAfter(LocalTime.of(10,14)) &&
                currentTime.isBefore(LocalTime.of(11,50))) {
            // 10:25
            for (int a = 3; a <= 5; a++) {
                daysOverdue.add(a);
            }
            listId = 200;
        } else if (currentTime.isAfter(LocalTime.of(11, 49)) &&
                currentTime.isBefore(LocalTime.of(12,35))) {
            // 11:55
            daysOverdue.add(1);
            listId = 208;
        }  else if (currentTime.isAfter(LocalTime.of(12,34)) &&
                currentTime.isBefore(LocalTime.of(13,15))) {
            // 12:45
            daysOverdue.add(2);
            listId = 209;
        } else if (currentTime.isAfter(LocalTime.of(13,14)) &&
                currentTime.isBefore(LocalTime.of(16,30))) {
            // 16:05
            for (int a = 6; a <= 15; a++) {
                daysOverdue.add(a);
            }
            listId = 203;
        } else if (currentTime.isAfter(LocalTime.of(16,29)) &&
                currentTime.isBefore(LocalTime.of(16,50))) {
            // 16:35
            for (int a = 3; a <= 5; a++) {
                daysOverdue.add(a);
            }
            listId = 200;
        } else if (currentTime.isAfter(LocalTime.of(16, 49)) &&
                currentTime.isBefore(LocalTime.of(17,35))) {
            // 17:15
            daysOverdue.add(1);
            listId = 208;
        } else if (currentTime.isAfter(LocalTime.of(17,34)) &&
                currentTime.isBefore(LocalTime.of(17,55))) {
            // 17:40
            daysOverdue.add(2);
            listId = 209;
        } else {
            for (int a = 7; a <= 300; a++) {
                daysOverdue.add(a);
            }
            listId = 207;
        }

        clientList = clientCRMService.getExpiredDebtsOwnNumbersForVicidial(daysOverdue);

        for (ExportClientsWithExpiredForVicidial client : clientList) {
            client.setListId(listId);
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
