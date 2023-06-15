package com.korotkov.creditCRM.service.collection;

import com.korotkov.creditCRM.service.clientCRM.ClientCRMService;
import com.korotkov.mainCurrentApp.model.UploadingClientPhones;
import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.model.helpedObjects.ClientAddressInsertCRM;
import com.korotkov.mainCurrentApp.model.helpedObjects.ClientPhoneInsertCRM;
import com.korotkov.mainCurrentApp.service.email.EmailCommonService;
import com.korotkov.mainCurrentApp.service.uploadingClientPhones.UploadingClientPhonesService;
import com.korotkov.mainCurrentApp.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;
import static com.korotkov.mainCurrentApp.service.email.EmailConfig.SUBJECT;
import static com.korotkov.mainCurrentApp.service.email.EmailConfig.TO;

@Service
public class CollectionServiceImpl implements CollectionService {
    private EmailCommonService emailService;
    private ClientCRMService clientCRMService;
    private UploadingClientPhonesService uploadingClientPhonesService;


    @Autowired
    public void setUploadingClientPhonesService(UploadingClientPhonesService uploadingClientPhonesService) {
        this.uploadingClientPhonesService = uploadingClientPhonesService;
    }

    @Autowired
    public void setClientCRMService(ClientCRMService clientCRMService) {
        this.clientCRMService = clientCRMService;
    }

    @Autowired
    public void setEmailService(EmailCommonService emailService) {
        this.emailService = emailService;
    }



