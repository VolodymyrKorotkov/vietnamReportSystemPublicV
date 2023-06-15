<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 01.08.2022
  Time: 13:14
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
                                        <fmt:parseDate pattern="yyyy-MM-dd" var="dateFrom" value="${individualBonus.dateFrom}" type="both"/>
                                        <fmt:formatDate value="${dateFrom}" pattern="dd.MM.yyyy"/>
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.textPositionRating"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="position" value="${individualBonus.positionOfPerson}"/>
                                        <strong><fmt:formatNumber value="${position}" type="number" maxFractionDigits="0"/></strong>
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.textTotalBonusAmountPerson"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="totalBonusP" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountTotal}"/>
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
                                        <fmt:parseDate pattern="yyyy-MM-dd" var="dateTo" value="${individualBonus.dateTo}" type="both"/>
                                        <fmt:formatDate value="${dateTo}" pattern="dd.MM.yyyy"/>
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.textTotalCountEmployees"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="totalCount" value="${individualBonus.countEmployees}"/>
                                        <fmt:formatNumber value="${totalCount}" type="number" maxFractionDigits="0"/>
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="myBonusPage.textTotalBonusAmountBestInDepartment"/>:
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="totalBonusB" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountTotal}"/>
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
                                                    <span class="fa fa-line-chart"></span> <spring:message code="myBonusPage.titlePaymentsTotal"/>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse1" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading1">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleCount"/>
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
                                                            <c:set var="paymCount115Dpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentCountFrom1To15OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymCount115Dpd1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymAmount115Dpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentAmountFrom1To15OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymAmount115Dpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymPercent115Dpd1P" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentAmountFrom1To15OverdueDays / individualBonus.stagesWithAssignedUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymPercent115Dpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymCount1629Dpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentCountFrom16To29OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymCount1629Dpd1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymAmount1629Dpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentAmountFrom16To29OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymAmount1629Dpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymPercent1629Dpd1P" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentAmountFrom16To29OverdueDays / individualBonus.stagesWithAssignedUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymPercent1629Dpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymCount3060Dpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentCountFrom30To60OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymCount3060Dpd1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymAmount3060Dpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentAmountFrom30To60OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymAmount3060Dpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymPercent3060Dpd1P" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentAmountFrom30To60OverdueDays / individualBonus.stagesWithAssignedUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymPercent3060Dpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymCount61AndMoreDpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentCountFrom61AndMoreOverdueDays}"/>
                                                            <fmt:formatNumber value="${paymCount61AndMoreDpd1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymAmount61AndMoreDpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentAmountFrom61AndMoreOverdueDays}"/>
                                                            <fmt:formatNumber value="${paymAmount61AndMoreDpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymPercent61AndMoreDpd1P" value="${individualBonus.stagesWithAssignedUserResultOfPerson.paymentAmountFrom61AndMoreOverdueDays / individualBonus.stagesWithAssignedUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymPercent61AndMoreDpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.paymentsTotal"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymCountTotalDpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.totalPaymentCount}"/>
                                                            <fmt:formatNumber value="${paymCountTotalDpd1}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="paymAmountTotalDpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymAmountTotalDpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
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
                                                    <span class="fa fa-dollar"></span> <span class="text-info"><strong><spring:message code="myBonusPage.titleBonusAmountTotal"/></strong></span>
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
                                                            <c:set var="paymBonusAmount115Dpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountFrom1To15DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paymBonusAmount115Dpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusPercent115Dpd1P" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountFrom1To15DaysOverdue / individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymBonusPercent115Dpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusAmount1629Dpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountFrom16To29DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paymBonusAmount1629Dpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusPercent1629Dpd1P" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountFrom16To29DaysOverdue / individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymBonusPercent1629Dpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusAmount3060Dpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountFrom30To60DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paymBonusAmount3060Dpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusPercent3060Dpd1P" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountFrom30To60DaysOverdue / individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymBonusPercent3060Dpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusAmount61AndMoreDpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountFrom61AndMoreDaysOverdue}"/>
                                                            <fmt:formatNumber value="${paymBonusAmount61AndMoreDpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusPercent61AndMoreDpd1P" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountFrom61AndMoreDaysOverdue / individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymBonusPercent61AndMoreDpd1P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <span class="text-info"><spring:message code="myBonusPage.paymentsTotal"/></span>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="paymBonusAmountTotalDpd1" value="${individualBonus.stagesWithAssignedUserResultOfPerson.bonusAmountTotal}"/>
                                                            <span class="text-info"><fmt:formatNumber value="${paymBonusAmountTotalDpd1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/></span>
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
                                                    <span class="fa fa-line-chart"></span> <spring:message code="myBonusPage.titlePaymentsTotal"/>
                                                </a>
                                            </h5>
                                        </div>
                                        <div id="collapse1_best" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading1_best">
                                            <div class="panel-body">
                                                <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                    <thead>
                                                    <tr>
                                                        <th class="center">

                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titleCount"/>
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
                                                            <c:set var="paymCount115Dpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentCountFrom1To15OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymCount115Dpd2}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymAmount115Dpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentAmountFrom1To15OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymAmount115Dpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymPercent115Dpd2P" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentAmountFrom1To15OverdueDays / individualBonus.stagesWithAssignedUserResultOfTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymPercent115Dpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymCount1629Dpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentCountFrom16To29OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymCount1629Dpd2}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymAmount1629Dpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentAmountFrom16To29OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymAmount1629Dpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymPercent1629Dpd2P" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentAmountFrom16To29OverdueDays / individualBonus.stagesWithAssignedUserResultOfTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymPercent1629Dpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymCount3060Dpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentCountFrom30To60OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymCount3060Dpd2}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymAmount3060Dpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentAmountFrom30To60OverdueDays}"/>
                                                            <fmt:formatNumber value="${paymAmount3060Dpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymPercent3060Dpd2P" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentAmountFrom30To60OverdueDays / individualBonus.stagesWithAssignedUserResultOfTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymPercent3060Dpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymCount61AndMoreDpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentCountFrom61AndMoreOverdueDays}"/>
                                                            <fmt:formatNumber value="${paymCount61AndMoreDpd2}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymAmount61AndMoreDpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentAmountFrom61AndMoreOverdueDays}"/>
                                                            <fmt:formatNumber value="${paymAmount61AndMoreDpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymPercent61AndMoreDpd2P" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.paymentAmountFrom61AndMoreOverdueDays / individualBonus.stagesWithAssignedUserResultOfTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymPercent61AndMoreDpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.paymentsTotal"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymCountTotalDpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.totalPaymentCount}"/>
                                                            <fmt:formatNumber value="${paymCountTotalDpd2}" type="number" maxFractionDigits="0"/>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="paymAmountTotalDpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.totalPaymentAmount}"/>
                                                            <fmt:formatNumber value="${paymAmountTotalDpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
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
                                                    <span class="fa fa-dollar"></span> <span class="text-info"><strong><spring:message code="myBonusPage.titleBonusAmountTotal"/></strong></span>
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
                                                            <c:set var="paymBonusAmount115Dpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountFrom1To15DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paymBonusAmount115Dpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusPercent115Dpd2P" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountFrom1To15DaysOverdue / individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymBonusPercent115Dpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments16_29Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusAmount1629Dpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountFrom16To29DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paymBonusAmount1629Dpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusPercent1629Dpd2P" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountFrom16To29DaysOverdue / individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymBonusPercent1629Dpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments30_60Dpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusAmount3060Dpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountFrom30To60DaysOverdue}"/>
                                                            <fmt:formatNumber value="${paymBonusAmount3060Dpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusPercent3060Dpd2P" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountFrom30To60DaysOverdue / individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymBonusPercent3060Dpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <spring:message code="myBonusPage.payments61AndMoreDpd"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusAmount61AndMoreDpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountFrom61AndMoreDaysOverdue}"/>
                                                            <fmt:formatNumber value="${paymBonusAmount61AndMoreDpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="paymBonusPercent61AndMoreDpd2P" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountFrom61AndMoreDaysOverdue / individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountTotal}"/>
                                                            <fmt:formatNumber value="${paymBonusPercent61AndMoreDpd2P}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr>
                                                        <td class="center">
                                                            <span class="text-info"><spring:message code="myBonusPage.paymentsTotal"/></span>
                                                        </td>
                                                        <td colspan="2" class="center">
                                                            <c:set var="paymBonusAmountTotalDpd2" value="${individualBonus.stagesWithAssignedUserResultOfTheBest.bonusAmountTotal}"/>
                                                            <span class="text-warning"><fmt:formatNumber value="${paymBonusAmountTotalDpd2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/></span>
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
                            <spring:message code="myBonusPage.textStageWithAssignedDebtsConditions"/>
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
                                                    <span class="fa fa-leaf"></span>
                                                    <span class="text-info">
                                                        <spring:message code="myBonusPage.payments1_15Dpd"/>
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
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <c:set var="minPlan1" value="${MINIMUM_PLAN_DPD_1_15}"/>
                                                            <fmt:formatNumber value="${minPlan1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep1_1" value="${PLAN_AMOUNT_STEP_1_DPD_1_15}"/>
                                                            <fmt:formatNumber value="${planStep1_1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent1_1" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${planPercent1_1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep1_1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep2_1" value="${PLAN_AMOUNT_STEP_2_DPD_1_15}"/>
                                                            <fmt:formatNumber value="${planStep2_1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent2_1" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${planPercent2_1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep2_1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep3_1" value="${PLAN_AMOUNT_STEP_3_DPD_1_15}"/>
                                                            <fmt:formatNumber value="${planStep3_1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent3_1" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${planPercent3_1}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep3_1}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - ...
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent4_1" value="${BONUS_PERCENT_FOR_1_15_OVERDUE_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${planPercent4_1}" type="percent" maxFractionDigits="2"/>
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
                                                    <span class="fa fa-leaf"></span>
                                                    <span class="text-info">
                                                        <spring:message code="myBonusPage.payments16_29Dpd"/>
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
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <c:set var="minPlan2" value="${MINIMUM_PLAN_DPD_16_29}"/>
                                                            <fmt:formatNumber value="${minPlan2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep1_2" value="${PLAN_AMOUNT_STEP_1_DPD_16_29}"/>
                                                            <fmt:formatNumber value="${planStep1_2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent1_2" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${planPercent1_2}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep1_2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep2_2" value="${PLAN_AMOUNT_STEP_2_DPD_16_29}"/>
                                                            <fmt:formatNumber value="${planStep2_2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent2_2" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${planPercent2_2}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep2_2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep3_2" value="${PLAN_AMOUNT_STEP_3_DPD_16_29}"/>
                                                            <fmt:formatNumber value="${planStep3_2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent3_2" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${planPercent3_2}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep3_2}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - ...
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent4_2" value="${BONUS_PERCENT_FOR_16_29_OVERDUE_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${planPercent4_2}" type="percent" maxFractionDigits="2"/>
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
                                                    <span class="fa fa-leaf"></span>
                                                    <span class="text-info">
                                                        <spring:message code="myBonusPage.payments30_60Dpd"/>
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
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <c:set var="minPlan3" value="${MINIMUM_PLAN_DPD_30_60}"/>
                                                            <fmt:formatNumber value="${minPlan3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             -
                                                            <c:set var="planStep1_3" value="${PLAN_AMOUNT_STEP_1_DPD_30_60}"/>
                                                            <fmt:formatNumber value="${planStep1_3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent1_3" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${planPercent1_3}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep1_3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             -
                                                            <c:set var="planStep2_3" value="${PLAN_AMOUNT_STEP_2_DPD_30_60}"/>
                                                            <fmt:formatNumber value="${planStep2_3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent2_3" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${planPercent2_3}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep2_3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep3_3" value="${PLAN_AMOUNT_STEP_3_DPD_30_60}"/>
                                                            <fmt:formatNumber value="${planStep3_3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent3_3" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${planPercent3_3}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep3_3}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - ...
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent4_3" value="${BONUS_PERCENT_FOR_30_60_OVERDUE_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${planPercent4_3}" type="percent" maxFractionDigits="2"/>
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
                                                    <span class="fa fa-leaf"></span>
                                                    <span class="text-info">
                                                        <spring:message code="myBonusPage.payments61AndMoreDpd"/>
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
                                                            <spring:message code="myBonusPage.paymentsAmountTotal"/>
                                                        </th>
                                                        <th class="center">
                                                            <spring:message code="common.titlePercentage"/>
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td class="center">
                                                            <c:set var="minPlan4" value="${MINIMUM_PLAN_DPD_61_AND_MORE}"/>
                                                            <fmt:formatNumber value="${minPlan4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep1_4" value="${PLAN_AMOUNT_STEP_1_DPD_61_AND_MORE}"/>
                                                            <fmt:formatNumber value="${planStep1_4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent1_4" value="${BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_1}"/>
                                                            <fmt:formatNumber value="${planPercent1_4}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep1_4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep2_4" value="${PLAN_AMOUNT_STEP_2_DPD_61_AND_MORE}"/>
                                                            <fmt:formatNumber value="${planStep2_4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent2_4" value="${BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_2}"/>
                                                            <fmt:formatNumber value="${planPercent2_4}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep2_4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - 
                                                            <c:set var="planStep3_4" value="${PLAN_AMOUNT_STEP_3_DPD_61_AND_MORE}"/>
                                                            <fmt:formatNumber value="${planStep3_4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent3_4" value="${BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_3}"/>
                                                            <fmt:formatNumber value="${planPercent3_4}" type="percent" maxFractionDigits="2"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="center">
                                                            <fmt:formatNumber value="${planStep3_4}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                                                             - ...
                                                        </td>
                                                        <td class="center">
                                                            <c:set var="planPercent4_4" value="${BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_COMPLETED_PLAN}"/>
                                                            <fmt:formatNumber value="${planPercent4_4}" type="percent" maxFractionDigits="2"/>
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
    </jsp:attribute>
</mytags:header-template>

</html>
