<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 29.12.2022
  Time: 14:48
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
                <h2><spring:message code="listResultedCallsAsPtP.title"/></h2>
                <h6><spring:message code="listResultedCallsAsPtP.title"/> / ${user} </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/mybonus" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>


        </div>

        <div class="col-lg-12" style="margin-bottom: 20px;">
            <c:if test="${listPtP.size() > 0}">
                <table class="table table-bordered table-hover table-pageable" style="font-size: small">
                    <thead>
                    <tr>
                        <th class="center" style="width: 50px; font-size: small;">
                            #
                        </th>
                        <th class="center" style="font-size: small;">
                            <spring:message code="common.titleAmount"/>
                        </th>
                        <th class="center" style="font-size: small;">
                            <spring:message code="common.daysOverdue"/>
                        </th>
                        <th class="center" style="font-size: small;">
                            <spring:message code="common.dateAndTimeOfCall"/>
                        </th>
                        <th class="center" style="font-size: small;">
                            <spring:message code="common.maxDateForPayment"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${listPtP}" varStatus="i">
                    <tr class="clickable blank" href="<c:url value="https://app.tienoi.com.vn/back-office/debt-collection/${item.debtId}"/>">
                        <td class="center" style="font-size: small;">${i.index + 1}</td>
                        <td class="center title" style="font-size: small;">
                            <c:set var="amount" value="${item.currentDebt}"/>
                            <fmt:formatNumber value="${amount}" type="number" maxFractionDigits="0"/>
                        </td>
                        <td class="center title" style="font-size: small;">
                            <c:set var="days" value="${item.daysOverdue}"/>
                            <fmt:formatNumber value="${days}" type="number" maxFractionDigits="0"/>
                        </td>
                        <td class="center" style="font-size: small;">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" var="date" value="${item.dateTimeCall}" type="both"/>
                            <fmt:formatDate value="${date}" pattern="dd.MM.yyyy HH:mm"/>
<%--                                ${item.dateTimeCall}--%>
                        </td>
                        <td class="center title" style="font-size: small;">
                            <fmt:parseDate pattern="yyyy-MM-dd" var="maxDate" value="${item.maxDateForPayment}" type="both"/>
                            <fmt:formatDate value="${maxDate}" pattern="dd.MM.yyyy"/>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="999" style="font-size: small;">
                            <div class="hidden-xs" style="padding-top: 5px;">
                                <spring:message code="common.totalCountRows"/> ${countRows}
                            </div>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </c:if>

        </div>

    </jsp:attribute>
</mytags:header-template>

</html>
