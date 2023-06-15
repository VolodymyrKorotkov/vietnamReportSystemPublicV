<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 20.06.2022
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<mytag:header-template>
    <jsp:attribute name="contentPage">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-7">
                <h2><spring:message code="welcomePage.welcomeTitle"/></h2>
                <h6><spring:message code="welcomePage.welcomeTitle"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action"></div>
            </div>
        </div>
        <div class="col-lg-12" style="margin-top: 20px;">
            <p class="text-box" style="margin-bottom: 20px;">
                <spring:message code="welcomePage.welcomeText"/>
            </p>
        </div>
    </jsp:attribute>
</mytag:header-template>

</html>
