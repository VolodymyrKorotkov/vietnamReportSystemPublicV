package com.korotkov.mainCurrentApp.service.scheduledTask;

import com.korotkov.creditCRM.model.CheckerEntity;
import com.korotkov.creditCRM.model.clients.*;
import com.korotkov.creditCRM.service.clientCRM.ClientCRMService;
import com.korotkov.creditCRM.service.debtCollection.DebtCollectionService;
import com.korotkov.creditCRM.service.checkerEntity.CheckerEntityService;
import com.korotkov.mainCurrentApp.api.currencyConverterApi.CurrencyConverterApiService;
import com.korotkov.mainCurrentApp.enums.*;
import com.korotkov.mainCurrentApp.model.ScheduledChecking;
import com.korotkov.mainCurrentApp.model.SendingEmail;
import com.korotkov.mainCurrentApp.service.applicationsQueueChecking.ApplicationsQueueCheckingService;
import com.korotkov.mainCurrentApp.service.clientEmailVerification.ClientEmailVerificationService;
import com.korotkov.mainCurrentApp.service.email.EmailCommonService;
import com.korotkov.mainCurrentApp.service.file.ExcelBuildReport;
import com.korotkov.mainCurrentApp.service.reportBuilder.ReportBuilderService;
import com.korotkov.mainCurrentApp.service.scheduledChecking.ScheduledCheckingService;
import com.korotkov.mainCurrentApp.service.scheduledTask.modelService.SchedTaskService;
import com.korotkov.mainCurrentApp.service.sendingEmail.SendingEmailService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.korotkov.mainCurrentApp.api.creditCRM.ApiCRMController.createAndGetVerificationUrlForClient;
import static com.korotkov.mainCurrentApp.config.ConfigConstants.*;
import static com.korotkov.mainCurrentApp.service.email.EmailConfig.SUBJECT;
import static com.korotkov.mainCurrentApp.service.email.EmailConfig.TO;


