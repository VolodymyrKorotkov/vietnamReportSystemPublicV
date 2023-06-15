<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 26.06.2022
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<mytag:header-template>
    <jsp:attribute name="contentPage">
        <div class="row wrapper border-bottom white-bg page-heading" style="margin-bottom: 30px;">
            <div class="col-lg-7">
                <fmt:parseDate pattern="yyyy-MM-dd" var="date" value="${modelPlan.dateMonth}" type="both"/>
                <h2><spring:message code="reportsPage.titleModelPlan"/> / <strong><fmt:formatDate value="${date}" pattern="MMMM yyyy"/></strong> </h2>
                <h6><spring:message code="reportsPage.reportsPageTitle"/> / <spring:message code="reportsPage.titleModelPlans"/> / <spring:message code="reportsPage.titleModelPlan"/> / <fmt:formatDate value="${date}" pattern="MMMM yyyy"/> / <spring:message code="common.view"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/reports/model-plans" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                            <spring:message code="common.actionsButton"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a href="/reports/model-plans/${modelPlan.id}/edit">
                                    <spring:message code="common.editButton"/>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>

        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${message}"/>

            </div>
        </c:if>

        <div class="col-lg-12" style="margin-bottom: 20px;">
            <div class="col-sm-4 col-view">
                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.modelPlanForDateMoth"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <fmt:parseDate pattern="yyyy-MM-dd" var="date" value="${modelPlan.dateMonth}" type="both"/>
                        <fmt:formatDate value="${date}" pattern="MMMM yyyy"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanContractsAmount"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="contractsAmount" value="${modelPlan.contractsAmountTotal}"/>
                        <fmt:formatNumber value="${contractsAmount}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanIssuedAmountTotal"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="issuedAmount" value="${modelPlan.issuedAmountTotal}"/>
                        <fmt:formatNumber value="${issuedAmount}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanProlongedAmountTotal"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="prolongedAmount" value="${modelPlan.prolongedAmount}"/>
                        <fmt:formatNumber value="${prolongedAmount}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanIssuedAmountNew"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="issuedAmountNew" value="${modelPlan.issuedAmountNew}"/>
                        <fmt:formatNumber value="${issuedAmountNew}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanIssuedAmountRepeat"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="issuedAmountRepeat" value="${modelPlan.issuedAmountRepeat}"/>
                        <fmt:formatNumber value="${issuedAmountRepeat}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

            </div>

            <div class="col-sm-4 col-view">

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanTotalRepaymentTotal"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="totalRepaymentTotal" value="${modelPlan.repaymentTotalAmountTotal}"/>
                        <fmt:formatNumber value="${totalRepaymentTotal}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanTotalRepaymentNew"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="totalRepaymentNew" value="${modelPlan.repaymentTotalAmountNew}"/>
                        <fmt:formatNumber value="${totalRepaymentNew}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanTotalRepaymentRepeat"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="totalRepaymentRepeat" value="${modelPlan.repaymentTotalAmountRepeat}"/>
                        <fmt:formatNumber value="${totalRepaymentRepeat}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanRepaymentPrincipalTotal"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="repaymentPrincipalTotal" value="${modelPlan.repaymentPrincipalAmountTotal}"/>
                        <fmt:formatNumber value="${repaymentPrincipalTotal}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanRepaymentPrincipalNew"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="repaymentPrincipalNew" value="${modelPlan.repaymentPrincipalAmountNew}"/>
                        <fmt:formatNumber value="${repaymentPrincipalNew}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanRepaymentPrincipalRepeat"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="repaymentPrincipalRepeat" value="${modelPlan.repaymentPrincipalAmountRepeat}"/>
                        <fmt:formatNumber value="${repaymentPrincipalRepeat}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanRepaymentIncomeTotal"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="repaymentIncomeTotal" value="${modelPlan.repaymentIncomeAmountTotal}"/>
                        <fmt:formatNumber value="${repaymentIncomeTotal}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanRepaymentIncomeNew"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="repaymentIncomeNew" value="${modelPlan.repaymentIncomeAmountNew}"/>
                        <fmt:formatNumber value="${repaymentIncomeNew}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.modelPlanRepaymentIncomeRepeat"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="repaymentIncomeRepeat" value="${modelPlan.repaymentIncomeAmountRepeat}"/>
                        <fmt:formatNumber value="${repaymentIncomeRepeat}" type="currency" maxFractionDigits="0" currencySymbol="$"/>
                    </div>
                </div>

            </div>

            <div class="col-sm-4 col-view">

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.createdAt"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAt" value="${modelPlan.createdAt}" type="both"/>
                        <fmt:formatDate value="${createdAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.lastModifiedAt"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="modifiedAt" value="${modelPlan.lastModifiedAt}" type="both"/>
                        <fmt:formatDate value="${modifiedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.createdBy"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="createdBy" value="${modelPlan.createdBy}"/>
                        <c:if test="${empty createdBy}">-</c:if>
                        <c:if test="${not empty createdBy}">
                            ${createdBy.username}
                        </c:if>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.lastModifiedBy"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="modifiedBy" value="${modelPlan.lastModifiedBy}"/>
                        <c:if test="${empty modifiedBy}">-</c:if>
                        <c:if test="${not empty modifiedBy}">
                            ${modifiedBy.username}
                        </c:if>
                    </div>
                </div>

            </div>

        </div>
    </jsp:attribute>
</mytag:header-template>

</html>