    @Override
    @Async("addingClientPhones")
    public void addingNewClientPhonesFromFileInsuranceFamilyBook(ArrayList<ArrayList<String>> arrayLists, UserAccount uploadedBy) {
        try {
            UploadingClientPhones uploadingClientPhones = new UploadingClientPhones();
            uploadingClientPhones.setStartedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            uploadingClientPhones.setUploadedBy(uploadedBy);

            int countPhonesForAdding = 0;
            int countPhonesWasAdded = 0;
            int countClientsWasNotFounded = 0;

            for (ArrayList<String> arrayList : arrayLists) {
                List<Long> clientIdList = new ArrayList<>();
                List<Long> clientIdList2 = new ArrayList<>();
                if (!arrayList.get(0).isEmpty()) {
                    clientIdList = clientCRMService.getClientIdsByDocumentNumber(arrayList.get(0));
                }
                if (!arrayList.get(1).isEmpty()) {
                    clientIdList2 = clientCRMService.getClientIdsByDocumentNumber(arrayList.get(1));
                }
                if (!clientIdList.isEmpty()) {
                    clientIdList.addAll(clientIdList2);
                } else {
                    clientIdList = clientIdList2;
                }
                if (clientIdList.isEmpty()) {
                    countClientsWasNotFounded++;
                } else {
                    for (Long clientId : clientIdList) {
                        if (!arrayList.get(7).isEmpty() && ValidationUtils.isValidPhone(arrayList.get(7))) {
                            countPhonesForAdding++;

                            if (!clientCRMService.existClientPhone(arrayList.get(7), clientId)) {
                                ClientPhoneInsertCRM phone = new ClientPhoneInsertCRM();
                                phone.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                                phone.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                                phone.setClientId(clientId);
                                phone.setPhone(arrayList.get(7));
                                if (!arrayList.get(3).isEmpty()) {
                                    phone.setRelationshipName(arrayList.get(3));
                                }
                                if (!arrayList.get(2).isEmpty()) {
                                    if (arrayList.get(2).equals("khách hàng") || arrayList.get(2).equals("Khách hàng")) {
                                        phone.setRelationshipType("OWN");
                                    } else {
                                        phone.setRelationshipType("OTHER");
                                    }
                                }
                                phone.setSource("I_CHECK_SERVICE");
                                String comment = "Added by I check service.";
                                if (!arrayList.get(4).isEmpty()) {
                                    comment += " QH CHỦ HỘ: " + arrayList.get(4);
                                }
                                if (!arrayList.get(5).isEmpty()) {
                                    comment += " NGÀY SINH: " + arrayList.get(5);
                                }
                                if (!arrayList.get(6).isEmpty()) {
                                    comment += " CMND: " + arrayList.get(6);
                                }
                                phone.setComment(comment);
                                clientCRMService.createClientPhone(phone);
                                countPhonesWasAdded++;
                            }
                        }

//                        if (!arrayList.get(12).isEmpty()) {
//                            String [] phoneArray = arrayList.get(12).split(";");
//                            for (String phoneFromArray : phoneArray) {
//                                if (ValidationUtils.isValidPhone(phoneFromArray)) {
//                                    countPhonesForAdding++;
//                                    if (!clientCRMService.existClientPhone(phoneFromArray, clientId)) {
//                                        ClientPhoneInsertCRM phoneInsert = new ClientPhoneInsertCRM();
//                                        phoneInsert.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                                        phoneInsert.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                                        phoneInsert.setClientId(clientId);
//                                        phoneInsert.setPhone(phoneFromArray);
//                                        if (!arrayList.get(11).isEmpty()) {
//                                            phoneInsert.setRelationshipName(arrayList.get(11));
//                                        } else {
//                                            phoneInsert.setRelationshipName("NGƯỜI ĐẠI DIỆN");
//                                        }
//                                        phoneInsert.setRelationshipType("COMPANY");
//                                        phoneInsert.setSource("I_CHECK_SERVICE");
//                                        String commentInsert = "Added by Insurance check service. This is SĐT NGƯỜI ĐẠI DIỆN.";
//                                        if (!arrayList.get(9).isEmpty()) {
//                                            commentInsert += "TÊN ĐƠN VỊ: " + arrayList.get(9);
//                                        }
//                                        if (!arrayList.get(11).isEmpty()) {
//                                            commentInsert += "NGƯỜI ĐẠI DIỆN: " + arrayList.get(11);
//                                        }
//                                        phoneInsert.setComment(commentInsert);
//                                        clientCRMService.createClientPhone(phoneInsert);
//                                        countPhonesWasAdded++;
//                                    }
//                                }
//                            }
//                        }


//                        if (!arrayList.get(14).isEmpty()) {
//                            String [] phoneArray2 = arrayList.get(14).split(";");
//                            for (String phoneFromArray2 : phoneArray2) {
//                                if (ValidationUtils.isValidPhone(phoneFromArray2)) {
//                                    countPhonesForAdding++;
//                                    if (!clientCRMService.existClientPhone(phoneFromArray2, clientId)) {
//                                        ClientPhoneInsertCRM phoneInsert2 = new ClientPhoneInsertCRM();
//                                        phoneInsert2.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                                        phoneInsert2.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                                        phoneInsert2.setClientId(clientId);
//                                        phoneInsert2.setPhone(phoneFromArray2);
//                                        if (!arrayList.get(13).isEmpty()) {
//                                            phoneInsert2.setRelationshipName(arrayList.get(11));
//                                        } else {
//                                            phoneInsert2.setRelationshipName("NGƯỜI LIÊN HỆ");
//                                        }
//                                        phoneInsert2.setRelationshipType("COMPANY");
//                                        phoneInsert2.setSource("I_CHECK_SERVICE");
//                                        String commentInsert2 = "Added by Insurance check service. This is SĐT NGƯỜI LIÊN HỆ.";
//                                        if (!arrayList.get(9).isEmpty()) {
//                                            commentInsert2 += "TÊN ĐƠN VỊ: " + arrayList.get(9);
//                                        }
//                                        if (!arrayList.get(11).isEmpty()) {
//                                            commentInsert2 += "NGƯỜI LIÊN HỆ: " + arrayList.get(13);
//                                        }
//                                        phoneInsert2.setComment(commentInsert2);
//                                        clientCRMService.createClientPhone(phoneInsert2);
//                                        countPhonesWasAdded++;
//                                    }
//                                }
//                            }
//                        }

                        if (!arrayList.get(8).isEmpty()) {
                            if (!clientCRMService.existClientAddressInStreet(arrayList.get(8), clientId)) {
                                ClientAddressInsertCRM addressInsertCRM = new ClientAddressInsertCRM();
                                addressInsertCRM.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                                addressInsertCRM.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                                addressInsertCRM.setClientId(clientId);
                                addressInsertCRM.setAddress(arrayList.get(8));
                                if (!arrayList.get(3).isEmpty()) {
                                    addressInsertCRM.setRelationshipName(arrayList.get(3));
                                } else {
                                    addressInsertCRM.setRelationshipName("-");
                                }
                                addressInsertCRM.setRelationshipType("OWN");
                                addressInsertCRM.setSource("I_CHECK_SERVICE");
                                addressInsertCRM.setComment("Added automated.");
                                clientCRMService.createClientAddress(addressInsertCRM);
                            }
                        }

                        if (!arrayList.get(10).isEmpty()) {
                            if (!clientCRMService.existClientAddressInStreet(arrayList.get(10), clientId)) {
                                ClientAddressInsertCRM addressInsertCRM2 = new ClientAddressInsertCRM();
                                addressInsertCRM2.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                                addressInsertCRM2.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                                addressInsertCRM2.setClientId(clientId);
                                addressInsertCRM2.setAddress(arrayList.get(10));
                                if (!arrayList.get(9).isEmpty()) {
                                    addressInsertCRM2.setRelationshipName(arrayList.get(9));
                                } else {
                                    addressInsertCRM2.setRelationshipName("-");
                                }
                                addressInsertCRM2.setRelationshipType("COMPANY");
                                addressInsertCRM2.setSource("I_CHECK_SERVICE");
                                addressInsertCRM2.setComment("Added automated.");
                                clientCRMService.createClientAddress(addressInsertCRM2);
                            }
                        }
                    }
                }
            }

            uploadingClientPhones.setCountPhonesForAdding(countPhonesForAdding);
            uploadingClientPhones.setCountPhonesAdded(countPhonesWasAdded);
            uploadingClientPhones.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            uploadingClientPhonesService.create(uploadingClientPhones);

            Map<String, Object> emailModel = new HashMap<>();
            emailModel.put(SUBJECT, "Result of uploading a file with client contacts from insurance");
            emailModel.put("bodyEmailTitle", "You started uploading a file with client contacts from insurance.");
            emailModel.put("bodyEmailText",
                    "As result -->" + " Count of clients was not found: " + countClientsWasNotFounded +
                            "; Count of phones was found in file for adding: " + countPhonesForAdding +
                            "; Count of phones was added to Data Base: " + countPhonesWasAdded + ".");
            emailModel.put(TO, uploadedBy.getEmail());
            emailModel.put("bannerLink", "https://tienoi.com.vn/wp-content/uploads/2022/10/shutterstock_142333726b.jpg");
            emailModel.put("buttonText", "List of all uploads of files for adding phones");
            emailModel.put("buttonLink", "https://report-system.tienoi.com.vn/collection-dep/uploading-client-phones");
            emailService.sendEmail("warningEmailToManagers.vm", emailModel);

        } catch (Exception e) {
            Map<String, Object> emailModel = new HashMap<>();
            emailModel.put(SUBJECT, "Error in uploading a file with client contacts from insurance");
            emailModel.put("bodyEmailTitle", "You started uploading a file with client contacts from insurance. But in result you have error. Show this message to developer:");
            emailModel.put("bodyEmailText", "message: " + e.getMessage() + "stackTrace: " +
                    Arrays.toString(e.getStackTrace()));
            emailModel.put(TO, uploadedBy.getEmail());
            emailModel.put("buttonText", "List of all uploads of files for adding phones");
            emailModel.put("buttonLink", "https://report-system.tienoi.com.vn/collection-dep/uploading-client-phones");

            emailModel.put("bannerLink", "https://tienoi.com.vn/wp-content/uploads/2022/10/57936397-warning-sign-yellow-warning-sign-warning-sign-icon-warning-sign-on-white-warning-sign-vector-warning.jpg");
            emailService.sendEmail("warningEmailToManagers.vm", emailModel);
        }
    }
}