@Service
@RequiredArgsConstructor
public class ScheduledTaskService {
    private final CurrencyConverterApiService currencyConverterApiService;
    private final ReportBuilderService reportBuilderService;
    private final EmailCommonService emailService;
    private final SchedTaskService schedTaskService;
    private final ClientCRMService clientCRMService;
    private final ClientEmailVerificationService clientEmailVerificationService;
    private final SendingEmailService sendingEmailService;
    private final CheckerEntityService checkerEntityService;
    private final ScheduledCheckingService scheduledCheckingService;
    private final ApplicationsQueueCheckingService applicationsQueueCheckingService;
    private final DebtCollectionService debtCollectionService;


//    @Async("emailScheduler")
//    @Scheduled(cron = "0 15 8 * * *", zone = TIME_ZONE)
//    public void sendEmailToRegisteredClientsWithoutConfirmedEmail1() {
//        int[] daysAfterRegistration = new int [] {1, 10};
//        sendEmailToRegisteredClientsWithoutConfirmedEmail(daysAfterRegistration);
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 16 8 * * *", zone = TIME_ZONE)
//    public void sendEmailToRegisteredClientsWithoutConfirmedEmail2() {
//        int[] daysAfterRegistration = new int [] {100, 200};
//        sendEmailToRegisteredClientsWithoutConfirmedEmail(daysAfterRegistration);
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 17 8 * * *", zone = TIME_ZONE)
//    public void sendEmailToRegisteredClientsWithoutConfirmedEmail3() {
//        int[] daysAfterRegistration = new int [] {350, 500};
//        sendEmailToRegisteredClientsWithoutConfirmedEmail(daysAfterRegistration);
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 23 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoans1Day() {
//        int[] daysAfterFinishedLastLoan = new int [] {1};
//
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_1_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_1_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Đăng ký ngay - nhân ngay khoản vay mới lên đến 10 triệu đồng!",
//                "passiveClient1DayAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 24 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoans2Day() {
//        int[] daysAfterFinishedLastLoan = new int [] {2};
//
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_2_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_2_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Ưu đãi áp dụng trong 24h sắp hết hạn!",
//                "passiveClient2DayAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 25 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoansFrom10Days() {
//        int[] daysAfterFinishedLastLoan = new int [] {10, 100, 340};
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_10_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_10_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Vay ngay với lãi xuất chỉ từ 1%!",
//                "passiveClient10DaysAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 26 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoansFrom20Days() {
//        int[] daysAfterFinishedLastLoan = new int [] {20, 130, 380};
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_20_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_20_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Bạn được tặng  một lượt quay may mắn miễn phí!",
//                "passiveClient20DaysAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 27 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoansFrom30Days() {
//        int[] daysAfterFinishedLastLoan = new int [] {30, 180, 450};
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_30_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_30_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Bạn nhận được một khoản vay mới đến 5 triệu đồng",
//                "passiveClient30DaysAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 28 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoansFrom50Days() {
//        int[] daysAfterFinishedLastLoan = new int [] {50, 230, 500};
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_50_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_50_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Bạn được duyệt đến 5 triệu đồng!",
//                "passiveClient50DaysAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 29 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoansFrom70Days() {
//        int[] daysAfterFinishedLastLoan = new int [] {70, 280, 560};
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_70_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_70_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Bạn nhận được một ưu đãi vay mới!",
//                "passiveClient70DaysAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 30 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoansFrom40Days() {
//        int[] daysAfterFinishedLastLoan = new int [] {40, 150, 320};
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_40_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_40_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Chúng tôi gửi đến bạn một chương trình vay hấp dẫn. Tham gia ngay",
//                "passiveClient40DaysAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 31 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoansFrom60Days() {
//        int[] daysAfterFinishedLastLoan = new int [] {60, 210, 360};
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_60_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_60_and_more_days&redirect=https://tienoi.com.vn/mybonus/",
//                "Bạn có biết Tcoin dùng để làm gì không? Khám phá ngay",
//                "passiveClient60DaysAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 32 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoansFrom80Days() {
//        int[] daysAfterFinishedLastLoan = new int [] {80, 250, 420};
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_80_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_80_and_more_days&redirect=https://tienoi.com.vn/wheeloffortune/",
//                "Chương trình vòng quay may mắn nhận ngay xe PCX và nhiều phần quà hấp dẫn dành cho bạn.",
//                "passiveClient80DaysAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 33 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToPassiveClientsWithoutLoansFrom90Days() {
//        int[] daysAfterFinishedLastLoan = new int [] {90, 280, 470};
//        sendEmailsToPassiveClientsWithoutLoans(daysAfterFinishedLastLoan,
//                String.valueOf(EmailSendingTitleEnum.PASSIVE_CLIENT_90_DAY_AFTER_FINISHED_LOAN),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_passive_client_90_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Chúc mừng bạn đã là 1 trong 100 khách hàng may mắn của Tiền Ơi. Khám phá ngay!",
//                "passiveClient90DaysAfterFinished.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 34 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToRegisteredClientsWithoutApplicationsFrom1Day() {
//        int[] daysAfterRegistration = new int[] {1, 25, 170, 400};
//        sendEmailsToRegisteredClientsWithoutApplications(daysAfterRegistration,
//                String.valueOf(EmailSendingTitleEnum.REGISTERED_CLIENT_WITHOUT_APPLICATIONS_1_DAY),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_registered_1_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Hồ sơ của bạn sắp hoàn thành rồi. Cơ hội nhận khoản vay 10 triệu trước mắt",
//                "registeredClientWithoutAppsFrom1Day.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 35 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToRegisteredClientsWithoutApplicationsFrom5Days() {
//        int[] daysAfterRegistration = new int[] {5, 50, 250, 450};
//        sendEmailsToRegisteredClientsWithoutApplications(daysAfterRegistration,
//                String.valueOf(EmailSendingTitleEnum.REGISTERED_CLIENT_WITHOUT_APPLICATIONS_5_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_registered_5_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Hoàn thành hồ sơ để nhận khoản vay 10 triệu nào!",
//                "registeredClientWithoutAppsFrom5Days.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 36 11 * * *", zone = TIME_ZONE)
//    public void sendEmailsToRegisteredClientsWithoutApplicationsFrom10Days() {
//        int[] daysAfterRegistration = new int[] {10, 100, 320, 500};
//        sendEmailsToRegisteredClientsWithoutApplications(daysAfterRegistration,
//                String.valueOf(EmailSendingTitleEnum.REGISTERED_CLIENT_WITHOUT_APPLICATIONS_10_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_registered_10_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Chỉ còn 1 bước nữa là nhận được tiền",
//                "registeredClientWithoutAppsFrom10Days.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 32 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsRejected30Days() {
//        int[] daysAfterLastApp = new int[] {30};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.REJECTED),
//                String.valueOf(EmailSendingTitleEnum.REJECTED_APPS_WITHOUT_NEW_APP_AFTER_30_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_rejected_30_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Hồ sơ của bạn đã được phê duyệt lại. Đăng ký vay ngay chỉ trong 2 phút!",
//                "rejectedAppsWithoutApps30Days.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 33 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsRejected40Days() {
//        int[] daysAfterLastApp = new int[] {40};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.REJECTED),
//                String.valueOf(EmailSendingTitleEnum.REJECTED_APPS_WITHOUT_NEW_APP_AFTER_40_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_rejected_40_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Chúc mừng bạn hồ sơ vay của bạn đã được xem xét lại thành công!",
//                "rejectedAppsWithoutApps40Days.vm");
//    }
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 34 8 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsApprovedAndNotConfirmed1Day() {
//        int[] daysAfterLastApp = new int[] {1};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.APPROVED),
//                String.valueOf(EmailSendingTitleEnum.APPROVED_APPS_NOT_CONFIRMED_AFTER_1_DAY),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_approved_1_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Chúc mừng hồ sơ của bạn đã được duyệt thành công. Vào ký hợp đồng ngay",
//                "approvedAppsAndNotConfirmed1Day.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 35 8 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsApprovedAndNotConfirmed2Day() {
//        int[] daysAfterLastApp = new int[] {2};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.APPROVED),
//                String.valueOf(EmailSendingTitleEnum.APPROVED_APPS_NOT_CONFIRMED_AFTER_2_DAY),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_approved_2_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Bạn cần xác nhận Hợp đồng vay!",
//                "approvedAppsAndNotConfirmed2Day.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 36 8 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsApprovedAndNotConfirmed3Day() {
//        int[] daysAfterLastApp = new int[] {3, 4};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.APPROVED),
//                String.valueOf(EmailSendingTitleEnum.APPROVED_APPS_NOT_CONFIRMED_AFTER_3_DAY),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_approved_3_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Hồ sơ đã được duyệt. Vào ký hợp đồng để nhận tiền ngay",
//                "approvedAppsAndNotConfirmed3Day.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 37 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsCanceledByClient1Day() {
//        int[] daysAfterLastApp = new int[] {1};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.CANCELED_BY_CLIENT),
//                String.valueOf(EmailSendingTitleEnum.APPS_CANCELED_BY_CLIENT_AFTER_1_DAY),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_canceled_by_client_1_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Bạn đã hủy đơn vay? Thử lại ngay",
//                "canceledByClientApps1Day.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 38 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsCanceledByClientFrom2Days() {
//        int[] daysAfterLastApp = new int[] {2, 25};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.CANCELED_BY_CLIENT),
//                String.valueOf(EmailSendingTitleEnum.APPS_CANCELED_BY_CLIENT_AFTER_FROM_2_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_canceled_by_client_2_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Có thể bạn chưa hài lòng với lần phê duyệt trước đây, hôm nay Tiền Ơi gửi đến bạn một khoản vay mới!",
//                "canceledByClientApps2Days.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 39 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsCanceledByClientFrom5Days() {
//        int[] daysAfterLastApp = new int[] {5, 50};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.CANCELED_BY_CLIENT),
//                String.valueOf(EmailSendingTitleEnum.APPS_CANCELED_BY_CLIENT_AFTER_FROM_5_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_canceled_by_client_5_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Hồ sơ vay của bạn đã đủ điều kiện phê duyệt của chúng tôi trước đó",
//                "canceledByClientApps5Days.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 40 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsCanceledByClientFrom10Days() {
//        int[] daysAfterLastApp = new int[] {10, 90};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.CANCELED_BY_CLIENT),
//                String.valueOf(EmailSendingTitleEnum.APPS_CANCELED_BY_CLIENT_AFTER_FROM_10_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_canceled_by_client_10_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Đăng ký lại ngay để nhận khoản vay 7 triệu từ Tiền Ơi",
//                "canceledByClientApps10Days.vm");
//    }
//
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 41 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsCanceledBySystem1Day() {
//        int[] daysAfterLastApp = new int[] {1};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.CANCELED_BY_SYSTEM),
//                String.valueOf(EmailSendingTitleEnum.APPS_CANCELED_BY_SYSTEM_AFTER_1_DAY),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_canceled_by_system_1_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Hồ sơ của bạn đã bị hủy do không ký hợp đồng. Nhưng đừng lo lắng, hãy làm lại hồ sơ ngay để nhận khoản vay mới 5 triệu dành riêng cho bạn.",
//                "canceledBySystemApps1Day.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 42 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsCanceledBySystemFrom2Days() {
//        int[] daysAfterLastApp = new int[] {2, 25};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.CANCELED_BY_SYSTEM),
//                String.valueOf(EmailSendingTitleEnum.APPS_CANCELED_BY_SYSTEM_AFTER_FROM_2_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_canceled_by_system_2_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Đăng ký khoản vay mới với hạn mức cao hơn!",
//                "canceledBySystemApps2Days.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 43 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsCanceledBySystemFrom5Days() {
//        int[] daysAfterLastApp = new int[] {5, 50};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.CANCELED_BY_SYSTEM),
//                String.valueOf(EmailSendingTitleEnum.APPS_CANCELED_BY_SYSTEM_AFTER_FROM_5_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_canceled_by_system_5_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Tiền ơi là công ty duy nhất...",
//                "canceledBySystemApps5Days.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 44 12 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsCanceledBySystemFrom10Days() {
//        int[] daysAfterLastApp = new int[] {10, 90};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.CANCELED_BY_SYSTEM),
//                String.valueOf(EmailSendingTitleEnum.APPS_CANCELED_BY_SYSTEM_AFTER_FROM_10_DAYS),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_canceled_by_system_10_and_more_days&redirect=https://app.tienoi.com.vn/auth/login",
//                "Tất cả Khách hàng hôm nay được duyệt đến 10 triệu!",
//                "canceledBySystemApps10Days.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 45 8 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsSentToClientForRevision1Day() {
//        int[] daysAfterLastApp = new int[] {1};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.SENT_TO_CLIENT_FOR_REVISION),
//                String.valueOf(EmailSendingTitleEnum.SENT_TO_CLIENT_FOR_REVISION_1_DAY),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_revision_1_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Hồ sơ của bạn sắp hoàn tất. Kiểm tra lại thông tin để hoàn thiện hồ sơ ngay",
//                "sentToClientForRevision1Day.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 46 8 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsSentToClientForRevision2Day() {
//        int[] daysAfterLastApp = new int[] {2};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.SENT_TO_CLIENT_FOR_REVISION),
//                String.valueOf(EmailSendingTitleEnum.SENT_TO_CLIENT_FOR_REVISION_2_DAY),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_revision_2_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Hồ sơ của bạn sắp thành công. Vui lòng kiểm tra lại",
//                "sentToClientForRevision2Day.vm");
//    }
//
//
//    @Async("emailScheduler")
//    @Scheduled(cron = "0 47 8 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithLastAppAsSentToClientForRevision3Day() {
//        int[] daysAfterLastApp = new int[] {3, 4};
//
//        sendEmailsToClientsWithLastAppAsSomeStatus(daysAfterLastApp,
//                String.valueOf(ApplicationStatusesCRMEnum.SENT_TO_CLIENT_FOR_REVISION),
//                String.valueOf(EmailSendingTitleEnum.SENT_TO_CLIENT_FOR_REVISION_3_DAY),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_revision_3_day&redirect=https://app.tienoi.com.vn/auth/login",
//                "Bạn cần bổ sung hồ sơ vay!",
//                "sentToClientForRevision3Day.vm");
//    }


    @Async("emailScheduler")
    @Scheduled(cron = "0 48 8 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindPaymentsMinus3Days() {
        int [] daysBeforeMaturityDate = new int[] {3};

        sendEmailsToClientsForRemindPayments(daysBeforeMaturityDate,
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_BEFORE_3_DAYS),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_payment_before_3_days&redirect=https://tienoi.com.vn/repayment/",
                "Nhắc khách hàng về khoản vay",
                "remindPaymentsBefore3days.vm");
    }

    @Async("emailScheduler")
    @Scheduled(cron = "0 49 8 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindPaymentsMinus1Days() {
        int [] daysBeforeMaturityDate = new int[] {1};

        sendEmailsToClientsForRemindPayments(daysBeforeMaturityDate,
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_BEFORE_1_DAY),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_payment_before_1_day&redirect=https://tienoi.com.vn/repayment/",
                "Thông tin thanh toán",
                "remindPaymentsBefore1day.vm");
    }

    @Async("emailScheduler")
    @Scheduled(cron = "0 50 8 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindPaymentsMinus0Days() {
        int [] daysBeforeMaturityDate = new int[] {0};

        sendEmailsToClientsForRemindPayments(daysBeforeMaturityDate,
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_BEFORE_0_DAYS),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_payment_today&redirect=https://tienoi.com.vn/repayment/",
                "Ngày thanh toán Hợp đồng vay",
                "remindPaymentsBefore0days.vm");
    }

