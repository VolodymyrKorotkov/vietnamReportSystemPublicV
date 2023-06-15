<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 23.09.2022
  Time: 09:50
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
                <h2>${templateEmailSending.title}</h2>
                <h6><spring:message code="marketingPage.marketingTitle"/> / <spring:message code="marketingPage.templatesEmailSending"/> / ${templateEmailSending.title} / <spring:message code="common.view"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/marketing/template-email-sending-list" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>

                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                            <spring:message code="common.actionsButton"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a href="/marketing/template-email-sending-list/${templateEmailSending.id}/test-email-sending">
                                    <spring:message code="marketingPage.testEmailSendingButton"/>
                                </a>
                            </li>
                            <li>
                                <a href="/marketing/template-email-sending-list/${templateEmailSending.id}/create-new-email-sending">
                                    <spring:message code="marketingPage.createNewPlannedEmailSendingButton"/>
                                </a>
                            </li>
                            <li>
                                <a href="/marketing/template-email-sending-list/${templateEmailSending.id}/edit">
                                    <spring:message code="common.editButton"/>
                                </a>
                            </li>
                            <li>
                                <a href="/marketing/template-email-sending-list/${templateEmailSending.id}/delete">
                                    <spring:message code="common.deleteButton"/>
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
                        <spring:message code="marketingPage.templateEmailSendingTitle"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        ${templateEmailSending.title}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.templateEmailSendingDescription"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${templateEmailSending.description}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.createdAt"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAt" value="${templateEmailSending.createdAt}" type="both"/>
                        <fmt:formatDate value="${createdAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.modifiedAt"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="modifiedAt" value="${templateEmailSending.lastModifiedAt}" type="both"/>
                        <fmt:formatDate value="${modifiedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.createdBy"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${templateEmailSending.createdBy.username}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.modifiedBy"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${templateEmailSending.lastModifiedBy.username}
                    </div>
                </div>

            </div>

            <div class="col-sm-6 col-view">

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.templateEmailSendingSubjectEmail"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${templateEmailSending.subjectEmail}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.templateEmailSendingTitleEmail"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${templateEmailSending.bodyEmailTitle}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.templateEmailSendingTextEmail"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${templateEmailSending.bodyEmailText}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.templateEmailSendingButtonEmail"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${templateEmailSending.buttonEmailText}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.templateEmailSendingLinkForClick"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${templateEmailSending.linkForClick}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="marketingPage.templateEmailSendingBannerLink"/>:
                    </div>
                    <div class="col-lg-12 view-data-key">
                            ${templateEmailSending.bannerLink}
                    </div>
                </div>

            </div>

        </div>
    </jsp:attribute>
</mytag:header-template>

</html>
