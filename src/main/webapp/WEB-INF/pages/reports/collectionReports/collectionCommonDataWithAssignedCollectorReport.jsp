<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 19.07.2022
  Time: 18:33
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
                <h2><spring:message code="COLLECTION_COMMON_DATA_WITH_ASSIGNED_COLLECTOR_REPORT"/></h2>
                <h6><spring:message code="reportsPage.reportsPageTitle"/> / <spring:message code="COLLECTION_COMMON_DATA_WITH_ASSIGNED_COLLECTOR_REPORT"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/reports" class="btn btn-default">
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

        <div class="col-lg-12">
            <p class="text-box" style="margin-bottom: 20px;">
                <spring:message code="collectionReportCommonDataWithAssigned.welcomeText"/>
            </p>

            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form">

                <div class="clearfix"></div>
                <div class="hr-line-dashed"></div>
                <div class="col-lg-6">
                    <div class="form-group">
                        <div class="col-sm-8 col-sm-offset-4">
                            <button class="btn btn-primary" type="submit"><spring:message code="reportsPage.exportReportButton"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>




        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
