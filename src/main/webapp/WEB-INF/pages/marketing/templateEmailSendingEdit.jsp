<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 23.09.2022
  Time: 11:25
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
                <h2><spring:message code="marketingPage.titleEditingTemplate"/> / ${templateEmailSending.id}</h2>
                <h6><spring:message code="marketingPage.marketingTitle"/> / <spring:message code="marketingPage.templatesEmailSending"/> / ${templateEmailSending.id} / <spring:message code="common.edit"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/marketing/template-email-sending-list/${templateEmailSending.id}" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${not empty messageBad}">
            <div class="alert alert-danger alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${messageBad}"/>
            </div>
        </c:if>

        <div class="col-lg-12" style="margin-bottom: 20px;">
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="templateEmailSending">
                <div class="clearfix">

                    <div class="col-lg-6">
                        <div class="form-group">
                            <label for="title" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.templateEmailSendingTitle"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="title" type="text" cssClass="form-control not-empty required" id="title" required="" ></form:input>
                                <form:errors path="title"></form:errors>
                                <c:if test="${not empty titleError}">
                                    <p class="help-block error text-danger"><spring:message code="${titleError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="description" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.templateEmailSendingDescription"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:textarea path="description" cssClass="form-control not-empty required" id="description"></form:textarea>
                                <form:errors path="description"></form:errors>
                                <c:if test="${not empty descriptionError}">
                                    <p class="help-block error text-danger"><spring:message code="${descriptionError}"/></p>
                                </c:if>
                            </div>
                        </div>

                    </div>

                    <div class="col-lg-6">

                        <div class="form-group">
                            <label for="subjectEmail" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.templateEmailSendingSubjectEmail"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="subjectEmail" type="text" cssClass="form-control not-empty required" id="subjectEmail" required="" ></form:input>
                                <form:errors path="subjectEmail"></form:errors>
                                <c:if test="${not empty subjectEmailError}">
                                    <p class="help-block error text-danger"><spring:message code="${subjectEmailError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="bannerLink" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.templateEmailSendingBannerLink"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="bannerLink" type="text" cssClass="form-control not-empty required" id="bannerLink" required=""></form:input>
                                <form:errors path="bannerLink"></form:errors>
                                <c:if test="${not empty bannerLinkError}">
                                    <p class="help-block error text-danger"><spring:message code="${bannerLinkError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="linkForClick" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.templateEmailSendingLinkForClick"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="linkForClick" type="text" cssClass="form-control not-empty required" id="linkForClick" required=""></form:input>
                                <form:errors path="linkForClick"></form:errors>
                                <c:if test="${not empty linkForClickError}">
                                    <p class="help-block error text-danger"><spring:message code="${linkForClickError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="buttonEmailText" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.templateEmailSendingButtonEmail"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="buttonEmailText" type="text" cssClass="form-control not-empty required" id="buttonEmailText" required="" ></form:input>
                                <form:errors path="buttonEmailText"></form:errors>
                                <c:if test="${not empty buttonEmailTextError}">
                                    <p class="help-block error text-danger"><spring:message code="${buttonEmailTextError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="bodyEmailTitle" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.templateEmailSendingTitleEmail"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="bodyEmailTitle" type="text" cssClass="form-control not-empty required" id="bodyEmailTitle" required="" ></form:input>
                                <form:errors path="bodyEmailTitle"></form:errors>
                                <c:if test="${not empty bodyEmailTitleError}">
                                    <p class="help-block error text-danger"><spring:message code="${bodyEmailTitleError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="bodyEmailText" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.templateEmailSendingTextEmail"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:textarea path="bodyEmailText" cssClass="form-control not-empty required" id="bodyEmailText"></form:textarea>
                                <form:errors path="bodyEmailText"></form:errors>
                                <c:if test="${not empty bodyEmailTextError}">
                                    <p class="help-block error text-danger"><spring:message code="${bodyEmailTextError}"/></p>
                                </c:if>
                            </div>
                        </div>

                    </div>




                </div>

                <div class="clearfix"></div>
                <div class="hr-line-dashed"></div>
                <div class="col-lg-6">
                    <div class="form-group">
                        <div class="col-sm-8 col-sm-offset-4">
                            <a class="btn btn-white btn-back" href="/marketing/template-email-sending-list/${templateEmailSending.id}"><spring:message code="common.cancelButton"/></a>
                            <button class="btn btn-primary" type="submit"><spring:message code="common.saveButton"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>



    </jsp:attribute>
</mytags:header-template>

</html>
