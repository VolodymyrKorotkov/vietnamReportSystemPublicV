<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 28.07.2022
  Time: 14:41
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
                <h2><spring:message code="myBonusPage.title"/></h2>
                <h6><spring:message code="myBonusPage.title"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
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
                <spring:message code="myBonusPage.textUnavailableBonusForRole"/>
            </p>

        </div>





    </jsp:attribute>
</mytags:header-template>

</html>
