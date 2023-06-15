package com.korotkov.mainCurrentApp.api.creditCRM;

import com.korotkov.creditCRM.model.clients.ClientEmailConfirmedCRM;
import com.korotkov.creditCRM.service.clientCRM.ClientCRMService;
import com.korotkov.mainCurrentApp.model.ClientEmailVerification;
import com.korotkov.mainCurrentApp.service.clientEmailVerification.ClientEmailVerificationService;
import com.korotkov.mainCurrentApp.service.email.EmailCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.MAIN_DOMAIN_URL;
import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Controller
public class ApiCRMController {
    private static final Logger logger = LoggerFactory.getLogger(ApiCRMController.class);
    private static final String REDIRECT_URL_SUCCESSFUL_VERIFICATION = "https://tienoi.com.vn/trang-thai-xac-thuc-email-thanh-cong/";
    private static final String REDIRECT_URL_UNSUCCESSFUL_VERIFICATION = "https://tienoi.com.vn/trang-thai-xac-thuc-email-khong-thanh-cong/";

    private ClientCRMService clientCRMService;
    private EmailCommonService emailService;
    private ClientEmailVerificationService clientEmailVerificationService;


    @Autowired
    public void setClientCRMService(ClientCRMService clientCRMService) {
        this.clientCRMService = clientCRMService;
    }

    @Autowired
    public void setEmailService(EmailCommonService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setClientEmailVerificationService(ClientEmailVerificationService clientEmailVerificationService) {
        this.clientEmailVerificationService = clientEmailVerificationService;
    }


    @RequestMapping(value = "/api/v1/crm/email-verification", method = RequestMethod.POST)
    public ResponseEntity verificationEmailProcessSendEmail(@RequestBody Map<String, String> body) {
        String clientIdString = body.get("clientId");
        String email = body.get("email");

        if (clientIdString == null || clientIdString.equals("") || email == null) {
            return ResponseEntity.badRequest().body("No all requires fields");
        }

        Long clientId = Long.parseLong(clientIdString);

        ClientEmailConfirmedCRM clientEmailConfirmedCRM =
                clientCRMService.getClientEmailConfirmedById(clientId);

        if (clientEmailConfirmedCRM == null || clientEmailConfirmedCRM.getClientId() == null) {
            return ResponseEntity.badRequest().body("Not find client");
        }

        if (!clientEmailConfirmedCRM.getEmail().equals(email)) {
            return ResponseEntity.badRequest().body("Not saved email");
        }

        if (clientEmailConfirmedCRM.isEmailConfirmed()) {
            return ResponseEntity.badRequest().body("Email is already confirmed");
        }

        String verificationUrl = createAndGetVerificationUrlForClient(clientEmailConfirmedCRM.getEmail(),
        clientEmailVerificationService.createNewAndGetCode(clientEmailConfirmedCRM.getClientId(),
                clientEmailConfirmedCRM.getEmail()), String.valueOf(clientEmailConfirmedCRM.getClientId()));

        Map<String, Object> emailModel = new HashMap<>();
        emailModel.put("verificationUrl", verificationUrl);
        emailModel.put("subject", "Tiền Ơi - Xác nhận email");
        emailModel.put("to", clientEmailConfirmedCRM.getEmail());
        emailModel.put("clientName", clientEmailConfirmedCRM.getName());

        boolean result = emailService.sendEmail("verifyClientEmail.vm", emailModel);

        if (!result) {
            return ResponseEntity.badRequest().body("Email error");
        }

        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/security/verification-client-email/{email}/{code}/{clientId}", method = RequestMethod.GET)
    public ModelAndView verificationEmailProcessCheckCodeAndConfirmEmail(@PathVariable(value = "email") String email,
                                                                         @PathVariable(value = "code") String code,
                                                                         @PathVariable(value = "clientId") Long clientId) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            ClientEmailConfirmedCRM clientEmailConfirmedCRM =
                    clientCRMService.getClientEmailConfirmedById(clientId);

            ClientEmailVerification clientEmailVerification =
                    clientEmailVerificationService.getByClientId(clientId);

            if (clientEmailConfirmedCRM == null || clientEmailConfirmedCRM.getClientId() == null ||
                    clientEmailVerification == null || clientEmailVerification.getClientId() == null ||
                    !clientEmailConfirmedCRM.getEmail().equals(email) ||
                    !clientEmailVerification.getEmail().equals(email) || !clientEmailVerification.getCode().equals(code)) {
                modelAndView.setViewName("redirect:" + REDIRECT_URL_UNSUCCESSFUL_VERIFICATION);
            } else {
                if (clientEmailVerificationService.needAddAttemptWithConfirmEmail(clientEmailConfirmedCRM.getClientId())) {
                    clientCRMService.setEmailConfirmedToTrueAndAddAttempt(clientEmailConfirmedCRM.getClientId());
                } else {
                    clientCRMService.setEmailConfirmedToTrue(clientEmailConfirmedCRM.getClientId());
                }
                clientEmailVerification.setDone(true);
                clientEmailVerification.setDoneAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                clientEmailVerificationService.update(clientEmailVerification);
                modelAndView.setViewName("redirect:" + REDIRECT_URL_SUCCESSFUL_VERIFICATION);
            }
        } catch (Exception e) {
            logger.error("tried verification URL and got error: " + e.getMessage());
            modelAndView.setViewName("redirect:" + REDIRECT_URL_UNSUCCESSFUL_VERIFICATION);
        }

        return modelAndView;
    }



    public static String createAndGetVerificationUrlForClient(String email, String code, String clientId) {
        return MAIN_DOMAIN_URL + "/" + "security/verification-client-email" + "/" + email + "/" + code + "/" + clientId;
    }

}