    @Async("emailScheduler")
    @Scheduled(cron = "0 51 8 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindPaymentsGracePeriodP() {
        sendEmailsToClientsForRemindPaymentsGracePeriod(
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_GRACE_PERIOD),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_grace&redirect=https://tienoi.com.vn/repayment/",
                "Hỗ trợ 1 ngày ân hạn cho Khách!",
                "remindPaymentsGracePeriod.vm");
    }

    @Async("emailScheduler")
    @Scheduled(cron = "0 3 9 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindExpiredLoans5Dpd() {
        int [] overdueDays = new int[] {5};
        sendEmailsToClientsForRemindExpiredLoans(overdueDays,
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_EXPIRED_LOANS_DPD_5),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_expired_5_dpd&redirect=https://tienoi.com.vn/repayment/",
                "Thư nhắc nợ lần 1",
                "remindPaymentsExpiredDPD5.vm");
    }

    @Async("emailScheduler")
    @Scheduled(cron = "0 4 9 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindExpiredLoans10Dpd() {
        int [] overdueDays = new int[] {10};
        sendEmailsToClientsForRemindExpiredLoans(overdueDays,
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_EXPIRED_LOANS_DPD_10),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_expired_10_dpd&redirect=https://tienoi.com.vn/repayment/",
                "Thư nhắc nợ lần 2",
                "remindPaymentsExpiredDPD10.vm");
    }

    @Async("emailScheduler")
    @Scheduled(cron = "0 5 9 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindExpiredLoans25Dpd() {
        int [] overdueDays = new int[] {25};
        sendEmailsToClientsForRemindExpiredLoans(overdueDays,
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_EXPIRED_LOANS_DPD_25),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_expired_25_dpd&redirect=https://tienoi.com.vn/repayment/",
                "Hỗ trợ giảm lãi đóng khoản vay",
                "remindPaymentsExpiredDPD25.vm");
    }

    @Async("emailScheduler")
    @Scheduled(cron = "0 6 9 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindExpiredLoans40Dpd() {
        int [] overdueDays = new int[] {40};
        sendEmailsToClientsForRemindExpiredLoans(overdueDays,
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_EXPIRED_LOANS_DPD_40),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_expired_40_dpd&redirect=https://tienoi.com.vn/repayment/",
                "Thông báo quan trọng về khoản vay!",
                "remindPaymentsExpiredDPD40.vm");
    }

    @Async("emailScheduler")
    @Scheduled(cron = "0 7 9 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindExpiredLoans60Dpd() {
        int [] overdueDays = new int[] {60};
        sendEmailsToClientsForRemindExpiredLoans(overdueDays,
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_EXPIRED_LOANS_DPD_60),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_expired_60_dpd&redirect=https://tienoi.com.vn/repayment/",
                "Hỗ trợ thanh toán tiền gốc để đóng khoản vay!",
                "remindPaymentsExpiredDPD60.vm");
    }

    @Async("emailScheduler")
    @Scheduled(cron = "0 8 9 * * *", zone = TIME_ZONE)
    public void sendEmailsToClientsForRemindExpiredLoans75Dpd() {
        int [] overdueDays = new int[] {75};
        sendEmailsToClientsForRemindExpiredLoans(overdueDays,
                String.valueOf(EmailSendingTitleEnum.PAYMENT_REMINDER_EXPIRED_LOANS_DPD_75),
                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=remind_expired_75_dpd&redirect=https://tienoi.com.vn/repayment/",
                "HĐ đã được chuyển qua bên công ty thứ 3",
                "remindPaymentsExpiredDPD75.vm");
    }


//    @Async("emailScheduler")
//    @Scheduled(cron = "0 18 14 * * *", zone = TIME_ZONE)
//    public void sendEmailsToClientsWithBirthDayToday() {
//        sendEmailsToClientsWithBirthDay(LocalDate.now(ZoneId.of(TIME_ZONE)),
//                String.valueOf(EmailSendingTitleEnum.DATE_OF_BIRTH),
//                "https://app.tienoi.com.vn/portal/api/v1/public/affiliate/processAndRedirect?utm_source=email&utm_campaign=promo_birthday&redirect=https://app.tienoi.com.vn/auth/login",
//                "happyBirthday.vm");
//    }













    // tasks for manual sending emails

    @Async("emailManual")
    @Scheduled(cron = "0 */15 * * * *", zone = TIME_ZONE)
    public void checkAndSendManualSendingEmail() {
        SendingEmail sendingEmail = sendingEmailService.getOnePlannedSendingEmailForStart();
        if (sendingEmail != null) {

            LocalDate dateNow = LocalDate.now(ZoneId.of(TIME_ZONE));

            List<ClientNameAndEmail> clientNameAndEmailList = new ArrayList<>();

            if (sendingEmail.isAllActiveClientsWithoutExpiredLoans()) {
                clientNameAndEmailList.addAll(clientCRMService.getClientsWithActiveLoans());
            }

            if (sendingEmail.isRepeatPassiveClients()) {
                LocalDate dateFromPassive = dateNow.minusDays(sendingEmail.getRepeatPassiveClientsDaysFrom());
                LocalDate dateToPassive = dateNow.minusDays(sendingEmail.getRepeatPassiveClientsDaysTo());
                clientNameAndEmailList.addAll(clientCRMService.getPassiveClientsWithoutLoans(dateFromPassive, dateToPassive));
            }

            if (sendingEmail.isClientsWithExpiredLoan()) {
                clientNameAndEmailList
                        .addAll(clientCRMService
                                .getClientsWithExpiredLoansForNow(sendingEmail.getClientsWithExpiredLoanDaysFrom(),
                                        sendingEmail.getClientsWithExpiredLoanDaysTo()));
            }

            if (sendingEmail.isRegisteredClientsWithoutApplications()) {
                LocalDate dateFromReg = dateNow.minusDays(sendingEmail.getRegisteredClientsWithoutApplicationsDaysFrom());
                LocalDate dateToReg = dateNow.minusDays(sendingEmail.getRegisteredClientsWithoutApplicationsDaysTo());
                clientNameAndEmailList.addAll(clientCRMService
                        .getRegisteredClientsWithoutApplications(dateFromReg, dateToReg));
            }

            sendingEmailService.updateAsStarted(sendingEmail, clientNameAndEmailList.size());

            int countSuccessSentEmails = 0;

            Map<String, Object> emailModel = new HashMap<>();
            emailModel.put("clickLink", sendingEmail.getTemplateSendingEmail().getLinkForClick());
            emailModel.put(SUBJECT, sendingEmail.getTemplateSendingEmail().getSubjectEmail());
            emailModel.put("bodyEmailTitle", sendingEmail.getTemplateSendingEmail().getBodyEmailTitle());
            emailModel.put("bodyEmailText", sendingEmail.getTemplateSendingEmail().getBodyEmailText());
            emailModel.put("buttonEmailText", sendingEmail.getTemplateSendingEmail().getButtonEmailText());
            emailModel.put("bannerLink", sendingEmail.getTemplateSendingEmail().getBannerLink());

            for (ClientNameAndEmail clientNameAndEmail : clientNameAndEmailList) {
                emailModel.put(TO, clientNameAndEmail.getEmail());
                emailModel.put("clientName", clientNameAndEmail.getName());
                boolean result = emailService.sendEmail("manualEmailSendingWithEmailTemplate.vm", emailModel);

                if (result) {
                    countSuccessSentEmails++;
                }
            }

            sendingEmailService.updateAsCompleted(sendingEmail.getId(), countSuccessSentEmails);

        }
    }



    @Async("taskScheduler")
    @Scheduled(cron = "0 0 5 * * *", zone = TIME_ZONE)
    public void canceledStartedButNotCompletedSendingEmail() {
        sendingEmailService
                .updateStatusAsCanceledForNotCompletedSendingEmail(LocalDate
                        .now(ZoneId.of(TIME_ZONE)).minusWeeks(1));
    }



    // tasks for checking main systems for potential bags and errors


