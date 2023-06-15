package com.korotkov.mainCurrentApp.controller;

import com.korotkov.mainCurrentApp.service.email.EmailCommonService;
import com.korotkov.mainCurrentApp.service.userAccount.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VerificationController {
    UserAccountService userAccountService;
    EmailCommonService emailService;

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Autowired
    public void setEmailService(EmailCommonService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping(value = "/security/verification/email/{idUser}/{code}",method = RequestMethod.GET)
    public ModelAndView verificationEmail(@PathVariable(value = "idUser") Long idUser,
                                          @PathVariable(value = "code") String code) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/security/registration");
        if (!userAccountService.updateEmailConfirmedToTrue(idUser, code)) {
            modelAndView.addObject("needDynamicCode","error");
            return modelAndView;
        }
        modelAndView.addObject("needDynamicCode", "false");
        return modelAndView;
    }

    @RequestMapping(value = "/security/verification/change-password/{idUser}/{code}", method = RequestMethod.GET)
    public ModelAndView verificationChangePassword(@PathVariable(value = "idUser") Long idUser,
                                                   @PathVariable(value = "code") String code) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("security/login-change-exp-password");
        if (!userAccountService.saveNewPasswordFinal(idUser, code)) {
            modelAndView.addObject("changedPass", "error");
            return modelAndView;
        }
        modelAndView.addObject("changedPass", "true");
        return modelAndView;
    }
}
