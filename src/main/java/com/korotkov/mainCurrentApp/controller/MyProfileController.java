package com.korotkov.mainCurrentApp.controller;

import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.service.userAccount.UserAccountService;
import com.korotkov.mainCurrentApp.validation.ValidationUtils;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class MyProfileController {
    UserAccountService userAccountService;

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping(value = "/myprofile/profile/view", method = RequestMethod.GET)
    public ModelAndView profilePageView(@AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/myProfile/myProfileView");
        modelAndView.addObject("userAccount", userAccountService.getById(userAccount.getId()));
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/profile/change-password",method = RequestMethod.GET)
    public ModelAndView pageMyProfileChangePassword(@AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/myProfile/myProfileEditPassword");
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setUsername(userAccount.getUsername());
        modelAndView.addObject("userAccount", newUserAccount);
        return modelAndView;
    }

    @RequestMapping(value = "/myprofile/profile/change-password", method = RequestMethod.POST)
    public ModelAndView pageMyProfileChangePasswordPost(@ModelAttribute("userAccount")
                                                        @Valid UserAccount userAccount,
                                                        BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/myProfile/myProfileEditPassword");
            return modelAndView;
        }
        if (!userAccountService.checkUserPassword(userAccount)) {
            modelAndView.setViewName("/myProfile/myProfileEditPassword");
            modelAndView.addObject("errorBadPassword","myProfilePage.errorBadPassword");
            return modelAndView;
        }
        if (!ValidationUtils.isValidPassword(userAccount.getNewPassword())) {
            modelAndView.setViewName("/myProfile/myProfileEditPassword");
            modelAndView.addObject("newPasswordError", "loginChangePasswordPage.errorIncorrectWithPolicyPassword");
            return modelAndView;
        }
        if (!userAccount.getNewPassword().equals(userAccount.getPasswordConfirm())) {
            modelAndView.setViewName("/myProfile/myProfileEditPassword");
            modelAndView.addObject("newPasswordError", "loginChangePasswordPage.errorTextPasswordNotMatchedError");
            return modelAndView;
        }
        if (userAccount.getPassword().equals(userAccount.getNewPassword())) {
            modelAndView.setViewName("/myProfile/myProfileEditPassword");
            modelAndView.addObject("newPasswordError", "loginChangePasswordPage.errorTextNewPasswordSameAsOldPassword");
            return modelAndView;
        }
        modelAndView.setViewName("/myProfile/myProfileView");
        modelAndView.addObject("userAccount", userAccountService.saveNewPasswordWithoutConfirm(userAccount));
        modelAndView.addObject("message", "myProfilePage.messageSuccessChangePassword");
        return modelAndView;
    }
}
