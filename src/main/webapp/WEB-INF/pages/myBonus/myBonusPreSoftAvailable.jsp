<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 28.07.2022
  Time: 17:47
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
                    <a href="/mybonus/pre-soft/active-ptp-call-list" class="btn btn-primary">
                        <spring:message code="listResultedCallsAsPtP.showMyPtPButton"/>
                    </a>
                </div>
            </div>
        </div>

        <%--        <c:if test="${not empty messageGood}">--%>
        <%--            <div class="alert alert-success alert-dismissable">--%>
        <%--                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>--%>
        <%--                <spring:message code="${messageGood}"/>--%>
        <%--            </div>--%>
        <%--        </c:if>--%>
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
                        <div class="col-lg-12">
                            <p class="text-box" style="margin-bottom: 20px;">
                                <spring:message code="myBonusPage.textAvailableBonusForRole"/>
                            </p>

                            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" action="/mybonus-presoft" modelAttribute="datesRequest">
                            <div class="clearfix">

                                <div class="col-lg-9">
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="from" class="col-sm-8 control-label">
                                                <spring:message code="reportsPage.commonTextTitleFrom"/>:
                                            </label>
                                            <div class="col-sm-4">
                                                <form:input path="from" type="date" cssClass="form-control date-mask" id="from" required=""></form:input>
                                                <form:errors path="from"></form:errors>
                                                <c:if test="${not empty fromError}">
                                                    <p class="help-block error text-danger"><spring:message code="${fromError}"/></p>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="to" class="col-sm-8 control-label">
                                                <spring:message code="reportsPage.commonTextTitleTo"/>:
                                            </label>
                                            <div class="col-sm-4">
                                                <form:input path="to" type="date" cssClass="form-control date-mask" id="to" required=""></form:input>
                                                <form:errors path="to"></form:errors>
                                                <c:if test="${not empty toError}">
                                                    <p class="help-block error text-danger"><spring:message code="${toError}"/></p>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="clearfix"></div>
                        <div class="hr-line-dashed"></div>
                        <div class="col-lg-6">
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-4">
                                    <button class="btn btn-primary" type="submit"><spring:message code="common.getResultButton"/></button>
                                </div>
                            </div>
                        </div>
                        </form:form>
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
