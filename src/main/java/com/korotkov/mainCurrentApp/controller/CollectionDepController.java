package com.korotkov.mainCurrentApp.controller;

import com.korotkov.creditCRM.service.clientCRM.ClientCRMService;
import com.korotkov.creditCRM.service.collection.CollectionService;
import com.korotkov.mainCurrentApp.model.UploadingClientPhones;
import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.service.file.ExcelReader;
import com.korotkov.mainCurrentApp.service.uploadingClientPhones.UploadingClientPhonesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;


@Controller
public class CollectionDepController {
    private UploadingClientPhonesService uploadingClientPhonesService;
    private ClientCRMService clientCRMService;
    private CollectionService collectionService;

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Autowired
    public void setClientCRMService(ClientCRMService clientCRMService) {
        this.clientCRMService = clientCRMService;
    }

    @Autowired
    public void setUploadingClientPhonesService(UploadingClientPhonesService uploadingClientPhonesService) {
        this.uploadingClientPhonesService = uploadingClientPhonesService;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/collection-dep/upload-new-client-phones", method = RequestMethod.GET)
    public ModelAndView pageUploadNewClientPhonesView(@RequestParam(defaultValue = "NaN") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/collectionDep/uploadNewClientPhonesView");
        switch (action) {
            case "fileIsEmpty" -> modelAndView.addObject("fileError", "common.fileErrorFileIsEmpty");
            case "fileNotExcel" -> modelAndView.addObject("fileError", "common.fileErrorFileIsNotNeedFormat");
            case "fileException" -> modelAndView.addObject("fileError", "common.fileErrorProcessFile");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/collection-dep/upload-client-docs-for-sending-and-checking-insurance", method = RequestMethod.GET)
    public ModelAndView pageUploadClientsDocsForSendingAndCheckingInsuranceView(@RequestParam(defaultValue = "NaN") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/collectionDep/uploadClientDocsForSendingAndCheckingInsuranceView");
        switch (action) {
            case "fileIsEmpty" -> modelAndView.addObject("fileError", "common.fileErrorFileIsEmpty");
            case "fileNotExcel" -> modelAndView.addObject("fileError", "common.fileErrorFileIsNotNeedFormat");
            case "fileException" -> modelAndView.addObject("fileError", "common.fileErrorProcessFile");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/upload-client-docs-for-sending-and-checking-insurance/uploaded", method = RequestMethod.GET)
    public ModelAndView uploadedFileForAddingClientDocsForSendingAndCheckingInsurance() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/templateSimpleInfoResultView");
        modelAndView.addObject("headerText", "addingNewClientDocsForCheckInsurancePage.headerText");
        modelAndView.addObject("underheaderText", "addingNewClientDocsForCheckInsurancePage.underheaderText");
        modelAndView.addObject("descriptionText", "addingNewClientDocsForCheckInsurancePage.descriptionText");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/upload-client-docs-for-sending-and-checking-insurance", method = RequestMethod.POST)
    public ModelAndView processUploadClientsDocsForSendingAndCheckingInsurance(@RequestParam("file") MultipartFile file,
                                                                               @AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        if (file.isEmpty()) {
            modelAndView.setViewName("redirect:/collectionDep/upload-client-docs-for-sending-and-checking-insurance?action=fileIsEmpty");
            return modelAndView;
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xls") &&
                !file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xlsx")) {
            modelAndView.setViewName("redirect:/collection-dep/upload-client-docs-for-sending-and-checking-insurance?action=fileNotExcel");
            return modelAndView;
        }
        try {
            clientCRMService.createSendingForCheckingInsurance(ExcelReader.readFileExcelAsTemplateWithOnlyFirstColumn(file),
                    userAccount.getUsername());
        } catch (Exception e) {
            modelAndView.setViewName("redirect:/collection-dep/upload-client-docs-for-sending-and-checking-insurance?action=fileException");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/upload-client-docs-for-sending-and-checking-insurance/uploaded");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/upload-new-file-for-adding-client-phones", method = RequestMethod.POST)
    public ModelAndView processUploadNewClientPhones(@RequestParam("file") MultipartFile file,
                                                     @AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        if (file.isEmpty()) {
            modelAndView.setViewName("redirect:/collection-dep/upload-new-client-phones?action=fileIsEmpty");
            return modelAndView;
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xls") &&
                !file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xlsx")) {
            modelAndView.setViewName("redirect:/collection-dep/upload-new-client-phones?action=fileNotExcel");
            return modelAndView;
        }

        try {
            collectionService
                    .addingNewClientPhonesFromFileInsuranceFamilyBook(ExcelReader
                            .readFileExcelAsTemplateForAddingClientPhones(file), userAccount);
        } catch (Exception e) {
            modelAndView.setViewName("redirect:/collection-dep/upload-new-client-phones?action=fileException");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/upload-new-file-for-adding-client-phones/uploaded");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/upload-new-file-for-adding-client-phones/uploaded", method = RequestMethod.GET)
    public ModelAndView uploadedFileForAddingClientPhones() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/templateSimpleInfoResultView");
        modelAndView.addObject("headerText", "addingNewClientPhonesPage.headerText");
        modelAndView.addObject("underheaderText", "addingNewClientPhonesPage.underheaderText");
        modelAndView.addObject("descriptionText", "addingNewClientPhonesPage.descriptionText");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/collection-dep/uploading-client-phones", method = RequestMethod.GET)
    public ModelAndView getPageViewUploadingClientPhones(@RequestParam(defaultValue = "NaN") String sf,
                                                         @RequestParam(defaultValue = "NaN") String action,
                                                         @RequestParam(defaultValue = "1") String pageString,
                                                         @RequestParam(value = "dateFrom", required = false) String dateFromString,
                                                         @RequestParam(value = "dateTo", required = false) String dateToString) {
        ModelAndView modelAndView = new ModelAndView();
        LocalDate dateFrom = null;
        LocalDate dateTo = null;
        if (dateFromString != null && !dateFromString.isEmpty()) {
            dateFrom = LocalDate.parse(dateFromString);
        }
        if (dateToString != null && !dateToString.isEmpty()) {
            dateTo = LocalDate.parse(dateToString);
        }
        int page;
        if (pageString.equals("NaN")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        if (!sf.equals("NaN")) {
            String view = "redirect:/collection-dep/uploading-client-phones";
            if ((dateFromString != null && !dateFromString.isEmpty()) || (dateToString != null && !dateToString.isEmpty())) {
                view += "?dateFrom=" + dateFromString + "&dateTo=" + dateToString;
            }
            modelAndView.setViewName(view);
            return modelAndView;
        }

        List<UploadingClientPhones> uploadingClientPhonesList;
        Long uploadingClientPhonesCount;

        if (dateFrom != null && dateTo != null) {
            uploadingClientPhonesList = uploadingClientPhonesService.getUploadingClientPhonesForDates(dateFrom, dateTo, page);
            uploadingClientPhonesCount = uploadingClientPhonesService.getCountUploadingClientPhonesForDates(dateFrom, dateTo);
        } else if (dateFrom != null) {
            uploadingClientPhonesList = uploadingClientPhonesService.getUploadingClientPhonesForDateFrom(dateFrom, page);
            uploadingClientPhonesCount = uploadingClientPhonesService.getCountUploadingClientPhonesForDateFrom(dateFrom);
        } else if (dateTo != null) {
            uploadingClientPhonesList = uploadingClientPhonesService.getUploadingClientPhonesForDateTo(dateTo, page);
            uploadingClientPhonesCount = uploadingClientPhonesService.getCountUploadingClientPhonesForDateTo(dateTo);
        } else {
            uploadingClientPhonesList = uploadingClientPhonesService.getAllUploadingClientPhones(page);
            uploadingClientPhonesCount = uploadingClientPhonesService.getCountAllUploadingClientPhones();
        }
        int pagesCount = (int) ((uploadingClientPhonesCount + 9) / 10);

        modelAndView.setViewName("/collectionDep/uploadingClientPhonesListView");
        modelAndView.addObject("page", page);
        modelAndView.addObject("pagesCount", pagesCount);
        modelAndView.addObject("uploadingClientPhonesList", uploadingClientPhonesList);
        modelAndView.addObject("uploadingClientPhonesCount", uploadingClientPhonesCount);

        return modelAndView;
    }


}
