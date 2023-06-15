package com.korotkov.mainCurrentApp.api.vicidial;

public interface VicidialApiService {
    boolean addNewLead(String phoneNumber, Integer listId, String firstName, String address1, String address2,
                       String postalCode);

    boolean addNewLeadExpiredDebt(String phoneNumber, Integer listId, String firstName, String lastName,
                                  String address1, String address2, String postalCode, String city,
                                  String email, String securityPhrase, String comments);
}
