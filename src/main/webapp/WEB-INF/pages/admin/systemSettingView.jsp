<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 28.10.2022
  Time: 12:00
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
                <h2><spring:message code="${systemSetting.title}"/> / <spring:message code="common.view"/> </h2>
                <h6><spring:message code="administratorPage.titleAdmin"/> / <spring:message code="admin.systemSettings"/> / <spring:message code="${systemSetting.title}"/> / <spring:message code="common.view"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/admin/system-settings" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                            <spring:message code="common.actionsButton"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a href="/admin/system-settings/${systemSetting.id}/edit">
                                    <spring:message code="common.editButton"/>
                                </a>
                            </li>
                        </ul>
                    </div>
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

        <div class="col-lg-12" style="margin-bottom: 20px;">
            <div class="col-sm-6 col-view">
                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="common.title"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            <spring:message code="${systemSetting.title}"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="common.value"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        ${systemSetting.value}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="common.type"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${systemSetting.type}
                    </div>
                </div>
            </div>

            <div class="col-sm-6 col-view">

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.lastModifiedAt"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="lastModifiedAt" value="${systemSetting.modifiedAt}"/>
                        <c:if test="${empty lastModifiedAt}">∞</c:if>
                        <c:if test="${not empty lastModifiedAt}">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="modifiedAt" value="${lastModifiedAt}" type="both"/>
                            <span>
                                <fmt:formatDate value="${modifiedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </span>
                        </c:if>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="reportsPage.lastModifiedBy"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <a href="/admin/user/${systemSetting.modifiedBy.id}">${systemSetting.modifiedBy.username}</a>
                    </div>
                </div>
            </div>

        </div>
    </jsp:attribute>
</mytag:header-template>

</html>
