<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 28.10.2022
  Time: 11:25
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
                <h2><spring:message code="admin.systemSettings"/></h2>
                <h6><spring:message code="administratorPage.titleAdmin"/> / <spring:message code="admin.systemSettings"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>


        </div>

        <div class="col-lg-12" style="margin-bottom: 20px;">
            <c:if test="${pagesCount > 0}">
                <table class="table table-bordered table-hover table-pageable" style="font-size: small">
                    <thead>
                    <tr>
                        <th class="center" style="width: 50px; font-size: small;">
                            #
                        </th>
                        <th class="center" style="font-size: small;">
                            <spring:message code="common.title"/>
                        </th>
                        <th class="center" style="font-size: small;">
                            <spring:message code="common.value"/>
                        </th>
                        <th class="center" style="font-size: small;">
                            <spring:message code="reportsPage.lastModifiedAt"/>
                        </th>
                        <th class="center" style="font-size: small;">
                            <spring:message code="reportsPage.lastModifiedBy"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${systemSettingList}" varStatus="i">
                    <tr class="clickable" href="<c:url value="/admin/system-settings/${item.id}"/>">
                        <td class="center" style="font-size: small;">${i.index + 1 + (page - 1) * 10}</td>
                        <td class="center title" style="font-size: small;">
                            <spring:message code="${item.title}"/>
                        </td>
                        <td class="center title" style="font-size: small;">
                                ${item.value}
                        </td>
                        <td class="center" style="font-size: small;">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="date" value="${item.modifiedAt}" type="both"/>
                            <fmt:formatDate value="${date}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </td>
                        <td class="center title" style="font-size: small;">
                                ${item.modifiedBy.username}
                        </td>
                    </tr>
                </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="999" style="font-size: small;">
                            <div class="pull-right">
                            <c:url value="/admin/system-settings" var="urlFirstPage">
                                <c:param name="pageString" value="1"/>
                                <%--                                <c:param name="dateFrom" value="${param.dateFrom}"/>--%>
                                <%--                                <c:param name="dateTo" value="${param.dateTo}"/>--%>
                            </c:url>
                                <c:url value="/admin/system-settings" var="urlLastPage">
                                <c:param name="pageString" value="${pagesCount}"/>
                                    <%--                                <c:param name="dateFrom" value="${param.dateFrom}"/>--%>
                                    <%--                                <c:param name="dateTo" value="${param.dateTo}"/>--%>
                            </c:url>

                                <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page == 1}">
                                        <c:url value="/admin/system-settings" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <%--                                            <c:param name="dateFrom" value="${param.dateFrom}"/>--%>
                                            <%--                                            <c:param name="dateTo" value="${param.dateTo}"/>--%>
                                        </c:url>
                                    </c:when>
                                    <c:when test="${empty page}">
                                        <c:url value="/admin/system-settings" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <%--                                            <c:param name="dateFrom" value="${param.dateFrom}"/>--%>
                                            <%--                                            <c:param name="dateTo" value="${param.dateTo}"/>--%>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/admin/system-settings" var="urlPreviousPage">
                                            <c:param name="pageString" value="${page - 1}"/>
                                            <%--                                            <c:param name="dateFrom" value="${param.dateFrom}"/>--%>
                                            <%--                                            <c:param name="dateTo" value="${param.dateTo}"/>--%>
                                        </c:url>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                                <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page >= pagesCount}">
                                        <c:url value="/admin/system-settings" var="urlNextPage">
                                            <c:param name="pageString" value="${pagesCount}"/>
                                            <%--                                            <c:param name="dateFrom" value="${param.dateFrom}"/>--%>
                                            <%--                                            <c:param name="dateTo" value="${param.dateTo}"/>--%>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/admin/system-settings" var="urlNextPage">
                                            <c:param name="pageString" value="${page + 1}"/>
                                            <%--                                            <c:param name="dateFrom" value="${param.dateFrom}"/>--%>
                                            <%--                                            <c:param name="dateTo" value="${param.dateTo}"/>--%>
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
                                <spring:message code="common.totalCountRows"/> ${systemSettingsCount}
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
