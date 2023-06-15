<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 25.06.2022
  Time: 21:39
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
                <fmt:parseDate pattern="yyyy-MM-dd" var="date" value="${currencyRate.date}" type="both"/>
                <h2><spring:message code="reportsPage.titleCurrencyRateView"/> / <strong><fmt:formatDate value="${date}" pattern="dd.MM.yyyy"/></strong> </h2>
                <h6><spring:message code="reportsPage.reportsPageTitle"/> / <spring:message code="reportsPage.usedCurrencyRate"/> / <spring:message code="reportsPage.titleCurrencyRateView"/> / <fmt:formatDate value="${date}" pattern="dd.MM.yyyy"/> / <spring:message code="common.view"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/reports/currency-rate" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                            <spring:message code="common.actionsButton"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a href="/reports/currency-rate/${currencyRate.id}/edit">
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
            <div class="col-sm-6 col-view">
                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.date"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <fmt:parseDate pattern="yyyy-MM-dd" var="date" value="${currencyRate.date}" type="both"/>
                        <fmt:formatDate value="${date}" pattern="dd.MM.yyyy"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="reportsPage.currencyRateUsdVnd"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <span class="label label-primary" style="font-size: large">
                            <c:set var="vndusd" value="${currencyRate.usdVnd}"/>
                            <fmt:formatNumber value="${vndusd}" type="currency" maxFractionDigits="0" currencySymbol="₫"/>
                        </span>
                    </div>
                </div>


            </div>

            <div class="col-sm-6 col-view">

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.createdAt"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAt" value="${currencyRate.createdAt}" type="both"/>
                        <fmt:formatDate value="${createdAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.lastModifiedAt"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="modifiedAt" value="${currencyRate.lastModifiedAt}" type="both"/>
                        <fmt:formatDate value="${modifiedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.lastModifiedBy"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="modifiedBy" value="${currencyRate.lastModifiedBy}"/>
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
