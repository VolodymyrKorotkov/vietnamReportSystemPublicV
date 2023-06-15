<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 04.07.2022
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><spring:message code="company.name"/></title>

    <link href="<c:url value="/res/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/res/css/font-awesome/font-awesome.css"/>" rel="stylesheet">

    <link href="<c:url value="/res/css/animate.css"/>" rel="stylesheet">
    <link href="<c:url value="/res/css/style.css"/>" rel="stylesheet">

</head>

<body class="gray-bg">

<div class="middle-box text-center animated fadeInDown">
    <h2><spring:message code="company.name"/></h2>
    <h3 class="font-bold"><spring:message code="reportPage.commonReportCompletedTitle"/></h3>

    <div class="error-desc">
        <spring:message code="reportPage.commonReportCompletedText"/>
        <form:form method="post" action="${urlPost}" cssClass="form-inline m-t" role="form" modelAttribute="reportObject">
            <a href="<c:url value="${urlBack}"/>" class="btn btn-danger"><spring:message code="reportPage.commonBackToReportButton"/></a>
            <button class="btn btn-primary" type="submit"><spring:message code="reportPage.commonReportExportButton"/></button>
        </form:form>
    </div>
</div>


<script type="text/javascript" src="/res/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/res/js/bootstrap.min.js"></script>

</body>

</html>
