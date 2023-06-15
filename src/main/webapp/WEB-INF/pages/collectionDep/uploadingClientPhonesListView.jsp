<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 03.11.2022
  Time: 16:11
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
                <h2><spring:message code="uploadingClientPhonesList.title"/></h2>
                <h6><spring:message code="collectionDepTitle"/> / <spring:message code="uploadingClientPhonesList.title"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
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
                                            <spring:message code="reportsPage.commonTextTitleFrom"/>
                                        </label>
                                        <input type="date" id="dateFrom" class="form-control" name="dateFrom" value="${param.get(dateFrom)}">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="col-md-8" style="margin-bottom: 10px;">
                                    <div class="form-group">
                                        <label>
                                            <spring:message code="reportsPage.commonTextTitleTo"/>
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
                            <a class="btn btn-white btn-sm btn-clear-filter" href="/collection-dep/uploading-client-phones">
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

            <table class="table table-bordered table-hover table-pageable" style="font-size: small">
                <thead>
                <tr>
                    <th class="center" style="width: 50px; font-size: small;">
                        #
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="marketingPage.startedAt"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="marketingPage.completedAt"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="uploadingClientPhonesList.uploadedBy"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="uploadingClientPhonesList.countPhonesForAdding"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="uploadingClientPhonesList.countPhonesAdded"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sc" items="${uploadingClientPhonesList}" varStatus="i">
                    <tr>
                        <td class="center" style="font-size: small;">${i.index + 1 + (page - 1) * 10}</td>
                        <td class="center date" style="font-size: small;">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="dateS" value="${sc.startedAt}" type="both"/>
                            <fmt:formatDate value="${dateS}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </td>
                        <td class="center date" style="font-size: small;">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="dateF" value="${sc.finishedAt}" type="both"/>
                            <fmt:formatDate value="${dateF}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </td>
                        <td class="center date" style="font-size: small;">
                            <a href="/admin/user/${sc.uploadedBy.id}">${sc.uploadedBy.username}</a>
                        </td>
                        <td class="center title" style="font-size: small;">
                            <fmt:formatNumber value="${sc.countPhonesForAdding}" type="number" maxFractionDigits="0"/>
                        </td>
                        <td class="center title" style="font-size: small;">
                            <fmt:formatNumber value="${sc.countPhonesAdded}" type="number" maxFractionDigits="0"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="999" style="font-size: small;">
                        <div class="pull-right">
                            <c:url value="/collection-dep/uploading-client-phones" var="urlFirstPage">
                                <c:param name="pageString" value="1"/>
                                <c:param name="dateFrom" value="${param.dateFrom}"/>
                                <c:param name="dateTo" value="${param.dateTo}"/>
                            </c:url>
                            <c:url value="/collection-dep/uploading-client-phones" var="urlLastPage">
                                <c:param name="pageString" value="${pagesCount}"/>
                                <c:param name="dateFrom" value="${param.dateFrom}"/>
                                <c:param name="dateTo" value="${param.dateTo}"/>
                            </c:url>

                            <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page == 1}">
                                        <c:url value="/collection-dep/uploading-client-phones" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="dateFrom" value="${param.dateFrom}"/>
                                            <c:param name="dateTo" value="${param.dateTo}"/>
                                        </c:url>
                                    </c:when>
                                    <c:when test="${empty page}">
                                        <c:url value="/collection-dep/uploading-client-phones" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="dateFrom" value="${param.dateFrom}"/>
                                            <c:param name="dateTo" value="${param.dateTo}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/collection-dep/uploading-client-phones" var="urlPreviousPage">
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
                                        <c:url value="/collection-dep/uploading-client-phones" var="urlNextPage">
                                            <c:param name="pageString" value="${pagesCount}"/>
                                            <c:param name="dateFrom" value="${param.dateFrom}"/>
                                            <c:param name="dateTo" value="${param.dateTo}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/collection-dep/uploading-client-phones" var="urlNextPage">
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
                            <spring:message code="common.totalCountRows"/> ${uploadingClientPhonesCount}
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
