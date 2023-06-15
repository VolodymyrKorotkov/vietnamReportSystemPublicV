package com.korotkov.mainCurrentApp.api.vicidial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class VicidialApiServiceImpl implements VicidialApiService {
    private static final Logger logger = LoggerFactory.getLogger(VicidialApiServiceImpl.class);
    private RestTemplate restTemplate;

    private final String URL_REQUEST = "https://112.213.94.154:9998/vicidial/non_agent_api.php?gmt_offset_now=7.00&pass=apiuser&phone_code=84&source=test&state=VN&user=apiuser";
    // prod: 112.213.94.154:9998
    //localTest: vdrr.vn.cloud.local



    @Autowired
    @Qualifier("restTemplateWithoutSsl")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public boolean addNewLead(String phoneNumber, Integer listId, String firstName, String address1, String address2,
                              String postalCode) {
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(URL_REQUEST + "&function=add_lead" + "&phone_number=" +
                    phoneNumber + "&list_id=" + listId + "&first_name=" + firstName + "&address1=" + address1 +
                    "&address2=" + address2 + "&postal_code=" + postalCode, String.class);
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + e.getMessage() + " in " + e.getClass());
            return false;
        }
        if (responseEntity.getStatusCodeValue() != 200) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + "Vicidial response code is not 200, response code is " + responseEntity.getStatusCodeValue() + ", body: " + responseEntity.getBody() + ". For phone: " + phoneNumber + ", for list: " + listId);
            return false;
        }
        return true;
    }


    @Override
    public boolean addNewLeadExpiredDebt(String phoneNumber, Integer listId, String firstName, String lastName,
                                         String address1, String address2, String postalCode, String city,
                                         String email, String securityPhrase, String comments) {
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(URL_REQUEST + "&function=add_lead" + "&phone_number=" +
                    phoneNumber + "&list_id=" + listId + "&first_name=" + firstName + "&last_name=" + lastName
                    + "&address1=" + address1 + "&address2=" + address2 + "&postal_code=" + postalCode
                    + "&city=" + city + "&email=" + email + "&security_phrase=" + securityPhrase
                    + "&comments=" + comments,String.class);
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + e.getMessage() + " in " + e.getClass());
            return false;
        }
        if (responseEntity.getStatusCodeValue() != 200) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + "Vicidial response code is not 200, response code is " + responseEntity.getStatusCodeValue() + ", body: " + responseEntity.getBody() + ". For phone: " + phoneNumber + ", for list: " + listId);
            return false;
        }
        return true;
    }




}
