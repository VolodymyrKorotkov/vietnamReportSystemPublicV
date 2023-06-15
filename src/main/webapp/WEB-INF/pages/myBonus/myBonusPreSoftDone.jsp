<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 29.07.2022
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<mytags:header-template>
    <jsp:attribute name="contentPage">
        <div class="row wrapper border-bottom white-bg page-heading" style="margin-bottom: 30px;">
            <div class="col-lg-7">
                <h2><spring:message code="myBonusPage.title"/></h2>
                <h6><spring:message code="myBonusPage.title"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>

        </div>

                <c:if test="${not empty messageGood}">
                    <div class="alert alert-success alert-dismissable">
                        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                        <spring:message code="${messageGood}"/>
                    </div>
                </c:if>
        <%--        <c:if test="${not empty messageBad}">--%>
        <%--            <div class="alert alert-danger alert-dismissable">--%>
        <%--                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>--%>
        <%--                <spring:message code="${messageBad}"/>--%>
        <%--            </div>--%>
        <%--        </c:if>--%>

        <div class="tabs-container">
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#bonus"><spring:message code="myBonusPage.title"/></a></li>
                <li><a data-toggle="tab" href="#settings" id="settings_btn"><spring:message code="myBonusPage.settingsTitle"/></a></li>
            </ul>
            <div class="tab-content">
                <div id="bonus" class="tab-pane active">
                    <div class="panel-body">
                        <div class="clearfix"></div>
                        <div class="col-lg-12" style="margin-bottom: 20px;">
                            <div class="col-sm-6">
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="reportsPage.commonTextTitleFrom"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <fmt:parseDate pattern="yyyy-MM-dd" var="dateFrom" value="${individualBonusForPreSoft.dateFrom}" type="both"/>
                                        <fmt:formatDate value="${dateFrom}" pattern="dd.MM.yyyy"/>
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.textPositionRating"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="position" value="${individualBonusForPreSoft.positionOfPerson}"/>
                                        <strong><fmt:formatNumber value="${position}" type="number" maxFractionDigits="0"/></strong>
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.textTotalBonusAmountPerson"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="totalBonusP" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountTotal}"/>
                                        <strong><fmt:formatNumber value="${totalBonusP}" type="currency" maxFractionDigits="0" currencySymbol="₫"/></strong>
                                    </div>
                                </div>



                            </div>

                            <div class="col-sm-6">
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="reportsPage.commonTextTitleTo"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <fmt:parseDate pattern="yyyy-MM-dd" var="dateTo" value="${individualBonusForPreSoft.dateTo}" type="both"/>
                                        <fmt:formatDate value="${dateTo}" pattern="dd.MM.yyyy"/>
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.textTotalCountEmployees"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="totalCount" value="${individualBonusForPreSoft.countEmployees}"/>
                                        <fmt:formatNumber value="${totalCount}" type="number" maxFractionDigits="0"/>
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.textTotalBonusAmountBestInDepartment"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="totalBonusB" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountTotal}"/>
                                        <fmt:formatNumber value="${totalBonusB}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="text-box" style="margin-bottom: 10px;">
                            <span class="text-info"><strong><spring:message code="myBonusPage.textTotalResultsPerson"/>:</strong></span>
                        </div>
                        <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>

                        <div class="tabs-container">
                            <div class="tabs">
                                <div class="panel-group" id="accordion1" role="tablist" aria-multiselectable="true">
                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading1">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse1" aria-expanded="true" aria-controls="collapse1" class="collapsed">
                                                    <span class="fa fa-line-chart"></span> <spring:message code="myBonusPage.textInfoAboutCalls"/>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse1" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading1">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">
                                                            <spring:message code="common.titleName"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleCount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleInboundCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="inbound1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countInboundCalls}"/>
                                                            <fmt:formatNumber value="${inbound1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="inboundP1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countInboundCalls / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${inboundP1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleOutboundManualCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="manual1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countManualCalls}"/>
                                                            <fmt:formatNumber value="${manual1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="manualP1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countManualCalls / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${manualP1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleOutboundAutoCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="auto1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countOutboundCalls}"/>
                                                            <fmt:formatNumber value="${auto1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="autoP1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countOutboundCalls / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${autoP1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleCallsWithMinDur"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="minDur1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countCallsWithMinTalkSec}"/>
                                                            <fmt:formatNumber value="${minDur1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="minDurP1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countCallsWithMinTalkSec / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${minDurP1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleCallsWithMinDurAndResSt"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="minDurResSt1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countCallsWithMinTalkAndNeededStatuses}"/>
                                                            <fmt:formatNumber value="${minDurResSt1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="minDurResStP1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countCallsWithMinTalkAndNeededStatuses / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${minDurResStP1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <strong><spring:message code="myBonusPage.titleTotalCalls"/></strong>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="totalCalls1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalCountCalls}"/>
                                                            <strong><fmt:formatNumber value="${totalCalls1}" type="number" maxFractionDigits="0"/></strong>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titlePaymentsAfterResCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="pAfterResC1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countResultPayment}"/>
                                                            <fmt:formatNumber value="${pAfterResC1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="pAfterResCP1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countResultPayment / individualBonusForPreSoft.preSoftBackUserResultOfPerson.countCallsWithMinTalkAndNeededStatuses}"/>
                                                            <fmt:formatNumber value="${pAfterResCP1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titlePtpTotalCalls"/>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="totalPtp1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countCallsInListRemindingPromisedPayments}"/>
                                                            <fmt:formatNumber value="${totalPtp1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titlePtpResultCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="serviceResC1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countCallsInListRemindingPromisedPaymentsWithMinTalkSecAndResultStatus}"/>
                                                            <fmt:formatNumber value="${serviceResC1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="serviceResCP1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.countCallsInListRemindingPromisedPaymentsWithMinTalkSecAndResultStatus / individualBonusForPreSoft.preSoftBackUserResultOfPerson.countCallsInListRemindingPromisedPayments}"/>
                                                            <fmt:formatNumber value="${serviceResCP1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading2">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse2" aria-expanded="true" aria-controls="collapse2" class="collapsed">
                                                    <span class="fa fa-bar-chart"></span> <spring:message code="myBonusPage.titlePaymentsWithAssigned"/>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse2" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading2">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleAmount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom1To15OverdueDaysWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym115Dpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd1P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom1To15OverdueDaysWithAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym115Dpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom16To29OverdueDaysWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd1P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom16To29OverdueDaysWithAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom30To60OverdueDaysWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd1P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom30To60OverdueDaysWithAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom61AndMoreOverdueDaysWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd1P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom61AndMoreOverdueDaysWithAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymTotalDpd1" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymTotalDpd1P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountWithAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading3">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse3" aria-expanded="true" aria-controls="collapse3" class="collapsed">
                                                    <span class="fa fa-bar-chart"></span> <spring:message code="myBonusPage.titlePaymentsWithoutAssigned"/>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse3" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading3">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleAmount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd2" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom1To15OverdueDaysNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym115Dpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd2P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom1To15OverdueDaysNoAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym115Dpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd2" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom16To29OverdueDaysNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd2P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom16To29OverdueDaysNoAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd2" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom30To60OverdueDaysNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd2P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom30To60OverdueDaysNoAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd2" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom61AndMoreOverdueDaysNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd2P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom61AndMoreOverdueDaysNoAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymTotalDpd2" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymTotalDpd2P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmountNoAssigned / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading4">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse4" aria-expanded="true" aria-controls="collapse4" class="collapsed">
                                                    <span class="fa fa-bar-chart"></span> <strong><spring:message code="myBonusPage.titlePaymentsTotal"/></strong>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse4" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading4">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleAmount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd3" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom1To15OverdueDays}"/>
                                                            <fmt:formatNumber value="${paym115Dpd3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd3P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom1To15OverdueDays / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paym115Dpd3P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd3" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom16To29OverdueDays}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd3P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom16To29OverdueDays / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd3P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd3" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom30To60OverdueDays}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd3P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom30To60OverdueDays / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd3P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd3" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom61AndMoreOverdueDays}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd3P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.paymentAmountFrom61AndMoreOverdueDays / individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd3P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="paymTotalDpd3" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading5">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse5" aria-expanded="true" aria-controls="collapse5" class="collapsed">
                                                    <span class="fa fa-dollar"></span> <span class="text-info"><strong><spring:message code="myBonusPage.titleBonusAmountTotal"/></strong></span>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse5" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading5">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleAmount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd4" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountFrom1To15DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paym115Dpd4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd4P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountFrom1To15DaysOverdue / individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paym115Dpd4P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd4" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountFrom16To29DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd4P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountFrom16To29DaysOverdue / individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd4P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd4" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountFrom30To60DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd4P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountFrom30To60DaysOverdue / individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd4P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd4" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountFrom61AndMoreDaysOverdue}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd4P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountFrom61AndMoreDaysOverdue / individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd4P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titlePtpResultCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymServicePtpDpd4" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountForRemindingCalls}"/>
                                                            <fmt:formatNumber value="${paymServicePtpDpd4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymServicePtpDpd4P" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountForRemindingCalls / individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymServicePtpDpd4P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <span class="text-info"><spring:message code="myBonusPage.total"/></span>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="paymTotalDpd4" value="${individualBonusForPreSoft.preSoftBackUserResultOfPerson.bonusAmountTotal}"/>
                                                            <span class="text-info"><fmt:formatNumber value="${paymTotalDpd4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/></span>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>



                        <div class="text-box" style="margin-bottom: 10px;">
                            <span class="text-warning"><strong><spring:message code="myBonusPage.textTotalResultsBestEmployee"/>:</strong></span>
                        </div>
                        <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>

                        <div class="tabs-container">
                            <div class="tabs">
                                <div class="panel-group" id="accordion1_best" role="tablist" aria-multiselectable="true">
                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading1_best">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse1_best" aria-expanded="true" aria-controls="collapse1_best" class="collapsed">
                                                    <span class="fa fa-line-chart"></span> <spring:message code="myBonusPage.textInfoAboutCalls"/>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse1_best" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading1_best">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">
                                                            <spring:message code="common.titleName"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleCount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleInboundCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="inbound1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countInboundCalls}"/>
                                                            <fmt:formatNumber value="${inbound1_best}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="inboundP1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countInboundCalls / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${inboundP1_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleOutboundManualCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="manual1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countManualCalls}"/>
                                                            <fmt:formatNumber value="${manual1_best}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="manualP1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countManualCalls / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${manualP1_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleOutboundAutoCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="auto1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countOutboundCalls}"/>
                                                            <fmt:formatNumber value="${auto1_best}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="autoP1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countOutboundCalls / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${autoP1_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleCallsWithMinDur"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="minDur1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countCallsWithMinTalkSec}"/>
                                                            <fmt:formatNumber value="${minDur1_best}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="minDurP1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countCallsWithMinTalkSec / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${minDurP1_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titleCallsWithMinDurAndResSt"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="minDurResSt1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countCallsWithMinTalkAndNeededStatuses}"/>
                                                            <fmt:formatNumber value="${minDurResSt1_best}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="minDurResStP1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countCallsWithMinTalkAndNeededStatuses / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalCountCalls}"/>
                                                            <fmt:formatNumber value="${minDurResStP1_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <strong><spring:message code="myBonusPage.titleTotalCalls"/></strong>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="totalCalls1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.totalCountCalls}"/>
                                                            <strong><fmt:formatNumber value="${totalCalls1_best}" type="number" maxFractionDigits="0"/></strong>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titlePaymentsAfterResCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="pAfterResC1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countResultPayment}"/>
                                                            <fmt:formatNumber value="${pAfterResC1_best}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="pAfterResCP1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countResultPayment / individualBonusForPreSoft.preSoftBackUserResultTheBest.countCallsWithMinTalkAndNeededStatuses}"/>
                                                            <fmt:formatNumber value="${pAfterResCP1_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titlePtpTotalCalls"/>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="totalPtp1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countCallsInListRemindingPromisedPayments}"/>
                                                            <fmt:formatNumber value="${totalPtp1_best}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titlePtpResultCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="serviceResC1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countCallsInListRemindingPromisedPaymentsWithMinTalkSecAndResultStatus}"/>
                                                            <fmt:formatNumber value="${serviceResC1_best}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="serviceResCP1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.countCallsInListRemindingPromisedPaymentsWithMinTalkSecAndResultStatus / individualBonusForPreSoft.preSoftBackUserResultTheBest.countCallsInListRemindingPromisedPayments}"/>
                                                            <fmt:formatNumber value="${serviceResCP1_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading2_best">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse2_best" aria-expanded="true" aria-controls="collapse2_best" class="collapsed">
                                                    <span class="fa fa-bar-chart"></span> <spring:message code="myBonusPage.titlePaymentsWithAssigned"/>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse2_best" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading2_best">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleAmount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom1To15OverdueDaysWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym115Dpd1_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd1P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom1To15OverdueDaysWithAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym115Dpd1P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom16To29OverdueDaysWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd1_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd1P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom16To29OverdueDaysWithAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd1P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom30To60OverdueDaysWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd1_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd1P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom30To60OverdueDaysWithAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd1P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom61AndMoreOverdueDaysWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd1_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd1P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom61AndMoreOverdueDaysWithAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd1P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymTotalDpd1_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountWithAssigned}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd1_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymTotalDpd1P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountWithAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd1P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading3_best">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse3_best" aria-expanded="true" aria-controls="collapse3_best" class="collapsed">
                                                    <span class="fa fa-bar-chart"></span> <spring:message code="myBonusPage.titlePaymentsWithoutAssigned"/>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse3_best" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading3_best">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleAmount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd2_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom1To15OverdueDaysNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym115Dpd2_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd2P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom1To15OverdueDaysNoAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym115Dpd2P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd2_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom16To29OverdueDaysNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd2_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd2P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom16To29OverdueDaysNoAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd2P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd2_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom30To60OverdueDaysNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd2_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd2P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom30To60OverdueDaysNoAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd2P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd2_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom61AndMoreOverdueDaysNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd2_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd2P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom61AndMoreOverdueDaysNoAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd2P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymTotalDpd2_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountNoAssigned}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd2_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymTotalDpd2P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmountNoAssigned / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd2P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading4_best">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse4_best" aria-expanded="true" aria-controls="collapse4_best" class="collapsed">
                                                    <span class="fa fa-bar-chart"></span> <strong><spring:message code="myBonusPage.titlePaymentsTotal"/></strong>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse4_best" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading4_best">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleAmount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd3_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom1To15OverdueDays}"/>
                                                            <fmt:formatNumber value="${paym115Dpd3_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd3P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom1To15OverdueDays / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paym115Dpd3P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd3_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom16To29OverdueDays}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd3_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd3P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom16To29OverdueDays / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd3P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd3_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom30To60OverdueDays}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd3_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd3P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom30To60OverdueDays / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd3P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd3_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom61AndMoreOverdueDays}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd3_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd3P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.paymentAmountFrom61AndMoreOverdueDays / individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd3P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="paymTotalDpd3_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymTotalDpd3_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="heading5_best">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapse5_best" aria-expanded="true" aria-controls="collapse5_best" class="collapsed">
                                                    <span class="fa fa-dollar"></span> <span class="text-info"><strong><spring:message code="myBonusPage.titleBonusAmountTotal"/></strong></span>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse5_best" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading5_best">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleAmount"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd4_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountFrom1To15DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paym115Dpd4_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym115Dpd4P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountFrom1To15DaysOverdue / individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paym115Dpd4P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd4_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountFrom16To29DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd4_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym1629Dpd4P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountFrom16To29DaysOverdue / individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paym1629Dpd4P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd4_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountFrom30To60DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd4_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym3060Dpd4P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountFrom30To60DaysOverdue / individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paym3060Dpd4P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd4_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountFrom61AndMoreDaysOverdue}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd4_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paym61AndMoreDpd4P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountFrom61AndMoreDaysOverdue / individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paym61AndMoreDpd4P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.titlePtpResultCalls"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymServicePtpDpd4_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountForRemindingCalls}"/>
                                                            <fmt:formatNumber value="${paymServicePtpDpd4_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymServicePtpDpd4P_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountForRemindingCalls / individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymServicePtpDpd4P_best}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <span class="text-info"><spring:message code="myBonusPage.total"/></span>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="paymTotalDpd4_best" value="${individualBonusForPreSoft.preSoftBackUserResultTheBest.bonusAmountTotal}"/>
                                                            <span class="text-info"><fmt:formatNumber value="${paymTotalDpd4_best}" type="currency" maxFractionDigits="0" currencySymbol="₫"/></span>
                                                        </td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>




                    </div>
                </div>

                <div id="settings" class="tab-pane">
                    <div class="panel-body">
                        <div class="clearfix"></div>
                        <div class="text-box" style="margin-bottom: 20px;">
                            <spring:message code="myBonusPage.textPreSoftConditions"/>
                        </div>
                        <div class="col-lg-12" style="margin-bottom: 10px;">
                            <div class="col-sm-6">
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.minNeededTalkSec"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="talkSec" value="${minNeededTalkSec}"/>
                                        <fmt:formatNumber value="${talkSec}" type="number" maxFractionDigits="0"/>
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.countDaysForResult"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="countDays" value="${countDaysForResult}"/>
                                        <fmt:formatNumber value="${countDays}" type="number" maxFractionDigits="0"/>
                                    </div>
                                </div>

                            </div>

                            <div class="col-sm-6">
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.resultStatuses"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                            ${resultStatuses}
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.bonusAmountForResultedCallInPtp"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="bonusPTP" value="${bonusAmountForResultedCallInPtp}"/>
                                        <fmt:formatNumber value="${bonusPTP}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="text-box" style="margin-bottom: 10px;">
                            <spring:message code="myBonusPage.textPercentageBonusPlan"/>:
                        </div>
                        <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>

                        <div class="tabs-container">
                            <div class="tabs">
                                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="headingOne">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne" class="collapsed">
                                                    <span class="fa fa-leaf"></span> <spring:message code="myBonusPage.textIfPlanAmountThenPercentage"/>:
                                                    <span class="text-info">
                                                        <c:set var="minPlan" value="${minimumPlan}"/>
                                                        <fmt:formatNumber value="${minPlan}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                         -
                                                        <c:set var="planStep1" value="${planAmountStep1}"/>
                                                        <fmt:formatNumber value="${planStep1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                    </span>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapseOne" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="headingOne">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.stage"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.notAssignedDebt"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.assignedDebt"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan1NotAssignedPercent115" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${plan1NotAssignedPercent115}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan1AssignedPercent115" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${plan1AssignedPercent115}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan1NotAssignedPercent1629" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${plan1NotAssignedPercent1629}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan1AssignedPercent1629" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${plan1AssignedPercent1629}" type="percent" maxFractionDigits="2"/>
                                                        </td class="center">
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan1NotAssignedPercent3060" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${plan1NotAssignedPercent3060}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan1AssignedPercent3060" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${plan1AssignedPercent3060}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan1NotAssignedPercent61" value="${BONUS_PERCENT_FOR_61_AND_MORE_NOT_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${plan1NotAssignedPercent61}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan1AssignedPercent61" value="${BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${plan1AssignedPercent61}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                    </div>




                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="headingTwo">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo" class="collapsed">
                                                    <span class="fa fa-leaf"></span> <spring:message code="myBonusPage.textIfPlanAmountThenPercentage"/>:
                                                    <span class="text-info">
                                                        <c:set var="planStep1" value="${planAmountStep1}"/>
                                                        <fmt:formatNumber value="${planStep1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                         -
                                                        <c:set var="planStep2" value="${planAmountStep2}"/>
                                                        <fmt:formatNumber value="${planStep2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                    </span>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapseTwo" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="headingTwo">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.stage"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.notAssignedDebt"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.assignedDebt"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan2NotAssignedPercent115" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${plan2NotAssignedPercent115}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan2AssignedPercent115" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${plan2AssignedPercent115}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan2NotAssignedPercent1629" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${plan2NotAssignedPercent1629}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan2AssignedPercent1629" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${plan2AssignedPercent1629}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan2NotAssignedPercent3060" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${plan2NotAssignedPercent3060}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan2AssignedPercent3060" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${plan2AssignedPercent3060}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan2NotAssignedPercent61" value="${BONUS_PERCENT_FOR_61_AND_MORE_NOT_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${plan2NotAssignedPercent61}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan2AssignedPercent61" value="${BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${plan2AssignedPercent61}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                    </div>




                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="headingThree">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="true" aria-controls="collapseThree" class="collapsed">
                                                    <span class="fa fa-leaf"></span> <spring:message code="myBonusPage.textIfPlanAmountThenPercentage"/>:
                                                    <span class="text-info">
                                                        <c:set var="planStep2" value="${planAmountStep2}"/>
                                                        <fmt:formatNumber value="${planStep2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                         -
                                                        <c:set var="planStep3" value="${planAmountStep3}"/>
                                                        <fmt:formatNumber value="${planStep3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                    </span>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapseThree" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="headingThree">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.stage"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.notAssignedDebt"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.assignedDebt"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan3NotAssignedPercent115" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${plan3NotAssignedPercent115}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan3AssignedPercent115" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${plan3AssignedPercent115}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan3NotAssignedPercent1629" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${plan3NotAssignedPercent1629}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan3AssignedPercent1629" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${plan3AssignedPercent1629}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan3NotAssignedPercent3060" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${plan3NotAssignedPercent3060}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan3AssignedPercent3060" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${plan3AssignedPercent3060}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan3NotAssignedPercent61" value="${BONUS_PERCENT_FOR_61_AND_MORE_NOT_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${plan3NotAssignedPercent61}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan3AssignedPercent61" value="${BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${plan3AssignedPercent61}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                    </div>



                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="headingFour">
                                            <h5 class="panel-title">
                                                <a role="button" data-toggle="collapse" data-target="#collapseFour" aria-expanded="true" aria-controls="collapseFour" class="collapsed">
                                                    <span class="fa fa-leaf"></span> <spring:message code="myBonusPage.textIfPlanAmountThenPercentage"/>:
                                                    <span class="text-info">
                                                        <c:set var="planStep3" value="${planAmountStep3}"/>
                                                        <fmt:formatNumber value="${planStep3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                         - ...
                                                    </span>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapseFour" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="headingFour">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.stage"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.notAssignedDebt"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="myBonusPage.assignedDebt"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments1_15Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan4NotAssignedPercent115" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${plan4NotAssignedPercent115}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan4AssignedPercent115" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${plan4AssignedPercent115}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan4NotAssignedPercent1629" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${plan4NotAssignedPercent1629}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan4AssignedPercent1629" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${plan4AssignedPercent1629}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan4NotAssignedPercent3060" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${plan4NotAssignedPercent3060}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan4AssignedPercent3060" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${plan4AssignedPercent3060}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan4NotAssignedPercent61" value="${BONUS_PERCENT_FOR_61_AND_MORE_NOT_OVERDUE_ASSIGNED_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${plan4NotAssignedPercent61}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="plan4AssignedPercent61" value="${BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${plan4AssignedPercent61}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                    </div>


                                </div>
                            </div>
                        </div>


                    </div>
                </div>

            </div>

        </div>
        </div>




        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
