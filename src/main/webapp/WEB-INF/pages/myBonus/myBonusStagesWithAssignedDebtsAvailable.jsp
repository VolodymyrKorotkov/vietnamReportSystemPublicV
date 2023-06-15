<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 31.07.2022
  Time: 22:02
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

                            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" action="/mybonus-stage-with-assigned-debt" modelAttribute="datesRequest">
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
                                                        <spring:message code="myBonusPage.payments16_24Dpd"/>
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
                                                        <spring:message code="myBonusPage.payments25_60Dpd"/>
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




        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
