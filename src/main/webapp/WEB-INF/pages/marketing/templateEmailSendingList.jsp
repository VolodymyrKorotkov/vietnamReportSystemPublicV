<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 21.09.2022
  Time: 19:35
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
                <h2><spring:message code="marketingPage.templatesEmailSending"/></h2>
                <h6><spring:message code="marketingPage.marketingTitle"/> / <spring:message code="marketingPage.templatesEmailSending"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                    <a href="/marketing/template-email-sending-list/create" class="btn btn-warning">
                        <spring:message code="marketingPage.buttonCreateNewTemplate"/>
                    </a>
                    <button class="btn btn-primary table-filter-button">
                        <spring:message code="common.filterSearch"/>
                    </button>
                </div>
            </div>
            <div class="col-lg-12" style="margin-top: 10px; margin-bottom: 10px;">

                <div class="content-section row wrapper wrapper-content border-bottom white-bg table-filter-container"
                     hidden = "${(viewState == null or not viewState.filtersPanelShown) and (param.sf == null or param.sf[0] ne 'true')}">
                    <form method="get" class="table-filter-form" style="margin-top: 20px; margin-bottom: 10px;">
                        <div class="col-lg-12">
                            <div class="col-lg-6">
                                <div class="col-md-8" style="margin-bottom: 10px;">
                                    <div class="form-group">
                                        <label>
                                            <spring:message code="marketingPage.createdAtFrom"/>
                                        </label>
                                        <input type="date" id="dateFrom" class="form-control" name="dateFrom" value="${param.get(dateFrom)}">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="col-md-8" style="margin-bottom: 10px;">
                                    <div class="form-group">
                                        <label>
                                            <spring:message code="marketingPage.createdAtTo"/>
                                        </label>
                                        <input type="date" id="dateTo" class="form-control" name="dateTo" value="${param.get(dateTo)}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <button type="submit" class="btn btn-white btn-sm">
                                <spring:message code="common.ApplyFilters"/>
                            </button>
                            <a class="btn btn-white btn-sm btn-clear-filter" href="/marketing/template-email-sending-list">
                                <spring:message code="common.ResetFilters"/>
                            </a>
                        </div>
                    </form>
                </div>
            </div>


        </div>

        <c:if test="${not empty messageGood}">
            <div class="alert alert-success alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${messageGood}"/>
            </div>
        </c:if>
        <c:if test="${not empty messageBad}">
            <div class="alert alert-danger alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${messageBad}"/>
            </div>
        </c:if>

        <div class="col-lg-12" style="margin-bottom: 20px;">
            <c:if test="${pagesCount > 0}">
                <p class="text-box" style="margin-bottom: 10px">
                    <spring:message code="common.textCommonForDetailedInfoClick"/>
                </p>

            <table class="table table-bordered table-hover table-pageable" style="font-size: small">
                <thead>
                <tr>
                    <th class="center" style="width: 50px; font-size: small;">
                        #
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="marketingPage.templateEmailSendingTitle"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="marketingPage.createdAt"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="marketingPage.createdBy"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="marketingPage.modifiedAt"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="marketingPage.modifiedBy"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="template" items="${templateSendingEmailList}" varStatus="i">
                    <tr class="clickable" href="<c:url value="/marketing/template-email-sending-list/${template.id}"/>">
                        <td class="center" style="font-size: small;">${i.index + 1 + (page - 1) * 10}</td>
                        <td class="center title" style="font-size: small;">
                            ${template.title}
                        </td>
                        <td class="center date" style="font-size: small;">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAt" value="${template.createdAt}" type="both"/>
                            <fmt:formatDate value="${createdAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </td>
                        <td class="center title" style="font-size: small;">
                                ${template.createdBy.username}
                        </td>
                        <td class="center date" style="font-size: small;">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="modifiedAt" value="${template.lastModifiedAt}" type="both"/>
                            <fmt:formatDate value="${modifiedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </td>
                        <td class="center title" style="font-size: small;">
                                ${template.lastModifiedBy.username}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="999" style="font-size: small;">
                        <div class="pull-right">
                            <c:url value="/marketing/template-email-sending-list" var="urlFirstPage">
                                <c:param name="pageString" value="1"/>
                                <c:param name="dateFrom" value="${param.dateFrom}"/>
                                <c:param name="dateTo" value="${param.dateTo}"/>
                            </c:url>
                            <c:url value="/marketing/template-email-sending-list" var="urlLastPage">
                                <c:param name="pageString" value="${pagesCount}"/>
                                <c:param name="dateFrom" value="${param.dateFrom}"/>
                                <c:param name="dateTo" value="${param.dateTo}"/>
                            </c:url>

                            <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page == 1}">
                                        <c:url value="/marketing/template-email-sending-list" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="dateFrom" value="${param.dateFrom}"/>
                                            <c:param name="dateTo" value="${param.dateTo}"/>
                                        </c:url>
                                    </c:when>
                                    <c:when test="${empty page}">
                                        <c:url value="/marketing/template-email-sending-list" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="dateFrom" value="${param.dateFrom}"/>
                                            <c:param name="dateTo" value="${param.dateTo}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/marketing/template-email-sending-list" var="urlPreviousPage">
                                            <c:param name="pageString" value="${page - 1}"/>
                                            <c:param name="dateFrom" value="${param.dateFrom}"/>
                                            <c:param name="dateTo" value="${param.dateTo}"/>
                                        </c:url>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                            <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page >= pagesCount}">
                                        <c:url value="/marketing/template-email-sending-list" var="urlNextPage">
                                            <c:param name="pageString" value="${pagesCount}"/>
                                            <c:param name="dateFrom" value="${param.dateFrom}"/>
                                            <c:param name="dateTo" value="${param.dateTo}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/marketing/template-email-sending-list" var="urlNextPage">
                                            <c:param name="pageString" value="${page + 1}"/>
                                            <c:param name="dateFrom" value="${param.dateFrom}"/>
                                            <c:param name="dateTo" value="${param.dateTo}"/>
                                        </c:url>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                            <a class="btn btn-default btn-circle btn-xs fa fa-angle-double-left table-filter-btn hidden-xs"
                               href="${urlFirstPage}"></a>
                            <a class="btn btn-default btn-circle btn-xs fa fa-angle-left table-filter-btn hidden-xs"
                               href="${urlPreviousPage}"></a>
                            <span class="label label-primary" style="margin-right: 5px;">
                                <spring:message code="common.pageFooterNumber"/>${page} <spring:message code="common.pagesFooterFrom"/> ${pagesCount}
                            </span>
                            <a class="btn btn-default btn-circle btn-xs fa fa-angle-right table-filter-btn hidden-xs"
                               href="${urlNextPage}"></a>
                            <a class="btn btn-default btn-circle btn-xs fa fa-angle-double-right table-filter-btn hidden-xs"
                               href="${urlLastPage}"></a>
                        </div>
                        <div class="hidden-xs" style="padding-top: 5px;">
                            <spring:message code="common.totalCountRows"/> ${templateCount}
                        </div>
                    </td>
                </tr>
                </tfoot>

            </table>
            </c:if>

        </div>


        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
