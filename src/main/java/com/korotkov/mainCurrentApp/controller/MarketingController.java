package com.korotkov.mainCurrentApp.controller;

import com.korotkov.mainCurrentApp.model.ManualUkraineEmails;
import com.korotkov.mainCurrentApp.model.SendingEmail;
import com.korotkov.mainCurrentApp.model.TemplateSendingEmail;
import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.service.email.EmailCommonService;
import com.korotkov.mainCurrentApp.service.file.ExcelReader;
import com.korotkov.mainCurrentApp.service.manualUkraineEmails.ManualUkraineEmailsService;
import com.korotkov.mainCurrentApp.service.sendingEmail.SendingEmailService;
import com.korotkov.mainCurrentApp.service.templateSendingEmail.TemplateSendingEmailService;
import com.korotkov.mainCurrentApp.service.ukraine.ManualUkraineEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;
import static com.korotkov.mainCurrentApp.service.email.EmailConfig.SUBJECT;
import static com.korotkov.mainCurrentApp.service.email.EmailConfig.TO;

@Controller
public class MarketingController {

    SendingEmailService sendingEmailService;
    TemplateSendingEmailService templateSendingEmailService;
    EmailCommonService emailService;
    ManualUkraineEmailsService manualUkraineEmailsService;
    ManualUkraineEmailService manualUkraineEmailService;

    @Autowired
    public void setManualUkraineEmailService(ManualUkraineEmailService manualUkraineEmailService) {
        this.manualUkraineEmailService = manualUkraineEmailService;
    }

    @Autowired
    public void setManualUkraineEmailsService(ManualUkraineEmailsService manualUkraineEmailsService) {
        this.manualUkraineEmailsService = manualUkraineEmailsService;
    }

