<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 22.06.2022
  Time: 17:41
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
                <h2><spring:message code="usersAccountPage.title"/></h2>
                <h6><spring:message code="administratorPage.titleAdmin"/> / <spring:message code="usersAccountPage.title"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                    <a href="/admin/create-new-user" class="btn btn-warning">
                        <spring:message code="usersAccountPage.createNewUserButton"/>
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
                                            <spring:message code="usersAccountPage.usernameAndEmail"/>
                                        </label>
                                        <input type="text" id="username" class="form-control" name="title" value="${param.get(username)}">
                                    </div>
                                </div>
                                <div class="col-md-8" style="margin-bottom: 10px;">
                                    <div class="form-group">
                                        <label>
                                            <spring:message code="usersAccountPage.role"/>
                                        </label>
                                        <select class="chosen-select" name="role" id="role" style="font-size: larger">
                                            <option value="NaN">
                                                <spring:message code="usersAccountPage.roleSearchAllRoles"/>
                                            </option>
                                            <option value="SUPER_ADMIN">
                                                <spring:message code="SUPER_ADMIN"/>
                                            </option>
                                            <option value="ADMIN">
                                                <spring:message code="ADMIN"/>
                                            </option>
                                            <option value="ACCOUNTANT">
                                                <spring:message code="ACCOUNTANT"/>
                                            </option>
                                            <option value="COLLECTION_AGENT">
                                                <spring:message code="COLLECTION_AGENT"/>
                                            </option>
                                            <option value="COLLECTION_SUPERVISOR">
                                                <spring:message code="COLLECTION_SUPERVISOR"/>
                                            </option>
                                            <option value="CC_AGENT">
                                                <spring:message code="CC_AGENT"/>
                                            </option>
                                            <option value="CC_SUPERVISOR">
                                                <spring:message code="CC_SUPERVISOR"/>
                                            </option>
                                            <option value="UNDERWRITER_AGENT">
                                                <spring:message code="UNDERWRITER_AGENT"/>
                                            </option>
                                            <option value="UNDERWRITER_SUPERVISOR">
                                                <spring:message code="UNDERWRITER_SUPERVISOR"/>
                                            </option>
                                            <option value="MARKETER">
                                                <spring:message code="MARKETER"/>
                                            </option>
                                            <option value="ANALYST">
                                                <spring:message code="ANALYST"/>
                                            </option>
                                            <option value="ANALYST_SUPERVISOR">
                                                <spring:message code="ANALYST_SUPERVISOR"/>
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>
                                            <spring:message code="usersAccountPage.status"/>
                                        </label>
                                        <select class="chosen-select" name="status" id="status" style="font-size: larger">
                                            <option value="NaN">
                                                <spring:message code="usersAccountPage.statusSearchAllStatuses"/>
                                            </option>
                                            <option value="ACTIVE">
                                                <spring:message code="usersAccountPage.statusSearchActive"/>
                                            </option>
                                            <option value="INACTIVE">
                                                <spring:message code="usersAccountPage.statusSearchInactive"/>
                                            </option>
                                            <option value="LOCKED">
                                                <spring:message code="usersAccountPage.statusSearchLocked"/>
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <button type="submit" class="btn btn-white btn-sm">
                                <spring:message code="common.ApplyFilters"/>
                            </button>
                            <a class="btn btn-white btn-sm btn-clear-filter" href="/admin/users">
                                <spring:message code="common.ResetFilters"/>
                            </a>
                        </div>
                    </form>
                </div>
            </div>


        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${message}"/>
            </div>
        </c:if>

        <div class="col-lg-12">
            <c:if test="${pagesCount > 0}">
                <p class="text-box" style="margin-bottom: 10px">
                    <spring:message code="usersAccountPage.textCommonForDetailedInfoUser"/>
                </p>

            <table class="table table-bordered table-hover table-pageable" style="font-size: small">
                <thead>
                <tr>
                    <th class="center" style="width: 50px; font-size: small;">
                        #
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="usersAccountPage.createdAt"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="usersAccountPage.lastModifiedAt"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="usersAccountPage.usernameAndEmail"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="usersAccountPage.status"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="usersAccountPage.firstName"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="usersAccountPage.lastName"/>
                    </th>
                    <th class="center" style="font-size: small;">
                        <spring:message code="usersAccountPage.role"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="userList" items="${userAccountList}" varStatus="i">
                    <tr class="clickable" href="<c:url value="/admin/user/${userList.id}"/>">
                        <td class="center" style="font-size: small;">${i.index + 1 + (page - 1) * 10}</td>
                        <td class="center" style="font-size: small;">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAt" value="${userList.createdAt}" type="both"/>
                            <fmt:formatDate value="${createdAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </td>
                        <td class="center date" style="font-size: small;">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="lastModifiedAt" value="${userList.lastModifiedAt}" type="both"/>
                            <fmt:formatDate value="${lastModifiedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </td>
                        <td class="center title" style="font-size: small;">
                                ${userList.username}
                        </td>
                        <td class="center title-action" style="font-size: small;">
                            <c:set var="status" value="${userList.status}"/>
                            <spring:message code="${status}"/>
                        </td>
                        <td class="center title" style="font-size: small;">
                                ${userList.firstName}
                        </td>
                        <td class="center title" style="font-size: small;">
                                ${userList.lastName}
                        </td>
                        <td class="center title-action" style="font-size: small;">
                            <c:set var="roleName" value="${userList.userRole.name}"/>
                            <spring:message code="${roleName}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="999" style="font-size: small;">
                        <div class="pull-right">
                            <c:url value="/admin/users" var="urlFirstPage">
                                <c:param name="pageString" value="1"/>
                                <c:param name="title" value="${param.title}"/>
                                <c:param name="status" value="${param.status}"/>
                                <c:param name="role" value="${param.role}"/>
                            </c:url>
                            <c:url value="/admin/users" var="urlLastPage">
                                <c:param name="pageString" value="${pagesCount}"/>
                                <c:param name="title" value="${param.title}"/>
                                <c:param name="status" value="${param.status}"/>
                                <c:param name="role" value="${param.role}"/>
                            </c:url>

                            <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page == 1}">
                                        <c:url value="/admin/users" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
                                            <c:param name="role" value="${param.role}"/>
                                        </c:url>
                                    </c:when>
                                    <c:when test="${empty page}">
                                        <c:url value="/admin/users" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
                                            <c:param name="role" value="${param.role}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/admin/users" var="urlPreviousPage">
                                            <c:param name="pageString" value="${page - 1}"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
                                            <c:param name="role" value="${param.role}"/>
                                        </c:url>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                            <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page >= pagesCount}">
                                        <c:url value="/admin/users" var="urlNextPage">
                                            <c:param name="pageString" value="${pagesCount}"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
                                            <c:param name="role" value="${param.role}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/admin/users" var="urlNextPage">
                                            <c:param name="pageString" value="${page + 1}"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
                                            <c:param name="role" value="${param.role}"/>
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
                            <spring:message code="usersAccountPage.totalCountUsers"/> ${userAccountCount}
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