//    @Async("taskScheduler")
//    @Scheduled(cron = "0 */15 * * * *", zone = TIME_ZONE)
//    public void checkSystemPotentialProblemsAppsOnLongDMS() {
//        ScheduledChecking scheduledChecking =
//                scheduledCheckingService
//                        .getUniqueNotFinishedScheduledChecking(String.valueOf(ScheduledCheckingTitleEnum.APPLICATION_ON_LONG_DMS));
//        List<CheckerEntity> applicationsOnLongDMS = checkerEntityService.getApplicationsOnLongDMS(5);
//
//        if (applicationsOnLongDMS.isEmpty()) {
//            if (scheduledChecking != null && scheduledChecking.getId() != null) {
//                scheduledChecking
//                        .setStatus(String.valueOf(ScheduledCheckingStatusEnum.FINISHED));
//                scheduledChecking.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                scheduledCheckingService.update(scheduledChecking);
//                String message = "There are no more applications in the system for now with a long decision-making process. The last recorded such problem was at " +
//                        reformatDateTimeToString(scheduledChecking.getStartedAt()) + " and was finished at " +
//                        reformatDateTimeToString(scheduledChecking.getFinishedAt()) + ".";
//                sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                        "https://tienoi.com.vn/wp-content/uploads/2022/10/shutterstock_142333726b.jpg",
//                        "Applications finished being in \"Decision-making process\" status for a long time.",
//                        message, "NO MORE long Decision-making process");
//            }
//        } else {
//            if (scheduledChecking == null || scheduledChecking.getId() == null) {
//                scheduledCheckingService
//                        .createNewScheduledCheckingAsStarted(String.valueOf(ScheduledCheckingTitleEnum.APPLICATION_ON_LONG_DMS),
//                                applicationsOnLongDMS.size());
//                String message = "Some of the applications have been in the \"Decision-making process\" status for a long time. " +
//                        "The number of applications that are more than 5 minutes in the status of \"Decision-making process\": " +
//                        applicationsOnLongDMS.size() +
//                        ". IDs of these applications: ";
//                int i = 0;
//                for (CheckerEntity entity : applicationsOnLongDMS) {
//                    message += entity.getId();
//                    if (i++ != applicationsOnLongDMS.size() - 1) {
//                        message += ", ";
//                    } else {
//                        message += ". ";
//                    }
//                }
//                message += "When the system notices that this problem no longer exists, you will receive an additional notification about this.";
//                sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                        "https://tienoi.com.vn/wp-content/uploads/2022/10/57936397-warning-sign-yellow-warning-sign-warning-sign-icon-warning-sign-on-white-warning-sign-vector-warning.jpg",
//                        "Applications are in \"Decision-making process\" status for a long time.",
//                        message, "Attention! Long Decision-making process");
//            }
//        }
//    }


    @Async("taskScheduler")
    @Scheduled(cron = "0 3 3,15 * * *", zone = TIME_ZONE)
    public void checkSystemPotentialProblemsCreateExpiredLoansOnDebtCollectionList() {
        ScheduledChecking scheduledChecking =
                scheduledCheckingService
                        .getUniqueNotFinishedScheduledChecking(String.valueOf(ScheduledCheckingTitleEnum.CHECK_CREATING_DEBT_COLLECTION));
        Long count = checkerEntityService.getCountLoansWithExpiredAndWithoutActiveDebtCollection();

        if (count == 0) {
            if (scheduledChecking != null && scheduledChecking.getId() != null) {
                scheduledChecking
                        .setStatus(String.valueOf(ScheduledCheckingStatusEnum.FINISHED));
                scheduledChecking.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                scheduledCheckingService.update(scheduledChecking);
                String message = "No more uncreated debt collections for expired loans. The last recorded such problem was at " +
                        reformatDateTimeToString(scheduledChecking.getStartedAt()) + " and was finished at " +
                        reformatDateTimeToString(scheduledChecking.getFinishedAt()) + ".";
                sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
                        "https://tienoi.com.vn/wp-content/uploads/2022/10/shutterstock_142333726b.jpg",
                        "All expired loans are already created in debt collection's block.",
                        message, "NO MORE uncreated debt collections");
            }
        } else {
            if (scheduledChecking == null || scheduledChecking.getId() == null) {
                scheduledCheckingService
                        .createNewScheduledCheckingAsStarted(String.valueOf(ScheduledCheckingTitleEnum.CHECK_CREATING_DEBT_COLLECTION),
                                count.intValue());
                String message = "There might be a potential problem. Not all expired loans have entries in the Debt Collection block. Number of entries not created: " +
                count + ". When the system notices that this problem no longer exists, you will receive an additional notification about this.";
                sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
                        "https://tienoi.com.vn/wp-content/uploads/2022/10/57936397-warning-sign-yellow-warning-sign-warning-sign-icon-warning-sign-on-white-warning-sign-vector-warning.jpg",
                        "There are expired loans, which were not created in debt collection's block.",
                        message, "Attention! Uncreated debt collections");
            }
        }
    }

    @Async("taskScheduler")
    @Scheduled(cron = "0 3 2,14 * * *", zone = TIME_ZONE)
    public void checkSystemPotentialProblemsInCalculationAllLoans() {
        ScheduledChecking scheduledChecking =
                scheduledCheckingService
                        .getUniqueNotFinishedScheduledChecking(String.valueOf(ScheduledCheckingTitleEnum.CHECK_CALCULATION_ALL_LOANS));
        Long count = checkerEntityService.getCountLoansWhichNotCalculated();

        if (count == 0) {
            if (scheduledChecking != null && scheduledChecking.getId() != null) {
                scheduledChecking
                        .setStatus(String.valueOf(ScheduledCheckingStatusEnum.FINISHED));
                scheduledChecking.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                scheduledCheckingService.update(scheduledChecking);

                String message = "No more loans without full calculation. The last recorded such problem was at " +
                        reformatDateTimeToString(scheduledChecking.getStartedAt()) + " and was finished at " +
                        reformatDateTimeToString(scheduledChecking.getFinishedAt()) + ".";
                sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
                        "https://tienoi.com.vn/wp-content/uploads/2022/10/shutterstock_142333726b.jpg",
                        "All loans are already full calculated",
                        message, "NO MORE loans without full calculation");
            }
        } else {
            if (scheduledChecking == null || scheduledChecking.getId() == null) {
                scheduledCheckingService
                        .createNewScheduledCheckingAsStarted(String.valueOf(ScheduledCheckingTitleEnum.CHECK_CALCULATION_ALL_LOANS),
                                count.intValue());
                String message = "There might be a potential problem. Not all loans were full calculated. Number of not calculated loans: " +
                        count + ". When the system notices that this problem no longer exists, you will receive an additional notification about this.";
                sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
                        "https://tienoi.com.vn/wp-content/uploads/2022/10/57936397-warning-sign-yellow-warning-sign-warning-sign-icon-warning-sign-on-white-warning-sign-vector-warning.jpg",
                        "There are loans, which were not full calculated.",
                        message, "Attention! Loans without full calculation");
            }
        }
    }