    @Autowired
    public void setEmailService(EmailCommonService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setSendingEmailService(SendingEmailService sendingEmailService) {
        this.sendingEmailService = sendingEmailService;
    }

    @Autowired
    public void setTemplateSendingEmailService(TemplateSendingEmailService templateSendingEmailService) {
        this.templateSendingEmailService = templateSendingEmailService;
    }



    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/email-sending-list", method = RequestMethod.GET)
    public ModelAndView emailSendingListView(@RequestParam(defaultValue = "NaN") String sf,
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
            String view = "redirect:/marketing/email-sending-list";
            if ((dateFromString != null && !dateFromString.isEmpty()) || (dateToString != null && !dateToString.isEmpty())) {
                view += "?dateFrom=" + dateFromString + "&dateTo=" + dateToString;
            }
            modelAndView.setViewName(view);
            return modelAndView;
        }

        List<SendingEmail> sendingEmailList;
        Long sendingEmailCount;

        if (dateFrom != null && dateTo != null) {
            sendingEmailList = sendingEmailService.getSendingEmailsForDates(dateFrom, dateTo, page);
            sendingEmailCount = sendingEmailService.getCountSendingEmailsForDates(dateFrom, dateTo);
        } else if (dateFrom != null) {
            sendingEmailList = sendingEmailService.getSendingEmailsForDateFrom(dateFrom, page);
            sendingEmailCount = sendingEmailService.getCountSendingEmailsForDateFrom(dateFrom);
        } else if (dateTo != null) {
            sendingEmailList = sendingEmailService.getSendingEmailsForDateTo(dateTo, page);
            sendingEmailCount = sendingEmailService.getCountSendingEmailsForDateTo(dateTo);
        } else {
            sendingEmailList = sendingEmailService.getAllSendingEmails(page);
            sendingEmailCount = sendingEmailService.getCountAllSendingEmails();
        }

        int pagesCount = (int) ((sendingEmailCount + 9) / 10);

        modelAndView.setViewName("/marketing/emailSendingList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("sendingEmailList", sendingEmailList);
        modelAndView.addObject("sendingEmailCount", sendingEmailCount);
        modelAndView.addObject("pagesCount", pagesCount);
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/email-sending-list/{id}", method = RequestMethod.GET)
    public ModelAndView emailSendingPageView(@PathVariable(value = "id") Long id,
                                             @RequestParam(defaultValue = "NaN") String action) {
        ModelAndView modelAndView = new ModelAndView();
        SendingEmail sendingEmail = sendingEmailService.getById(id);
        if (sendingEmail.isAuto()) {
            modelAndView.setViewName("/marketing/emailSendingView");
        } else {
            modelAndView.setViewName("/marketing/emailSendingManualView");
        }

        if (action.equals("new")) {
            modelAndView.addObject("messageGood", "message.createdNewSendingEmail");
        }
        modelAndView.addObject("emailSending", sendingEmail);
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list", method = RequestMethod.GET)
    public ModelAndView templateEmailSendingListView(@RequestParam(defaultValue = "NaN") String sf,
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
            String view = "redirect:/marketing/template-email-sending-list";
            if ((dateFromString != null && !dateFromString.isEmpty()) || (dateToString != null && !dateToString.isEmpty())) {
                view += "?dateFrom=" + dateFromString + "&dateTo=" + dateToString;
            }
            modelAndView.setViewName(view);
            return modelAndView;
        }

        List<TemplateSendingEmail> templateSendingEmailList;
        Long templateCount;

        if (dateFrom != null && dateTo != null) {
            templateSendingEmailList = templateSendingEmailService.getTemplatesSendingEmailsForDates(dateFrom, dateTo, page);
            templateCount = templateSendingEmailService.getCountTemplatesSendingEmailsForDates(dateFrom, dateTo);
        } else if (dateFrom != null) {
            templateSendingEmailList = templateSendingEmailService.getTemplatesSendingEmailsForDateFrom(dateFrom, page);
            templateCount = templateSendingEmailService.getCountTemplatesSendingEmailsForDateFrom(dateFrom);
        } else if (dateTo != null) {
            templateSendingEmailList = templateSendingEmailService.getTemplatesSendingEmailsForDateTo(dateTo, page);
            templateCount = templateSendingEmailService.getCountTemplatesSendingEmailsForDateTo(dateTo);
        } else {
            templateSendingEmailList = templateSendingEmailService.getAllTemplatesSendingEmails(page);
            templateCount = templateSendingEmailService.getCountAllTemplatesSendingEmails();
        }

        int pagesCount = (int) ((templateCount + 9) / 10);

        modelAndView.setViewName("/marketing/templateEmailSendingList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("pagesCount", pagesCount);
        modelAndView.addObject("templateSendingEmailList", templateSendingEmailList);
        modelAndView.addObject("templateCount", templateCount);

        if (action.equals("deleted")) {
            modelAndView.addObject("messageGood", "message.deleteSuccessfullyTemplateSendingEmail");
        }

        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN)")
    @RequestMapping(value = "/ukraine-email", method = RequestMethod.GET)
    public ModelAndView ukraineManualEmailPageView(@RequestParam(defaultValue = "NaN") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/marketing/ukraineEmailSendingView");
        switch (action) {
            case "fileIsEmpty" -> modelAndView.addObject("fileError", "common.fileErrorFileIsEmpty");
            case "fileNotExcel" -> modelAndView.addObject("fileError", "common.fileErrorFileIsNotNeedFormat");
            case "fileException" -> modelAndView.addObject("fileError", "common.fileErrorProcessFile");
            case "success" -> modelAndView.addObject("messageGood", "common.done");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN)")
    @RequestMapping(value = "/ukraine-email", method = RequestMethod.POST)
    public ModelAndView ukraineManualEmailProcess(@RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        if (file.isEmpty()) {
            modelAndView.setViewName("redirect:/ukraine-email?action=fileIsEmpty");
            return modelAndView;
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xls") &&
                !file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase().equals("xlsx")) {
            modelAndView.setViewName("redirect:/ukraine-email?action=fileNotExcel");
            return modelAndView;
        }
        try {
            manualUkraineEmailService.sendUkraineManualEmails(ExcelReader.readFileExcelAsTemplateWithOnlyFirstColumn(file));
        } catch (Exception e) {
            modelAndView.setViewName("redirect:/ukraine-email?action=fileException");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/ukraine-email?action=success");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list/create", method = RequestMethod.GET)
    public ModelAndView templateSendingEmailCreate() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/marketing/templateEmailSendingCreate");
        modelAndView.addObject("templateEmailSending", new TemplateSendingEmail());
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list/create", method = RequestMethod.POST)
    public ModelAndView templateSendingEmailSave(@AuthenticationPrincipal UserAccount saveBy,
                                                 @ModelAttribute("templateEmailSending")
                                                 @Valid TemplateSendingEmail templateSendingEmail,
                                                 BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/marketing/templateEmailSendingCreate");
        boolean hasError = false;
        if (bindingResult.hasErrors()) {
            hasError = true;
        }
        if (templateSendingEmail.getTitle().isBlank()) {
            modelAndView.addObject("titleError", "common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getSubjectEmail().isBlank()) {
            modelAndView.addObject("subjectEmailError", "common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getBodyEmailTitle().isBlank()) {
            modelAndView.addObject("bodyEmailTitleError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getBodyEmailText().isBlank()) {
            modelAndView.addObject("bodyEmailTextError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getButtonEmailText().isBlank()) {
            modelAndView.addObject("buttonEmailTextError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getLinkForClick().isBlank()) {
            modelAndView.addObject("linkForClickError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (!templateSendingEmail.getLinkForClick().startsWith("https://")) {
            modelAndView.addObject("linkForClickError","common.errorLinkHttps");
            hasError = true;
        }
        if (templateSendingEmail.getBannerLink().isBlank()) {
            modelAndView.addObject("bannerLinkError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (!templateSendingEmail.getBannerLink().startsWith("https://")) {
            modelAndView.addObject("bannerLinkError","common.errorLinkHttps");
            hasError = true;
        }

        if (hasError) {
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/marketing/template-email-sending-list/" +
                templateSendingEmailService.createAndGetId(templateSendingEmail, saveBy) +
                "?action=new");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list/{id}", method = RequestMethod.GET)
    public ModelAndView templateSendingEmailPageView(@PathVariable(value = "id") Long id,
                                                     @RequestParam(defaultValue = "NaN") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/marketing/templateSendingEmailView");
        modelAndView.addObject("templateEmailSending", templateSendingEmailService.getById(id));
        if (action.equals("new")) {
            modelAndView.addObject("messageGood", "message.createdNewTemplateSendingEmail");
        } else if (action.equals("delete-error")) {
            modelAndView.addObject("messageBad", "message.deleteUnsuccessfullyTemplateSendingEmail");
        } else if (action.equals("edited")) {
            modelAndView.addObject("messageGood", "message.editSuccessfullyTemplateSendingEmail");
        } else if (action.equals("test-ok")) {
            modelAndView.addObject("messageGood", "message.testSendingEmailOk");
        } else if (action.equals("test-error")) {
            modelAndView.addObject("messageBad", "message.testSendingEmailNotOk");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list/{id}/edit", method = RequestMethod.GET)
    public ModelAndView templateSendingEmailPageEdit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/marketing/templateEmailSendingEdit");
        modelAndView.addObject("templateEmailSending", templateSendingEmailService.getById(id));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list/{id}/edit", method = RequestMethod.POST)
    public ModelAndView templateEmailSendingEditProcess(@PathVariable(value = "id") Long id,
                                                        @AuthenticationPrincipal UserAccount saveBy,
                                                        @ModelAttribute("templateEmailSending")
                                                        @Valid TemplateSendingEmail templateSendingEmail,
                                                        BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/marketing/templateEmailSendingEdit");
        boolean hasError = bindingResult.hasErrors();
        if (templateSendingEmail.getTitle().isBlank()) {
            modelAndView.addObject("titleError", "common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getSubjectEmail().isBlank()) {
            modelAndView.addObject("subjectEmailError", "common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getBodyEmailTitle().isBlank()) {
            modelAndView.addObject("bodyEmailTitleError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getBodyEmailText().isBlank()) {
            modelAndView.addObject("bodyEmailTextError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getButtonEmailText().isBlank()) {
            modelAndView.addObject("buttonEmailTextError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (templateSendingEmail.getLinkForClick().isBlank()) {
            modelAndView.addObject("linkForClickError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (!templateSendingEmail.getLinkForClick().startsWith("https://")) {
            modelAndView.addObject("linkForClickError","common.errorLinkHttps");
            hasError = true;
        }
        if (templateSendingEmail.getBannerLink().isBlank()) {
            modelAndView.addObject("bannerLinkError","common.errorValueCanNotBeEmpty");
            hasError = true;
        }
        if (!templateSendingEmail.getBannerLink().startsWith("https://")) {
            modelAndView.addObject("bannerLinkError","common.errorLinkHttps");
            hasError = true;
        }

        if (hasError) {
            return modelAndView;
        }

        if (!templateSendingEmailService.update(templateSendingEmail, saveBy)) {
            modelAndView.addObject("messageBad", "common.unknownError");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/marketing/template-email-sending-list/" + id + "?action=edited");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list/{id}/delete", method = RequestMethod.GET)
    public ModelAndView templateSendingEmailPageDelete(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        if (!templateSendingEmailService.delete(id)) {
            modelAndView.setViewName("redirect:/marketing/template-email-sending-list/" + id + "?action=delete-error");
        } else {
            modelAndView.setViewName("redirect:/marketing/template-email-sending-list?action=deleted");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list/{id}/test-email-sending", method = RequestMethod.GET)
    public ModelAndView templateSendingEmailTestEmailSending(@PathVariable(value = "id") Long id,
                                                             @AuthenticationPrincipal UserAccount userAccount) {
        TemplateSendingEmail templateSendingEmail = templateSendingEmailService.getById(id);
        Map<String, Object> emailModel = new HashMap<>();
        emailModel.put("clickLink", templateSendingEmail.getLinkForClick());
        emailModel.put(SUBJECT, templateSendingEmail.getSubjectEmail());
        emailModel.put(TO, userAccount.getEmail());
        emailModel.put("clientName", userAccount.getLastName() + " " + userAccount.getFirstName());
        emailModel.put("bodyEmailTitle", templateSendingEmail.getBodyEmailTitle());
        emailModel.put("bodyEmailText", templateSendingEmail.getBodyEmailText());
        emailModel.put("buttonEmailText", templateSendingEmail.getButtonEmailText());
        emailModel.put("bannerLink", templateSendingEmail.getBannerLink());

        boolean result = emailService.sendEmail("manualEmailSendingWithEmailTemplate.vm",emailModel);

        ModelAndView modelAndView = new ModelAndView();
        if (!result) {
            modelAndView.setViewName("redirect:/marketing/template-email-sending-list/" + id + "?action=test-error");
        } else {
            modelAndView.setViewName("redirect:/marketing/template-email-sending-list/" + id + "?action=test-ok");
        }

        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list/{id}/create-new-email-sending", method = RequestMethod.GET)
    public ModelAndView templateSendingEmailCreateNewEmailSending(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("templateEmailSending", templateSendingEmailService.getById(id));
        modelAndView.addObject("emailSending", new SendingEmail());
        modelAndView.setViewName("/marketing/emailSendingCreate");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/marketing/template-email-sending-list/{id}/create-new-email-sending", method = RequestMethod.POST)
    public ModelAndView templateSendingEmailCreateNewEmailSendingProcess(@PathVariable(value = "id") Long id,
                                                                         @AuthenticationPrincipal UserAccount userAccount,
                                                                         @ModelAttribute("emailSending")
                                                                         @Valid SendingEmail sendingEmail,
                                                                         BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        boolean hasError = bindingResult.hasErrors();

        if (sendingEmail.getTitle().isBlank()) {
            modelAndView.addObject("titleError", "common.errorValueCanNotBeEmpty");
            hasError = true;
        }

        if (sendingEmail.getPlannedAt() == null) {
            modelAndView.addObject("plannedAtError", "common.errorValueCanNotBeEmpty");
            hasError = true;
        } else {
            if (sendingEmail.getPlannedAt().isAfter(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusWeeks(1))) {
                modelAndView.addObject("plannedAtError", "marketingPage.plannedAtErrorAfterWeek");
                hasError = true;
            }
        }

        if (sendingEmail.isRepeatPassiveClients()) {
            if (sendingEmail.getRepeatPassiveClientsDaysFrom() == 0) {
                modelAndView.addObject("repeatPassiveClientsDaysFromError", "marketingPage.daysEmptyError");
                hasError = true;
            } else if (sendingEmail.getRepeatPassiveClientsDaysFrom() > sendingEmail.getRepeatPassiveClientsDaysTo()) {
                modelAndView.addObject("repeatPassiveClientsDaysFromError", "marketingPage.errorFromLargerTo");
                hasError = true;
            }

            if (sendingEmail.getRepeatPassiveClientsDaysTo() == 0) {
                modelAndView.addObject("repeatPassiveClientsDaysToError", "marketingPage.daysEmptyError");
                hasError = true;
            }
        }

        if (sendingEmail.isClientsWithExpiredLoan()) {
            if (sendingEmail.getClientsWithExpiredLoanDaysFrom() == 0) {
                modelAndView.addObject("clientsWithExpiredLoanDaysFromError", "marketingPage.daysEmptyError");
                hasError = true;
            } else if (sendingEmail.getClientsWithExpiredLoanDaysFrom() > sendingEmail.getClientsWithExpiredLoanDaysTo()) {
                modelAndView.addObject("clientsWithExpiredLoanDaysFromError", "marketingPage.errorFromLargerTo");
                hasError = true;
            }

            if (sendingEmail.getClientsWithExpiredLoanDaysTo() == 0) {
                modelAndView.addObject("clientsWithExpiredLoanDaysToError", "marketingPage.daysEmptyError");
                hasError = true;
            }
        }

        if (sendingEmail.isRegisteredClientsWithoutApplications()) {
            if (sendingEmail.getRegisteredClientsWithoutApplicationsDaysFrom() == 0) {
                modelAndView.addObject("registeredClientsWithoutApplicationsDaysFromError", "marketingPage.daysEmptyError");
                hasError = true;
            } else if (sendingEmail.getRegisteredClientsWithoutApplicationsDaysFrom() > sendingEmail.getRegisteredClientsWithoutApplicationsDaysTo()) {
                modelAndView.addObject("registeredClientsWithoutApplicationsDaysFromError", "marketingPage.errorFromLargerTo");
                hasError = true;
            }

            if (sendingEmail.getRegisteredClientsWithoutApplicationsDaysTo() == 0) {
                modelAndView.addObject("registeredClientsWithoutApplicationsDaysToError", "marketingPage.daysEmptyError");
                hasError = true;
            }
        }

        if (!sendingEmail.isAllActiveClientsWithoutExpiredLoans() && !sendingEmail.isRepeatPassiveClients() &&
        !sendingEmail.isClientsWithExpiredLoan() && !sendingEmail.isRegisteredClientsWithoutApplications()) {
            modelAndView.addObject("messageBad", "marketingPage.anyClientError");
            hasError = true;
        }

        TemplateSendingEmail templateSendingEmail = templateSendingEmailService.getById(id);

        if (hasError) {
            modelAndView.setViewName("/marketing/emailSendingCreate");
            modelAndView.addObject("templateSendingEmail", templateSendingEmail);
            return modelAndView;
        }

        Long idSendingEmail = sendingEmailService.createManualAsPlannedAndGetId(templateSendingEmail, sendingEmail, userAccount);

        modelAndView.setViewName("redirect:/marketing/email-sending-list/" + idSendingEmail +
                "?action=new");
        return modelAndView;
    }



}
