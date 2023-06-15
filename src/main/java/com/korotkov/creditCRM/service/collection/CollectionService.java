package com.korotkov.creditCRM.service.collection;

import com.korotkov.mainCurrentApp.model.UserAccount;

import java.util.ArrayList;

public interface CollectionService {
    void addingNewClientPhonesFromFileInsuranceFamilyBook(ArrayList<ArrayList<String>> arrayLists, UserAccount uploadedBy);
}
