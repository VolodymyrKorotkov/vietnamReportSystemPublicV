<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 20.06.2022
  Time: 19:57
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
            <h2><spring:message code="myProfilePage.myProfile"/></h2>
            <h6><spring:message code="myProfilePage.myProfile"/> / <spring:message code="myProfilePage.profile"/> / <spring:message code="common.view"/> </h6>
          </div>
          <div class="col-lg-5">
            <div class="title-action">
              <a href="/" class="btn btn-default">
                <spring:message code="common.backButton"/>
              </a>
              <div class="btn-group">
                <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                  <spring:message code="common.actionsButton"/> <span class="caret"></span>
                </button>
                <ul class="dropdown-menu dropdown-menu-right">
                  <li>
                    <a href="/myprofile/profile/change-password">
                      <spring:message code="myProfilePage.changePasswordButton"/>
                    </a>
                  </li>
                </ul>
              </div>
            </div>

          </div>

        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissable">
              <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
              <spring:message code="${message}"/>

            </div>
        </c:if>

        <div class="col-lg-12" style="margin-bottom: 20px;">
          <div class="col-sm-6 col-view">
            <div class="row" style="margin-bottom: 10px;">
              <div class="col-lg-12 view-data-title">
                <spring:message code="myProfilePage.usernameAndEmail"/>
              </div>
              <div class="col-lg-12 view-data-key">
                  ${userAccount.username}
              </div>
            </div>

            <div class="row" style="margin-bottom: 10px;">
              <div class="col-lg-12 view-data-title">
                <spring:message code="myProfilePage.firstName"/>
              </div>
              <div class="col-lg-12 view-data-key">
                <c:set var="firstName" value="${userAccount.firstName}"/>
                <c:if test="${empty firstName}">-</c:if>
                <c:if test="${not empty firstName}">
                            ${firstName}
                        </c:if>
              </div>
            </div>

            <div class="row" style="margin-bottom: 10px;">
              <div class="col-lg-12 view-data-title">
                <spring:message code="myProfilePage.lastName"/>
              </div>
              <div class="col-lg-12 view-data-key">
                <c:set var="lastName" value="${userAccount.lastName}"/>
                <c:if test="${empty lastName}">-</c:if>
                <c:if test="${not empty lastName}">
                            ${lastName}
                        </c:if>
              </div>
            </div>
          </div>

          <div class="col-sm-6 col-view">
            <div class="row" style="margin-bottom: 10px;">
              <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                <spring:message code="myProfilePage.passwordExpiredAt"/>
              </div>
              <div class="col-lg-12 view-data-key">
                <c:set var="passwordExpiredAt" value="${userAccount.passwordExpiredAt}"/>
                <c:if test="${empty passwordExpiredAt}">∞</c:if>
                <c:if test="${not empty passwordExpiredAt}">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="passExpAt" value="${passwordExpiredAt}" type="both"/>
                            <span class="label label-danger">
                                <fmt:formatDate value="${passExpAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </span>
                        </c:if>
              </div>
            </div>

            <div class="row" style="margin-bottom: 10px;">
              <div class="col-lg-12 view-data-title">
                <spring:message code="myProfilePage.gender"/>
              </div>
              <div class="col-lg-12 view-data-key">
                <c:set var="gender" value="${userAccount.gender}"/>
                <c:if test="${empty gender}">-</c:if>
                <c:if test="${not empty gender}">
                            <spring:message code="${gender}"/>
                        </c:if>
              </div>
            </div>

              <div class="row" style="margin-bottom: 20px;">
                  <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                      <spring:message code="myProfilePage.role"/>
                  </div>
                  <div class="col-lg-12 view-data-key">
                        <span class="label label-primary" style="font-size: large">
                            <spring:message code="${userAccount.userRole.name}"/>
                        </span>
                  </div>
              </div>

          </div>

        </div>
    </jsp:attribute>
</mytag:header-template>

</html>