//    @Async("taskScheduler")
//    @Scheduled(cron = "0 */3 * * * *", zone = TIME_ZONE)
//    public void checkSystemPotentialProblemsLastCreatedApplicationTime() {
//        ScheduledChecking scheduledChecking =
//                scheduledCheckingService
//                        .getUniqueNotFinishedScheduledChecking(String.valueOf(ScheduledCheckingTitleEnum.CHECK_TIME_LAST_APPLICATION));
//        CheckerEntity checkerEntity = checkerEntityService.getLastCreatedApplication();
//
//        if (checkerEntity != null && checkerEntity.getId() != null) {
//            int currentHour = LocalDateTime.now(ZoneId.of(TIME_ZONE)).getHour();
//
//            int countMinutesForCheck = 5;
//
//            if (currentHour >= HOUR_CHECKING_DAY_START && currentHour <= HOUR_CHECKING_DAY_END) {
//                countMinutesForCheck = 6;
//            } else if (currentHour >= HOUR_CHECKING_EVENING_START && currentHour <= HOUR_CHECKING_EVENING_END) {
//                countMinutesForCheck = 10;
//            } else if (currentHour >= HOUR_CHECKING_NIGHT_START && currentHour <= HOUR_CHECKING_NIGHT_END) {
//                countMinutesForCheck = 25;
//            }
//
//            if (checkerEntity.getDateTime().isAfter(LocalDateTime.now(ZoneId.of(TIME_ZONE)).minusMinutes(countMinutesForCheck))) {
//                if (scheduledChecking != null && scheduledChecking.getId() != null) {
//                    scheduledChecking
//                            .setStatus(String.valueOf(ScheduledCheckingStatusEnum.FINISHED));
//                    scheduledChecking.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                    scheduledCheckingService.update(scheduledChecking);
//
//                    String message = "Applications are submitted with the usual time interval. The last recorded problem was at " +
//                            reformatDateTimeToString(scheduledChecking.getStartedAt()) + " and was finished at " +
//                            reformatDateTimeToString(scheduledChecking.getFinishedAt()) + ".";
//                    sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                            "https://tienoi.com.vn/wp-content/uploads/2022/10/shutterstock_142333726b.jpg",
//                            "Applications are submitted as usual.",
//                            message, "Applications are submitted as usual");
//                }
//            } else {
//                if (scheduledChecking == null || scheduledChecking.getId() == null) {
//                    scheduledCheckingService
//                            .createNewScheduledCheckingAsStarted(String.valueOf(ScheduledCheckingTitleEnum.CHECK_TIME_LAST_APPLICATION));
//                    String message = "There might be a potential problem. We have no applications longer than usual. Last application was initialed at " +
//                            reformatDateTimeToString(checkerEntity.getDateTime()) + ". When the system notices that this problem no longer exists, you will receive an additional notification about this.";
//                    sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                            "https://tienoi.com.vn/wp-content/uploads/2022/10/57936397-warning-sign-yellow-warning-sign-warning-sign-icon-warning-sign-on-white-warning-sign-vector-warning.jpg",
//                            "Longer than usual no applications.",
//                            message, "Attention! Longer than usual no applications");
//                }
//            }
//        }
//    }
//
//
//    @Async("taskScheduler")
//    @Scheduled(cron = "0 */6 * * * *", zone = TIME_ZONE)
//    public void checkSystemPotentialProblemsLastIssuedLoanTime() {
//        ScheduledChecking scheduledChecking =
//                scheduledCheckingService
//                        .getUniqueNotFinishedScheduledChecking(String.valueOf(ScheduledCheckingTitleEnum.CHECK_TIME_LAST_LOAN));
//        CheckerEntity checkerEntity = checkerEntityService.getLastIssuedLoan();
//
//        if (checkerEntity != null && checkerEntity.getId() != null) {
//            int currentHour = LocalDateTime.now(ZoneId.of(TIME_ZONE)).getHour();
//
//            int countMinutesForCheck = 10;
//
//            if (currentHour >= HOUR_CHECKING_DAY_START && currentHour <= HOUR_CHECKING_DAY_END) {
//                countMinutesForCheck = 20;
//            } else if (currentHour >= HOUR_CHECKING_EVENING_START && currentHour <= HOUR_CHECKING_EVENING_END) {
//                countMinutesForCheck = 40;
//            } else if (currentHour >= HOUR_CHECKING_NIGHT_START && currentHour <= HOUR_CHECKING_NIGHT_END) {
//                countMinutesForCheck = 70;
//            }
//
//            if (checkerEntity.getDateTime().isAfter(LocalDateTime.now(ZoneId.of(TIME_ZONE)).minusMinutes(countMinutesForCheck))) {
//                if (scheduledChecking != null && scheduledChecking.getId() != null) {
//                    scheduledChecking
//                            .setStatus(String.valueOf(ScheduledCheckingStatusEnum.FINISHED));
//                    scheduledChecking.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                    scheduledCheckingService.update(scheduledChecking);
//
//                    String message = "Loans are issued with the usual time interval. The last recorded problem was at " +
//                            reformatDateTimeToString(scheduledChecking.getStartedAt()) + " and was finished at " +
//                            reformatDateTimeToString(scheduledChecking.getFinishedAt()) + ".";
//                    sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                            "https://tienoi.com.vn/wp-content/uploads/2022/10/shutterstock_142333726b.jpg",
//                            "Loans are issued as usual.",
//                            message, "Loans are issued as usual");
//                }
//            } else {
//                if (scheduledChecking == null || scheduledChecking.getId() == null) {
//                    scheduledCheckingService
//                            .createNewScheduledCheckingAsStarted(String.valueOf(ScheduledCheckingTitleEnum.CHECK_TIME_LAST_LOAN));
//                    String message = "There might be a potential problem. We have no loans longer than usual. Last loan was issued at " +
//                            reformatDateTimeToString(checkerEntity.getDateTime()) + ". When the system notices that this problem no longer exists, you will receive an additional notification about this.";
//                    sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                            "https://tienoi.com.vn/wp-content/uploads/2022/10/57936397-warning-sign-yellow-warning-sign-warning-sign-icon-warning-sign-on-white-warning-sign-vector-warning.jpg",
//                            "Longer than usual no loans.",
//                            message, "Attention! Longer than usual no loans");
//                }
//            }
//        }
//    }
//
//
//    @Async("taskScheduler")
//    @Scheduled(cron = "0 */4 * * * *", zone = TIME_ZONE)
//    public void checkSystemPotentialProblemsLastRegisteredClientTime() {
//        ScheduledChecking scheduledChecking =
//                scheduledCheckingService
//                        .getUniqueNotFinishedScheduledChecking(String.valueOf(ScheduledCheckingTitleEnum.CHECK_TIME_LAST_REGISTERED_CLIENT));
//        CheckerEntity checkerEntity = checkerEntityService.getLastRegisteredClient();
//
//        if (checkerEntity != null && checkerEntity.getId() != null) {
//            int currentHour = LocalDateTime.now(ZoneId.of(TIME_ZONE)).getHour();
//
//            int countMinutesForCheck = 5;
//
//            if (currentHour >= HOUR_CHECKING_DAY_START && currentHour <= HOUR_CHECKING_DAY_END) {
//                countMinutesForCheck = 6;
//            } else if (currentHour >= HOUR_CHECKING_EVENING_START && currentHour <= HOUR_CHECKING_EVENING_END) {
//                countMinutesForCheck = 10;
//            } else if (currentHour >= HOUR_CHECKING_NIGHT_START && currentHour <= HOUR_CHECKING_NIGHT_END) {
//                countMinutesForCheck = 30;
//            }
//
//            if (checkerEntity.getDateTime().isAfter(LocalDateTime.now(ZoneId.of(TIME_ZONE)).minusMinutes(countMinutesForCheck))) {
//                if (scheduledChecking != null && scheduledChecking.getId() != null) {
//                    scheduledChecking
//                            .setStatus(String.valueOf(ScheduledCheckingStatusEnum.FINISHED));
//                    scheduledChecking.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                    scheduledCheckingService.update(scheduledChecking);
//
//                    String message = "Clients are registered with the usual time interval. The last recorded problem was at " +
//                            reformatDateTimeToString(scheduledChecking.getStartedAt()) + " and was finished at " +
//                            reformatDateTimeToString(scheduledChecking.getFinishedAt()) + ".";
//                    sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                            "https://tienoi.com.vn/wp-content/uploads/2022/10/shutterstock_142333726b.jpg",
//                            "Clients are registered as usual.",
//                            message, "Clients are registered as usual");
//                }
//            } else {
//                if (scheduledChecking == null || scheduledChecking.getId() == null) {
//                    scheduledCheckingService
//                            .createNewScheduledCheckingAsStarted(String.valueOf(ScheduledCheckingTitleEnum.CHECK_TIME_LAST_REGISTERED_CLIENT));
//                    String message = "There might be a potential problem. We have no new registrations of clients longer than usual. Last registration was made at " +
//                            reformatDateTimeToString(checkerEntity.getDateTime()) + ". When the system notices that this problem no longer exists, you will receive an additional notification about this.";
//                    sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                            "https://tienoi.com.vn/wp-content/uploads/2022/10/57936397-warning-sign-yellow-warning-sign-warning-sign-icon-warning-sign-on-white-warning-sign-vector-warning.jpg",
//                            "Longer than usual no new registrations of clients.",
//                            message, "Attention! Longer than usual no new registrations of clients");
//                }
//            }
//        }
//    }
//
//
//    @Async("taskScheduler")
//    @Scheduled(cron = "0 */12 * * * *", zone = TIME_ZONE)
//    public void checkSystemPotentialProblemsLastPaymentTime() {
//        ScheduledChecking scheduledChecking =
//                scheduledCheckingService
//                        .getUniqueNotFinishedScheduledChecking(String.valueOf(ScheduledCheckingTitleEnum.CHECK_TIME_LAST_PAYMENT));
//        CheckerEntity checkerEntity = checkerEntityService.getLastPayment();
//
//        if (checkerEntity != null && checkerEntity.getId() == null) {
//            int currentHour = LocalDateTime.now(ZoneId.of(TIME_ZONE)).getHour();
//
//            int countMinutesForCheck = 20;
//
//            if (currentHour >= HOUR_CHECKING_DAY_START && currentHour <= HOUR_CHECKING_DAY_END) {
//                countMinutesForCheck = 30;
//            } else if (currentHour >= HOUR_CHECKING_EVENING_START && currentHour <= HOUR_CHECKING_EVENING_END) {
//                countMinutesForCheck = 60;
//            } else if (currentHour >= HOUR_CHECKING_NIGHT_START && currentHour <= HOUR_CHECKING_NIGHT_END) {
//                countMinutesForCheck = 180;
//            }
//
//            if (checkerEntity.getDateTime().isAfter(LocalDateTime.now(ZoneId.of(TIME_ZONE)).minusMinutes(countMinutesForCheck))) {
//                if (scheduledChecking != null && scheduledChecking.getId() != null) {
//                    scheduledChecking
//                            .setStatus(String.valueOf(ScheduledCheckingStatusEnum.FINISHED));
//                    scheduledChecking.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                    scheduledCheckingService.update(scheduledChecking);
//
//                    String message = "Payments are received with the usual time interval. The last recorded problem was at " +
//                            reformatDateTimeToString(scheduledChecking.getStartedAt()) + " and was finished at " +
//                            reformatDateTimeToString(scheduledChecking.getFinishedAt()) + ".";
//                    sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                            "https://tienoi.com.vn/wp-content/uploads/2022/10/shutterstock_142333726b.jpg",
//                            "Payments are received as usual.",
//                            message, "Payments are received as usual");
//                }
//            } else {
//                if (scheduledChecking == null || scheduledChecking.getId() == null) {
//                    scheduledCheckingService
//                            .createNewScheduledCheckingAsStarted(String.valueOf(ScheduledCheckingTitleEnum.CHECK_TIME_LAST_PAYMENT));
//                    String message = "There might be a potential problem. We have no payments longer than usual. Last payments was received at " +
//                            reformatDateTimeToString(checkerEntity.getDateTime()) + ". When the system notices that this problem no longer exists, you will receive an additional notification about this.";
//                    sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                            "https://tienoi.com.vn/wp-content/uploads/2022/10/57936397-warning-sign-yellow-warning-sign-warning-sign-icon-warning-sign-on-white-warning-sign-vector-warning.jpg",
//                            "Longer than usual no payments.",
//                            message, "Attention! Longer than usual no payments");
//                }
//            }
//        }
//    }
//
//    @Async("taskScheduler")
//    @Scheduled(cron = "0 18 * * * *", zone = TIME_ZONE)
//    public void checkSystemPotentialProblemsAppsWithPayoutError() {
//        ScheduledChecking scheduledChecking =
//                scheduledCheckingService
//                        .getUniqueNotFinishedScheduledChecking(String.valueOf(ScheduledCheckingTitleEnum.CHECK_APPS_WITH_PAYOUT_ERROR));
//        List<CheckerEntity> applicationsWithPayoutError = checkerEntityService.getApplicationsWithPayoutError();
//
//        if (applicationsWithPayoutError.isEmpty()) {
//            if (scheduledChecking != null && scheduledChecking.getId() != null) {
//                scheduledChecking
//                        .setStatus(String.valueOf(ScheduledCheckingStatusEnum.FINISHED));
//                scheduledChecking.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
//                scheduledCheckingService.update(scheduledChecking);
//                String message = "There are no more applications in the system for now with payout errors. The last recorded such problem was at " +
//                        reformatDateTimeToString(scheduledChecking.getStartedAt()) + " and was finished at " +
//                        reformatDateTimeToString(scheduledChecking.getFinishedAt()) + ".";
//                sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                        "https://tienoi.com.vn/wp-content/uploads/2022/10/shutterstock_142333726b.jpg",
//                        "Applications are issued without payout errors",
//                        message, "NO MORE applications with payout errors");
//            }
//        } else {
//            if (scheduledChecking == null || scheduledChecking.getId() == null) {
//                scheduledCheckingService
//                        .createNewScheduledCheckingAsStarted(String.valueOf(ScheduledCheckingTitleEnum.CHECK_APPS_WITH_PAYOUT_ERROR),
//                                applicationsWithPayoutError.size());
//                String message = "Some of the applications was now issued and have payout errors. " +
//                        "The number of such applications: " +
//                        applicationsWithPayoutError.size() +
//                        ". IDs of these applications: ";
//                int i = 0;
//                for (CheckerEntity entity : applicationsWithPayoutError) {
//                    message += entity.getId();
//                    if (i++ != applicationsWithPayoutError.size() - 1) {
//                        message += ", ";
//                    } else {
//                        message += ". ";
//                    }
//                }
//                message += "When the system notices that this problem no longer exists, you will receive an additional notification about this.";
//                sendEmailWarningToManagers(EMAIL_MANAGERS_FOR_WARNINGS,
//                        "https://tienoi.com.vn/wp-content/uploads/2022/10/57936397-warning-sign-yellow-warning-sign-warning-sign-icon-warning-sign-on-white-warning-sign-vector-warning.jpg",
//                        "Applications are in \"Payout Error\" status for a long time.",
//                        message, "Attention! Applications with Payout errors");
//            }
//        }
//    }

