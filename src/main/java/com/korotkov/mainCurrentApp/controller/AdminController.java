package com.korotkov.mainCurrentApp.controller;

import com.korotkov.mainCurrentApp.config.ConfigConstants;
import com.korotkov.mainCurrentApp.model.*;
import com.korotkov.mainCurrentApp.service.email.EmailCommonService;
import com.korotkov.mainCurrentApp.service.scheduledChecking.ScheduledCheckingService;
import com.korotkov.mainCurrentApp.service.scheduledTask.modelService.SchedTaskService;
import com.korotkov.mainCurrentApp.service.scheduledUploadingLeadsVicidial.ScheduledUploadingLeadsVicidialService;
import com.korotkov.mainCurrentApp.service.systemSetting.SystemSettingService;
import com.korotkov.mainCurrentApp.service.userAccount.UserAccountService;
import com.korotkov.mainCurrentApp.service.userRole.UserRoleService;
import com.korotkov.mainCurrentApp.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController implements ConfigConstants {
    UserAccountService userAccountService;
    UserRoleService userRoleService;
    EmailCommonService emailService;
    SchedTaskService schedTaskService;
    ScheduledCheckingService scheduledCheckingService;
    SystemSettingService systemSettingService;
    ScheduledUploadingLeadsVicidialService scheduledUploadingLeadsVicidialService;

    @Autowired
    public void setScheduledUploadingLeadsVicidialService(ScheduledUploadingLeadsVicidialService scheduledUploadingLeadsVicidialService) {
        this.scheduledUploadingLeadsVicidialService = scheduledUploadingLeadsVicidialService;
    }

    @Autowired
    public void setSystemSettingService(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setEmailService(EmailCommonService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setSchedTaskService(SchedTaskService schedTaskService) {
        this.schedTaskService = schedTaskService;
    }

    @Autowired
    public void setScheduledCheckingService(ScheduledCheckingService scheduledCheckingService) {
        this.scheduledCheckingService = scheduledCheckingService;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN)")
    @RequestMapping(value = "/admin/scheduled-tasks", method = RequestMethod.GET)
    public ModelAndView scheduledTaskListView(@RequestParam(defaultValue = "1") String pageString
//                                      @RequestParam(defaultValue = "NaN") String status,
//                                      @RequestParam(defaultValue = "NaN") String task,
//                                      @RequestParam(defaultValue = "NaN") String sf
                                      ) {
        int page;
        if (pageString.equals("NaN")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        ModelAndView modelAndView = new ModelAndView();
//        if (!sf.equals("NaN")) {
//            modelAndView.setViewName("redirect:/admin/scheduled-tasks?" + "task=" + task +
//                    "&status=" + status);
//            return modelAndView;
//        }

        List<ScheduledTask> scheduledTaskList = schedTaskService.getAllScheduledTasks(page);
        Long scheduledTaskCount = schedTaskService.getCountAllScheduledTasks();


        int pagesCount = (int) ((scheduledTaskCount + 9) / 10);
        modelAndView.setViewName("/admin/scheduledTaskList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("scheduledTaskList", scheduledTaskList);
        modelAndView.addObject("scheduledTaskCount", scheduledTaskCount);
        modelAndView.addObject("pagesCount", pagesCount);

        return modelAndView;
    }



    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public ModelAndView usersListView(@RequestParam(defaultValue = "1") String pageString,
                                      @RequestParam(defaultValue = "") String username,
                                      @RequestParam(defaultValue = "NaN") String status,
                                      @RequestParam(defaultValue = "NaN") String role,
                                      @RequestParam(defaultValue = "NaN") String sf,
                                      @RequestParam(defaultValue = "NaN") String action) {
        int page;
        if (pageString.equals("NaN")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        ModelAndView modelAndView = new ModelAndView();
        if (!sf.equals("NaN")) {
            modelAndView.setViewName("redirect:/admin/users?" + "username=" + username +
                    "&status=" + status + "&role=" + role);
            return modelAndView;
        }
        List<UserAccount> userAccountList;
        Long userAccountCount;
        if (!username.equals("") && !status.equals("NaN") && !role.equals("NaN")) {
            userAccountList = userAccountService
                    .getUserListWithFilterUsernameAndStatusAndRole(page, username, status, role);
            userAccountCount = userAccountService
                    .getCountUserListWithFilterUsernameAndStatusAndRole(username, status, role);
        } else if (!username.equals("") && !role.equals("NaN")) {
            userAccountList = userAccountService
                    .getUserListWithFilterUsernameAndRole(page, username, role);
            userAccountCount = userAccountService
                    .getCountUserListWithFilterUsernameAndRole(username, role);
        } else if (!status.equals("NaN") && !role.equals("NaN")) {
            userAccountList = userAccountService
                    .getUserListWithFilterStatusAndRole(page, status, role);
            userAccountCount = userAccountService
                    .getCountUserListWithFilterStatusAndRole(status, role);
        } else if (!username.equals("") && !status.equals("NaN")) {
            userAccountList = userAccountService
                    .getUserListWithFilterUsernameAndStatus(page,username,status);
            userAccountCount = userAccountService
                    .getCountUserListWithFilterUsernameAndStatus(username, status);
        } else if (!username.equals("")) {
            userAccountList = userAccountService
                    .getUserListWithFilterUsername(page, username);
            userAccountCount = userAccountService
                    .getCountUserListWithFilterUsername(username);
        } else if (!status.equals("NaN")) {
            userAccountList = userAccountService
                    .getUserListWithFilterStatus(page, status);
            userAccountCount = userAccountService
                    .getCountUserListWithFilterStatus(status);
        } else {
            userAccountList = userAccountService.getAllUsers(page);
            userAccountCount = userAccountService.getCountAllUsers();
        }
        int pagesCount = (int) ((userAccountCount + 9) / 10);
        modelAndView.setViewName("/admin/usersList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("userAccountList", userAccountList);
        modelAndView.addObject("userAccountCount", userAccountCount);
        modelAndView.addObject("pagesCount", pagesCount);
        //some actions with param action
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN)")
    @RequestMapping(value = "/admin/create-new-user", method = RequestMethod.GET)
    public ModelAndView createNewUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/userCreate");
        modelAndView.addObject("userAccount", new UserAccount());
        modelAndView.addObject("roleList", userRoleService.getAllRoles());
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN)")
    @RequestMapping(value = "/admin/create-new-user", method = RequestMethod.POST)
    public ModelAndView saveNewUser(@AuthenticationPrincipal UserAccount saveBy,
                                    @ModelAttribute("userAccount")
                                    @Valid UserAccount userAccount,
                                    BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            return modelAndView;
        }
        if (!ValidationUtils.isValidEmail(userAccount.getUsername())) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("usernameError", "usersAccountPage.errorIncorrectEmail");
            return modelAndView;
        }
        if (!ValidationUtils.isValidEmailDomain(userAccount.getUsername())) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("usernameError", "usersAccountPage.errorIncorrectEmailDomain");
            return modelAndView;
        }
        if (!ValidationUtils.isValidPassword(userAccount.getPassword())) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("passwordError", "usersAccountPage.errorIncorrectWithPolicyPassword");
            return modelAndView;
        }
        if (!userAccount.getPassword().equals(userAccount.getPasswordConfirm())) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("passwordError", "usersAccountPage.errorTextPasswordNotMatchedError");
            return modelAndView;
        }
        if (!ValidationUtils.isCyrillicOrLatin(userAccount.getFirstName())) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("firstNameError", "usersAccountPage.errorName");
            return modelAndView;
        }
        if (!ValidationUtils.isCyrillicOrLatin(userAccount.getLastName())) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("lastNameError", "usersAccountPage.errorName");
            return modelAndView;
        }
        if (!ValidationUtils.isValidGender(userAccount.getGender())) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("genderError", "usersAccountPage.errorChooseValue");
            return modelAndView;
        }
        if (userAccount.getUserRoleId() == 0) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("roleError", "usersAccountPage.errorChooseValue");
            return modelAndView;
        }
        if (!userAccountService.saveUser(userAccount, userAccount.getUserRoleId(), saveBy)) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("usernameError","usersAccountPage.errorTextUsernameErrorSameUserAlreadyExists");
            return modelAndView;
        }
        if (!userAccountService.generateNewVerificationCode(userAccount)) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("message", "usersAccountPage.dangerMessageSomthWrong");
            return modelAndView;
        }
        Map<String, Object> emailModel = new HashMap<>();
        emailModel.put("mainUrl", MAIN_DOMAIN_URL);
        emailModel.put("subject", "Verify and Confirm email");
        emailModel.put("to", userAccount.getUsername());
        emailModel.put("verifyurl", userAccountService.generateNewLinkConfirmEmail(userAccount));
        boolean result = emailService.sendEmail("verifyEmail.vm", emailModel);
        if (!result) {
            modelAndView.setViewName("/admin/userCreate");
            modelAndView.addObject("roleList", userRoleService.getAllRoles());
            modelAndView.addObject("message", "usersAccountPage.dangerMessageSomthWrongEmail");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/admin/user/" +
                userAccountService.findByUsername(userAccount.getUsername()).getId() + "?action=new");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN)")
    @RequestMapping(value = "/admin/user/{id}/edit", method = RequestMethod.GET)
    public ModelAndView userEditPage(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/userEdit");
        modelAndView.addObject("userAccount", userAccountService.getById(id));
        modelAndView.addObject("roleList", userRoleService.getAllRoles());
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN)")
    @RequestMapping(value = "/admin/user/{id}/edit", method = RequestMethod.POST)
    public ModelAndView userEditProcess(@PathVariable(value = "id") Long id,
                                        @ModelAttribute("userAccount") UserAccount userAccount,
                                        @AuthenticationPrincipal UserAccount changedBy) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/userEdit");
        if (!ValidationUtils.isCyrillicOrLatin(userAccount.getFirstName())) {
            modelAndView.addObject("firstNameError", "usersAccountPage.errorName");
            return modelAndView;
        }
        if (!ValidationUtils.isCyrillicOrLatin(userAccount.getLastName())) {
            modelAndView.addObject("lastNameError", "usersAccountPage.errorName");
            return modelAndView;
        }
        if (!ValidationUtils.isValidGender(userAccount.getGender())) {
            modelAndView.addObject("genderError", "usersAccountPage.errorChooseValue");
            return modelAndView;
        }
        if (userAccount.getUserRoleId() == 0) {
            modelAndView.addObject("roleError", "usersAccountPage.errorChooseValue");
            return modelAndView;
        }
        userAccountService.changeUserAccountData(id, userAccount, changedBy, userAccount.getUserRoleId());
        modelAndView.setViewName("redirect:/admin/user/" + id + "?action=changed-data");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN)")
    @RequestMapping(value = "/admin/user/{id}/activate", method = RequestMethod.GET)
    public ModelAndView activateUserProcess(@PathVariable(value = "id") Long id,
                                            @AuthenticationPrincipal UserAccount changedBy) {
        ModelAndView modelAndView = new ModelAndView();
        if (!userAccountService.activateUser(id, changedBy)) {
            modelAndView.setViewName("redirect:/admin/user" + id + "?action=activate-bad");
        } else {
            modelAndView.setViewName("redirect:/admin/user" + id + "?action=activate-good");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN)")
    @RequestMapping(value = "/admin/user/{id}/lock", method = RequestMethod.GET)
    public ModelAndView lockUserProcess(@PathVariable(value = "id") Long id,
                                        @AuthenticationPrincipal UserAccount changedBy) {
        ModelAndView modelAndView = new ModelAndView();
        if (!userAccountService.lockUser(id, changedBy)) {
            modelAndView.setViewName("redirect:/admin/user" + id + "?action=lock-bad");
        } else {
            modelAndView.setViewName("redirect:/admin/user" + id + "?action=lock-good");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/admin/user/{id}", method = RequestMethod.GET)
    public ModelAndView userViewPage(@PathVariable(value = "id") Long id,
                                     @RequestParam(defaultValue = "NaN") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/userView");
        modelAndView.addObject("userAccount", userAccountService.getById(id));
        switch (action) {
            case "new" -> modelAndView.addObject("messageGood", "usersAccountPage.goodMessageCreatedUser");
            case "changed-data" -> modelAndView.addObject("messageGood", "usersAccountPage.goodMessageChangedUser");
            case "activate-bad" -> modelAndView.addObject("messageBad", "usersAccountPage.badMessageActivatedUser");
            case "activate-good" -> modelAndView.addObject("messageGood", "usersAccountPage.goodMessageActivatedUser");
            case "lock-bad" -> modelAndView.addObject("messageBad", "usersAccountPage.badMessageLockedUser");
            case "lock-good" -> modelAndView.addObject("messageGood", "usersAccountPage.goodMessageLockedUser");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/admin/scheduled-uploading-leads-vicidial", method = RequestMethod.GET)
    public ModelAndView scheduledUploadingLeadsVicidialPageViewList(@RequestParam(defaultValue = "NaN") String sf,
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
            String view = "redirect:/admin/scheduled-uploading-leads-vicidial";
            if ((dateFromString != null && !dateFromString.isEmpty()) || (dateToString != null && !dateToString.isEmpty())) {
                view += "?dateFrom=" + dateFromString + "&dateTo=" + dateToString;
            }
            modelAndView.setViewName(view);
            return modelAndView;
        }

        List<ScheduledUploadingLeadsVicidial> scheduledUploadingLeadsVicidialList;
        Long scheduledUploadingLeadsVicidialCount;

        if (dateFrom != null && dateTo != null) {
            scheduledUploadingLeadsVicidialList = scheduledUploadingLeadsVicidialService.getScheduledUploadingLeadsVicidialListForDates(dateFrom, dateTo, page);
            scheduledUploadingLeadsVicidialCount = scheduledUploadingLeadsVicidialService.getCountScheduledUploadingLeadsVicidialForDates(dateFrom, dateTo);
        } else if (dateFrom != null) {
            scheduledUploadingLeadsVicidialList = scheduledUploadingLeadsVicidialService.getScheduledUploadingLeadsVicidialListForDateFrom(dateFrom, page);
            scheduledUploadingLeadsVicidialCount = scheduledUploadingLeadsVicidialService.getCountScheduledUploadingLeadsVicidialForDateFrom(dateFrom);
        } else if (dateTo != null) {
            scheduledUploadingLeadsVicidialList = scheduledUploadingLeadsVicidialService.getScheduledUploadingLeadsVicidialListForDateTo(dateTo, page);
            scheduledUploadingLeadsVicidialCount = scheduledUploadingLeadsVicidialService.getCountScheduledUploadingLeadsVicidialForDateTo(dateTo);
        } else {
            scheduledUploadingLeadsVicidialList = scheduledUploadingLeadsVicidialService.getAllScheduledUploadingLeadsVicidialList(page);
            scheduledUploadingLeadsVicidialCount = scheduledUploadingLeadsVicidialService.getCountAllScheduledUploadingLeadsVicidial();
        }

        int pagesCount = (int) ((scheduledUploadingLeadsVicidialCount + 9) / 10);

        modelAndView.setViewName("/admin/scheduledUploadingLeadsVicidialList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("pagesCount", pagesCount);
        modelAndView.addObject("scheduledUploadingLeadsVicidialList", scheduledUploadingLeadsVicidialList);
        modelAndView.addObject("scheduledUploadingLeadsVicidialCount", scheduledUploadingLeadsVicidialCount);

        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/admin/scheduled-checking", method = RequestMethod.GET)
    public ModelAndView scheduledCheckingPageViewList(@RequestParam(defaultValue = "NaN") String sf,
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
            String view = "redirect:/admin/scheduled-checking";
            if ((dateFromString != null && !dateFromString.isEmpty()) || (dateToString != null && !dateToString.isEmpty())) {
                view += "?dateFrom=" + dateFromString + "&dateTo=" + dateToString;
            }
            modelAndView.setViewName(view);
            return modelAndView;
        }

        List<ScheduledChecking> scheduledCheckingList;
        Long scheduledCheckingCount;

        if (dateFrom != null && dateTo != null) {
            scheduledCheckingList = scheduledCheckingService.getScheduledCheckingForDates(dateFrom, dateTo, page);
            scheduledCheckingCount = scheduledCheckingService.getCountScheduledCheckingForDates(dateFrom, dateTo);
        } else if (dateFrom != null) {
            scheduledCheckingList = scheduledCheckingService.getScheduledCheckingForDateFrom(dateFrom, page);
            scheduledCheckingCount = scheduledCheckingService.getCountScheduledCheckingForDateFrom(dateFrom);
        } else if (dateTo != null) {
            scheduledCheckingList = scheduledCheckingService.getScheduledCheckingForDateTo(dateTo, page);
            scheduledCheckingCount = scheduledCheckingService.getCountScheduledCheckingForDateTo(dateTo);
        } else {
            scheduledCheckingList = scheduledCheckingService.getAllScheduledChecking(page);
            scheduledCheckingCount = scheduledCheckingService.getCountAllScheduledChecking();
        }

        int pagesCount = (int) ((scheduledCheckingCount + 9) / 10);

        modelAndView.setViewName("/admin/scheduledCheckingList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("pagesCount", pagesCount);
        modelAndView.addObject("scheduledCheckingList", scheduledCheckingList);
        modelAndView.addObject("scheduledCheckingCount", scheduledCheckingCount);

        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).T_T," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/admin/system-settings", method = RequestMethod.GET)
    public ModelAndView systemSettingsListView(@RequestParam(defaultValue = "1") String pageString) {
        int page;
        if (pageString.equals("NaN")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        ModelAndView modelAndView = new ModelAndView();
        List<SystemSetting> systemSettingList = systemSettingService.getAllSystemSettingList(page);
        Long systemSettingsCount = systemSettingService.getCountAllSystemSettingList();
        int pagesCount = (int) ((systemSettingsCount + 9) / 10);
        modelAndView.setViewName("/admin/systemSettingList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("systemSettingList", systemSettingList);
        modelAndView.addObject("systemSettingsCount", systemSettingsCount);
        modelAndView.addObject("pagesCount", pagesCount);

        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).T_T," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/admin/system-settings/{id}", method = RequestMethod.GET)
    public ModelAndView systemSettingPageView(@PathVariable(value = "id") Long id,
                                              @RequestParam(defaultValue = "NaN") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/systemSettingView");
        modelAndView.addObject("systemSetting", systemSettingService.getById(id));
        if (action.equals("edited")) {
            modelAndView.addObject("messageGood", "message.editSuccessfully");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).T_T," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/admin/system-settings/{id}/edit", method = RequestMethod.GET)
    public ModelAndView systemSettingEditView(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/systemSettingEdit");
        modelAndView.addObject("systemSetting", systemSettingService.getById(id));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).T_T," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/admin/system-settings/{id}/edit", method = RequestMethod.POST)
    public ModelAndView systemSettingEditProcess(@PathVariable(value = "id") Long id,
                                                 @ModelAttribute("systemSetting") SystemSetting systemSetting,
                                                 @AuthenticationPrincipal UserAccount changedBy) {
        ModelAndView modelAndView = new ModelAndView();
        SystemSetting systemSettingFromDB = systemSettingService.getById(id);
        if (!ValidationUtils.isValidSystemSettingValue(systemSettingFromDB.getType(), systemSetting.getValue())) {
            modelAndView.addObject("valueError", "common.incorrectValue");
            modelAndView.addObject("systemSetting", systemSettingFromDB);
            modelAndView.setViewName("/admin/systemSettingEdit");
            return modelAndView;
        }

        if (!systemSettingService.update(systemSetting, changedBy)) {
            modelAndView.addObject("valueError", "common.otherError");
            modelAndView.addObject("systemSetting", systemSettingFromDB);
            modelAndView.setViewName("/admin/systemSettingEdit");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/admin/system-settings/" + id + "?action=edited");
        return modelAndView;
    }


}
