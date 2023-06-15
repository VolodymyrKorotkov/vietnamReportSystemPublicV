<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 15.09.2022
  Time: 13:10
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
              <c:set var="title" value="${emailSending.title}"/>
              <h2>
                  <c:if test="${emailSending.auto == true}">
                        <spring:message code="${title}"/>
                    </c:if>
                  <c:if test="${emailSending.auto == false}">
                        ${title}
                    </c:if>
              </h2>
            <h6><spring:message code="marketingPage.marketingTitle"/> / <spring:message code="marketingPage.emailSendingList"/> / <c:if test="${emailSending.auto == true}">
                        <spring:message code="${title}"/>
                    </c:if>
                <c:if test="${emailSending.auto == false}">
                        ${title}
                    </c:if> / <spring:message code="common.view"/> </h6>
          </div>
          <div class="col-lg-5">
            <div class="title-action">
              <a href="/marketing/email-sending-list" class="btn btn-default">
                <spring:message code="common.backButton"/>
              </a>
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
          <div class="col-sm-6 col-view">
            <div class="row" style="margin-bottom: 20px;">
              <div class="col-lg-12 view-data-title">
                <spring:message code="marketingPage.titleEmailSending"/>:
              </div>
              <div class="col-lg-12 view-data-key">
                <c:if test="${emailSending.auto == true}">
                        <spring:message code="${title}"/>
                    </c:if>
                  <c:if test="${emailSending.auto == false}">
                        ${title}
                    </c:if>
              </div>
            </div>

            <div class="row" style="margin-bottom: 20px;">
              <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                <spring:message code="marketingPage.statusEmailSending"/>:
              </div>
              <div class="col-lg-12 view-data-key">
                        <span class="label label-primary" style="font-size: large">
                            <spring:message code="${emailSending.status}"/>
                        </span>
              </div>
            </div>

              <div class="row" style="margin-bottom: 20px;">
                  <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                      <spring:message code="marketingPage.autoEmailSending"/>:
                  </div>
                  <div class="col-lg-12 view-data-key">
                        <c:choose>
                            <c:when test="${emailSending.auto == true}">
                                ✓
                            </c:when>
                            <c:otherwise>
                                ×
                            </c:otherwise>
                        </c:choose>
                  </div>
              </div>

              <div class="row" style="margin-bottom: 20px;">
                  <div class="col-lg-12 view-data-title">
                      <spring:message code="marketingPage.startedAt"/>:
                  </div>
                  <div class="col-lg-12 view-data-key">
                      <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="startedAt" value="${emailSending.startedAt}" type="both"/>
                      <fmt:formatDate value="${startedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                  </div>
              </div>

          </div>

          <div class="col-sm-6 col-view">

              <div class="row" style="margin-bottom: 20px;">
                  <div class="col-lg-12 view-data-title">
                      <spring:message code="marketingPage.totalEmailCount"/>:
                  </div>
                  <div class="col-lg-12 view-data-key">
                      <fmt:formatNumber value="${emailSending.countEmailsTotal}" type="number" maxFractionDigits="0"/>
                  </div>
              </div>

              <div class="row" style="margin-bottom: 20px;">
                  <div class="col-lg-12 view-data-title">
                      <spring:message code="marketingPage.sentEmailCount"/>:
                  </div>
                  <div class="col-lg-12 view-data-key">
                      <fmt:formatNumber value="${emailSending.countEmailsSent}" type="number" maxFractionDigits="0"/>
                  </div>
              </div>

              <div class="row" style="margin-bottom: 20px;">
                  <div class="col-lg-12 view-data-title">
                      <spring:message code="marketingPage.notSentEmailCount"/>:
                  </div>
                  <div class="col-lg-12 view-data-key">
                      <fmt:formatNumber value="${emailSending.countEmailsNotSent}" type="number" maxFractionDigits="0"/>
                  </div>
              </div>

              <div class="row" style="margin-bottom: 20px;">
                  <div class="col-lg-12 view-data-title">
                      <spring:message code="marketingPage.completedAt"/>:
                  </div>
                  <div class="col-lg-12 view-data-key">
                      <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="completedAt" value="${emailSending.completedAt}" type="both"/>
                      <fmt:formatDate value="${completedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                  </div>
              </div>

          </div>

        </div>
    </jsp:attribute>
</mytag:header-template>

</html>
