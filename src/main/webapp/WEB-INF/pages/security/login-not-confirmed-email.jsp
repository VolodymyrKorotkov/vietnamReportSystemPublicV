<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 20.06.2022
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><spring:message code = "company.name"/></title>

    <link href="${pageContext.request.contextPath}/res/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/font-awesome/font-awesome.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/res/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/style.css" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="row border-bottom">
    <div>
        <nav class="navbar navbar-static-top gray-bg-forced" role="navigation" style="margin-bottom: 0px;">
            <ul class="nav navbar-header navbar-top-links" style="margin-left: 50px;">
                <li class="text-muted">
                    <a href="<%=request.getContextPath()%>?languageVar=en">English</a>
                </li>
                <li class="text-muted">
                    <span>|</span>
                </li>
                <li class="text-muted">
                    <a href="<%=request.getContextPath()%>?languageVar=ru">Русский</a>
                </li>
                <li class="text-muted">
                    <span>|</span>
                </li>
                <li class="text-muted">
                    <a href="<%=request.getContextPath()%>?languageVar=vn">Tiếng Việt</a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div style="margin-top: 100px;">

        <h1 class="logo-name" style="position: relative; left: -15px; top: -40px; font-size: 70px; font-weight: 600;">
            <spring:message code="confirmingEmailPage.confirmingEmailHead"/>
        </h1>

        <form action="/security/login" class="m-t" role="form" method="get">
            <p><spring:message code = "confirmingEmailPage.confirmingEmailText"/></p>
            <br/>
            <button type="submit" class="btn btn-primary block full-width m-b">
                <spring:message code = "loginPage.loginButton"/>
            </button>
        </form>


        <p class="m-t">
            <small><spring:message code="company.name"/> &copy; 2022</small>
        </p>
    </div>
</div>



<%--<!-- Mainly scripts -->--%>
<script src="${pageContext.request.contextPath}/res/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>

</body>

</html>