//    @Async("taskScheduler")
//    @Scheduled(cron = "0 */5 * * * *", zone = TIME_ZONE)
//    public void checkApplicationsQueueForVerification() {
//        Long count = checkerEntityService.getCountAppsOnStatusSentToUnderwriting();
//        if (count > 0) {
//            applicationsQueueCheckingService.create(count.intValue());
//        }
//    }


    @Async("taskScheduler")
    @Scheduled(cron = "0 43 1 * * *", zone = TIME_ZONE)
    public void updatePromisedPaymentsAsExpired() {
        debtCollectionService.updatePromisedPaymentsAsExpired();
    }


    // Main tasks for investors and board

    @Async("taskScheduler")
    @Scheduled(cron = "0 2 0,12,15 * * *", zone = TIME_ZONE)
    public void apiSchedulerRequestCurrencyRateUsdToVnd() {
        if (!currencyConverterApiService.apiSchedulerRequestCurrencyRateUsdToVnd()) {
            schedTaskService.create(String.valueOf(ScheduledTaskName.CURRENCY_RATE_UPDATING),
                    String.valueOf(ScheduledTaskStatus.UNSUCCESSFUL),
                    "error, could not update via https://www.currencyconverterapi.com/");
        } else {
            schedTaskService.create(String.valueOf(ScheduledTaskName.CURRENCY_RATE_UPDATING),
                    String.valueOf(ScheduledTaskStatus.SUCCESSFUL),
                    "updated via https://www.currencyconverterapi.com/");
        }
    }

    @Async("taskScheduler")
    @Scheduled(cron = "0 3 0 * * *", zone = TIME_ZONE)
    public void schedulerCreatingMainDailyReportAndSendingViaEmail() {
        Workbook workbook = new XSSFWorkbook();
        Map<String, Object> model = new HashMap<>();
        model.put("modelObject",
                reportBuilderService.getMainDailyReport(LocalDate.now(ZoneId.of(TIME_ZONE)).minusMonths(3)
                        .withDayOfMonth(1), LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(1)));
        ExcelBuildReport.buildExcelMainDailyReport(model, workbook);
        List<FileSystemResource> fileList = new ArrayList<>();
        File f = new File("MainDailyReport.xlsx");

        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileSystemResource file = new FileSystemResource(f);
        fileList.add(file);

        int countSuccessSentEmails = 0;

        List<String> emailsForGettingReport = new ArrayList<>();
        emailsForGettingReport.add("volodymyr.korotkov@tienoi.com.vn");

        for (int a = 0; a < emailsForGettingReport.size(); a++) {
            Map<String, Object> emailModel = new HashMap<>();
            emailModel.put("mainUrl", MAIN_DOMAIN_URL);
            emailModel.put(SUBJECT, "Основной ежедневный отчет. Tiền Ơi");
            emailModel.put("attachments", fileList);
            emailModel.put(TO, emailsForGettingReport.get(a));
            boolean result = emailService.sendEmail("mailForMainDailyReport.vm", emailModel);

            if (result) {
                countSuccessSentEmails ++;
            }
        }

        if (countSuccessSentEmails == emailsForGettingReport.size()) {
            schedTaskService.create(String.valueOf(ScheduledTaskName.MAIN_DAILY_REPORT_EMAILING),
                    String.valueOf(ScheduledTaskStatus.SUCCESSFUL), "everything ok. Emails were sent to all persons. System sent " +
                            countSuccessSentEmails + " emails.");
        } else {
            schedTaskService.create(String.valueOf(ScheduledTaskName.MAIN_DAILY_REPORT_EMAILING),
                    String.valueOf(ScheduledTaskStatus.UNSUCCESSFUL), "We had some error. Not all Emails were sent. System sent " +
                            countSuccessSentEmails + " emails. And did not send " + (emailsForGettingReport.size() - countSuccessSentEmails) + " emails.");
        }
    }








    // private methods

    private void sendEmailToRegisteredClientsWithoutConfirmedEmail(int[] daysAfterRegistration) {
        List<LocalDate> dateListOfRegistration = new ArrayList<>();
        for (int i : daysAfterRegistration) {
            LocalDate date = LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(i);
            dateListOfRegistration.add(date);
        }

        List<ClientEmailConfirmedCRM> clientEmailConfirmedCRMList =
                clientCRMService.getClientEmailConfirmedCRMList(dateListOfRegistration);

        if (!clientEmailConfirmedCRMList.isEmpty()) {
            Long idSendingEmail =
                    sendingEmailService
                            .createAutoAsStartedAndGetId(String.valueOf(EmailSendingTitleEnum.EMAILS_NOT_CONFIRMED_EMAIL_REGISTERED_CLIENT),
                                    clientEmailConfirmedCRMList.size());

            int countSuccessSentEmails = 0;

            for (ClientEmailConfirmedCRM clientEmailConfirmedCRM : clientEmailConfirmedCRMList) {
                String verificationUrl =
                        createAndGetVerificationUrlForClient(clientEmailConfirmedCRM.getEmail(),
                                clientEmailVerificationService.createNewAndGetCode(clientEmailConfirmedCRM.getClientId(),
                                        clientEmailConfirmedCRM.getEmail()), String.valueOf(clientEmailConfirmedCRM.getClientId()));

                Map<String, Object> emailModel = new HashMap<>();
                emailModel.put("verificationUrl", verificationUrl);
                emailModel.put(SUBJECT, "Tiền Ơi - Xác nhận email");
                emailModel.put(TO, clientEmailConfirmedCRM.getEmail());
                emailModel.put("clientName", clientEmailConfirmedCRM.getName());

                boolean result = emailService.sendEmail("verifyClientEmail.vm", emailModel);

                if (result) {
                    countSuccessSentEmails++;
                }
            }

            sendingEmailService.updateAsCompleted(idSendingEmail, countSuccessSentEmails);
        }
    }


    private void sendEmailsToPassiveClientsWithoutLoans(int [] daysAfterFinishedLoan, String emailTitle,
                                                        String mainUrlEmail, String subjectEmail,
                                                        String templateEmail) {

        List<LocalDate> dateLastFinishedLoanList = new ArrayList<>();
        for (int i : daysAfterFinishedLoan) {
            LocalDate date = LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(i);
            dateLastFinishedLoanList.add(date);
        }

        List<ClientNameAndEmail> clientNameAndEmailList =
                clientCRMService.getPassiveClientsWithoutLoans(dateLastFinishedLoanList);

        if (!clientNameAndEmailList.isEmpty()) {
            Long idSendingEmail =
                    sendingEmailService
                            .createAutoAsStartedAndGetId(emailTitle,
                                    clientNameAndEmailList.size());

            int countSuccessSentEmails = 0;

            for (ClientNameAndEmail clientNameAndEmail : clientNameAndEmailList) {
                Map<String, Object> emailModel = new HashMap<>();
                emailModel.put("mainUrl", mainUrlEmail);
                emailModel.put(SUBJECT, subjectEmail);
                emailModel.put(TO, clientNameAndEmail.getEmail());
                emailModel.put("clientName", clientNameAndEmail.getName());
                boolean result = emailService.sendEmail(templateEmail, emailModel);

                if (result) {
                    countSuccessSentEmails++;
                }
            }
            sendingEmailService.updateAsCompleted(idSendingEmail, countSuccessSentEmails);
        }
    }


    private void sendEmailsToRegisteredClientsWithoutApplications(int[] daysAfterRegistration, String emailTitle,
                                                                   String mainUrlEmail, String subjectEmail,
                                                                   String templateEmail) {

        List<LocalDate> dateRegistrationOfClientList = new ArrayList<>();
        for (int i : daysAfterRegistration) {
            LocalDate date = LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(i);
            dateRegistrationOfClientList.add(date);
        }

        List<ClientNameAndEmail> clientNameAndEmailList =
                clientCRMService.getRegisteredClientsWithoutApplications(dateRegistrationOfClientList);

        if (!clientNameAndEmailList.isEmpty()) {
            Long idSendingEmail =
                    sendingEmailService
                            .createAutoAsStartedAndGetId(emailTitle,
                                    clientNameAndEmailList.size());

            int countSuccessSentEmails = 0;

            for (ClientNameAndEmail clientNameAndEmail : clientNameAndEmailList) {
                Map<String, Object> emailModel = new HashMap<>();
                emailModel.put("mainUrl", mainUrlEmail);
                emailModel.put(SUBJECT, subjectEmail);
                emailModel.put(TO, clientNameAndEmail.getEmail());
                emailModel.put("clientName", clientNameAndEmail.getName());
                boolean result = emailService.sendEmail(templateEmail, emailModel);

                if (result) {
                    countSuccessSentEmails++;
                }
            }

            sendingEmailService.updateAsCompleted(idSendingEmail, countSuccessSentEmails);
        }

    }


    private void sendEmailsToClientsWithLastAppAsSomeStatus(int[] daysAfterLastApplication,
                                                            String appStatus,
                                                            String emailTitle,
                                                            String mainUrlEmail,
                                                            String subjectEmail,
                                                            String templateEmail) {

        List<LocalDate> dateLastAppChangedStatusList = new ArrayList<>();
        for (int i : daysAfterLastApplication) {
            LocalDate date = LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(i);
            dateLastAppChangedStatusList.add(date);
        }

        List<ClientNameAndEmail> clientNameAndEmailList =
                clientCRMService.getClientsWithSomeLastApplications(dateLastAppChangedStatusList,
                        appStatus);

        if (!clientNameAndEmailList.isEmpty()) {
            Long idSendingEmail =
                    sendingEmailService
                            .createAutoAsStartedAndGetId(emailTitle,
                                    clientNameAndEmailList.size());

            int countSuccessSentEmails = 0;

            for (ClientNameAndEmail clientNameAndEmail : clientNameAndEmailList) {
                Map<String, Object> emailModel = new HashMap<>();
                emailModel.put("mainUrl", mainUrlEmail);
                emailModel.put(SUBJECT, subjectEmail);
                emailModel.put(TO, clientNameAndEmail.getEmail());
                emailModel.put("clientName", clientNameAndEmail.getName());
                boolean result = emailService.sendEmail(templateEmail, emailModel);

                if (result) {
                    countSuccessSentEmails++;
                }
            }

            sendingEmailService.updateAsCompleted(idSendingEmail, countSuccessSentEmails);
        }

    }

    private void sendEmailsToClientsForRemindPayments(int[] daysBeforePayment,
                                                      String emailTitle,
                                                      String mainUrlEmail,
                                                      String subjectEmail,
                                                      String templateEmail) {
        List<LocalDate> maturityDateList = new ArrayList<>();
        for (int i : daysBeforePayment) {
            LocalDate date = LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(i);
            maturityDateList.add(date);
        }
        List<ClientNameAndEmailRemindPayment> clientNameAndEmailRemindPaymentList =
                clientCRMService.getClientsForRemindPayment(maturityDateList);
        if (!clientNameAndEmailRemindPaymentList.isEmpty()) {
            Long idSendingEmail =
                    sendingEmailService
                            .createAutoAsStartedAndGetId(emailTitle,
                                    clientNameAndEmailRemindPaymentList.size());
            int countSuccessSentEmails = 0;
            for (ClientNameAndEmailRemindPayment clientNameAndEmail : clientNameAndEmailRemindPaymentList) {
                Map<String, Object> emailModel = new HashMap<>();
                emailModel.put("mainUrl", mainUrlEmail);
                emailModel.put(SUBJECT, subjectEmail);
                emailModel.put(TO, clientNameAndEmail.getEmail());
                emailModel.put("clientName", clientNameAndEmail.getName());
                emailModel.put("contractNumber", clientNameAndEmail.getContactNumber());
                emailModel.put("maturityDate", reformatDateToString(clientNameAndEmail.getMaturityDate()));
                emailModel.put("virtualAccount", clientNameAndEmail.getVirtualAccount());
                emailModel.put("documentNumber", clientNameAndEmail.getDocumentNumber());
                emailModel.put("debtAmount", reformatAmountToString(clientNameAndEmail.getDebtAmount()));
                emailModel.put("contactSignedAt", reformatDateTimeToString(clientNameAndEmail.getContactSignedAt()));
                boolean result = emailService.sendEmail(templateEmail, emailModel);

                if (result) {
                    countSuccessSentEmails++;
                }
            }

            sendingEmailService.updateAsCompleted(idSendingEmail, countSuccessSentEmails);
        }
    }


    private void sendEmailsToClientsForRemindPaymentsGracePeriod(String emailTitle,
                                                                 String mainUrlEmail,
                                                                 String subjectEmail,
                                                                 String templateEmail) {

        List<ClientNameAndEmailGracePeriod> clientNameAndEmailRemindPaymentList =
                clientCRMService.getClientsForRemindPaymentGracePeriod();
        if (!clientNameAndEmailRemindPaymentList.isEmpty()) {
            Long idSendingEmail =
                    sendingEmailService
                            .createAutoAsStartedAndGetId(emailTitle,
                                    clientNameAndEmailRemindPaymentList.size());
            int countSuccessSentEmails = 0;
            for (ClientNameAndEmailGracePeriod clientNameAndEmail : clientNameAndEmailRemindPaymentList) {
                Map<String, Object> emailModel = new HashMap<>();
                emailModel.put("mainUrl", mainUrlEmail);
                emailModel.put(SUBJECT, subjectEmail);
                emailModel.put(TO, clientNameAndEmail.getEmail());
                emailModel.put("clientName", clientNameAndEmail.getName());
                emailModel.put("contractNumber", clientNameAndEmail.getContactNumber());
                emailModel.put("virtualAccount", clientNameAndEmail.getVirtualAccount());
                emailModel.put("documentNumber", clientNameAndEmail.getDocumentNumber());
                emailModel.put("debtAmount", reformatAmountToString(clientNameAndEmail.getDebtAmount()));
                emailModel.put("contactSignedAt", reformatDateTimeToString(clientNameAndEmail.getContactSignedAt()));
                boolean result = emailService.sendEmail(templateEmail, emailModel);

                if (result) {
                    countSuccessSentEmails++;
                }
            }

            sendingEmailService.updateAsCompleted(idSendingEmail, countSuccessSentEmails);
        }
    }


    private void sendEmailsToClientsForRemindExpiredLoans(int[] overdueDays,
                                                          String emailTitle,
                                                          String mainUrlEmail,
                                                          String subjectEmail,
                                                          String templateEmail) {

        List<Integer> daysOverdueList = new ArrayList<>();
        for (int dayOverdue : overdueDays) {
            daysOverdueList.add(dayOverdue);
        }

        List<ClientNameAndEmailExpiredLoan> clientNameAndEmailList =
                clientCRMService.getClientsWithExpiredLoansMoreInfo(daysOverdueList);
        if (!clientNameAndEmailList.isEmpty()) {
            Long idSendingEmail =
                    sendingEmailService
                            .createAutoAsStartedAndGetId(emailTitle,
                                    clientNameAndEmailList.size());

            int countSuccessSentEmails = 0;
            for (ClientNameAndEmailExpiredLoan clientNameAndEmail : clientNameAndEmailList) {
                Map<String, Object> emailModel = new HashMap<>();
                emailModel.put("mainUrl", mainUrlEmail);
                emailModel.put(SUBJECT, subjectEmail);
                emailModel.put(TO, clientNameAndEmail.getEmail());
                emailModel.put("clientName", clientNameAndEmail.getName());
                emailModel.put("contractNumber", clientNameAndEmail.getContactNumber());
                emailModel.put("daysOverdue", clientNameAndEmail.getDaysOverdue());
                emailModel.put("virtualAccount", clientNameAndEmail.getVirtualAccount());
                emailModel.put("documentNumber", clientNameAndEmail.getDocumentNumber());
                emailModel.put("debtAmount", reformatAmountToString(clientNameAndEmail.getDebtAmount()));
                emailModel.put("contactSignedAt", reformatDateTimeToString(clientNameAndEmail.getContractSignedAt()));
                boolean result = emailService.sendEmail(templateEmail, emailModel);

                if (result) {
                    countSuccessSentEmails++;
                }
            }

            sendingEmailService.updateAsCompleted(idSendingEmail, countSuccessSentEmails);
        }
    }


    private void sendEmailsToClientsWithBirthDay(LocalDate birthDate, String emailTitle,
                                                 String mainUrlEmail,
                                                 String templateEmail) {
        List<ClientNameAndEmail> clientNameAndEmailList =
                clientCRMService.getClientsWithBirthDate(birthDate);

        if (!clientNameAndEmailList.isEmpty()) {
            Long idSendingEmail =
                    sendingEmailService
                            .createAutoAsStartedAndGetId(emailTitle,
                                    clientNameAndEmailList.size());

            int countSuccessSentEmails = 0;

            for (ClientNameAndEmail clientNameAndEmail : clientNameAndEmailList) {
                Map<String, Object> emailModel = new HashMap<>();
                emailModel.put("mainUrl", mainUrlEmail);
                emailModel.put(SUBJECT, "Chúc mừng sinh nhật " + clientNameAndEmail.getName() + ", Chúng tôi có một món quà cho bạn!");
                emailModel.put(TO, clientNameAndEmail.getEmail());
                emailModel.put("clientName", clientNameAndEmail.getName());
                boolean result = emailService.sendEmail(templateEmail, emailModel);

                if (result) {
                    countSuccessSentEmails++;
                }
            }

            sendingEmailService.updateAsCompleted(idSendingEmail, countSuccessSentEmails);
        }

    }

    private void sendEmailWarningToManagers(String[] emailsManagers,
                                            String emailBannerUrl,
                                            String titleScheduleChecking,
                                            String message,
                                            String emailSubject) {
        Map<String, Object> emailModel = new HashMap<>();
        emailModel.put(SUBJECT, emailSubject);
        emailModel.put("bodyEmailTitle", titleScheduleChecking);
        emailModel.put("bodyEmailText", message);
        emailModel.put("bannerLink", emailBannerUrl);
        emailModel.put("buttonText", "Detailed list of potential bugs");
        emailModel.put("buttonLink", "https://report-system.tienoi.com.vn/admin/scheduled-checking");
        for (String email : emailsManagers) {
            emailModel.put(TO, email);
            emailService.sendEmail("warningEmailToManagers.vm", emailModel);
        }
    }


    private String reformatDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String reformatDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    private String reformatAmountToString(Double amount) {
        return String.format("%,d", amount.intValue());
    }



}
