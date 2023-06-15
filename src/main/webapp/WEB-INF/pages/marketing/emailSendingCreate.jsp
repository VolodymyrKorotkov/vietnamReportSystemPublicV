<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 23.09.2022
  Time: 15:21
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
                <h2><spring:message code="marketingPage.titleCreatingNewEmailSending"/></h2>
                <h6><spring:message code="marketingPage.marketingTitle"/> / <spring:message code="marketingPage.templatesEmailSending"/> / ${templateEmailSending.title} / <spring:message code="marketingPage.titleCreatingNewEmailSending"/> </h6>
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

        <div class="text-box" style="margin-bottom: 20px;">
            <spring:message code="marketingPage.textCreatingNewEmailSending"/>: <strong>${templateEmailSending.title}</strong>
        </div>

        <div class="col-lg-12" style="margin-bottom: 20px;">

            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="emailSending">
                <div class="clearfix">

                    <div class="col-lg-6">
                        <div class="form-group">
                            <label for="title" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.titleEmailSending"/>:
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
                            <label for="plannedAt" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.plannedAt"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="plannedAt" type="datetime-local" cssClass="form-control not-empty required" id="plannedAt" required="" ></form:input>
                                <form:errors path="plannedAt"></form:errors>
                                <c:if test="${not empty plannedAtError}">
                                    <p class="help-block error text-danger"><spring:message code="${plannedAtError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="allActiveClientsWithoutExpiredLoans" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.activeClients"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:checkbox path="allActiveClientsWithoutExpiredLoans" cssClass="form-control" id="allActiveClientsWithoutExpiredLoans"></form:checkbox>
                                <form:errors path="allActiveClientsWithoutExpiredLoans"></form:errors>
                                <c:if test="${not empty allActiveClientsWithoutExpiredLoansError}">
                                    <p class="help-block error text-danger"><spring:message code="${allActiveClientsWithoutExpiredLoansError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="repeatPassiveClients" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.passiveClients"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:checkbox path="repeatPassiveClients" cssClass="form-control" id="repeatPassiveClients"></form:checkbox>
                                <form:errors path="repeatPassiveClients"></form:errors>
                                <c:if test="${not empty repeatPassiveClientsError}">
                                    <p class="help-block error text-danger"><spring:message code="${repeatPassiveClientsError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="repeatPassiveClientsDaysFrom" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.passiveClientsDaysFrom"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="repeatPassiveClientsDaysFrom" type="number" min="0" step="1" cssClass="form-control" id="repeatPassiveClientsDaysFrom" maxlength="100"></form:input>
                                <form:errors path="repeatPassiveClientsDaysFrom"></form:errors>
                                <c:if test="${not empty repeatPassiveClientsDaysFromError}">
                                    <p class="help-block error text-danger"><spring:message code="${repeatPassiveClientsDaysFromError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="repeatPassiveClientsDaysTo" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.passiveClientsDaysTo"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="repeatPassiveClientsDaysTo" type="number" min="0" step="1" cssClass="form-control" id="repeatPassiveClientsDaysTo" maxlength="100"></form:input>
                                <form:errors path="repeatPassiveClientsDaysTo"></form:errors>
                                <c:if test="${not empty repeatPassiveClientsDaysToError}">
                                    <p class="help-block error text-danger"><spring:message code="${repeatPassiveClientsDaysToError}"/></p>
                                </c:if>
                            </div>
                        </div>

                    </div>

                    <div class="col-lg-6">

                        <div class="form-group">
                            <label for="clientsWithExpiredLoan" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.expiredClients"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:checkbox path="clientsWithExpiredLoan" cssClass="form-control" id="clientsWithExpiredLoan"></form:checkbox>
                                <form:errors path="clientsWithExpiredLoan"></form:errors>
                                <c:if test="${not empty clientsWithExpiredLoanError}">
                                    <p class="help-block error text-danger"><spring:message code="${clientsWithExpiredLoanError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="clientsWithExpiredLoanDaysFrom" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.expiredClientsDaysFrom"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="clientsWithExpiredLoanDaysFrom" type="number" min="0" step="1" cssClass="form-control" id="clientsWithExpiredLoanDaysFrom" maxlength="100"></form:input>
                                <form:errors path="clientsWithExpiredLoanDaysFrom"></form:errors>
                                <c:if test="${not empty clientsWithExpiredLoanDaysFromError}">
                                    <p class="help-block error text-danger"><spring:message code="${clientsWithExpiredLoanDaysFromError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="clientsWithExpiredLoanDaysTo" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.expiredClientsDaysTo"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="clientsWithExpiredLoanDaysTo" type="number" min="0" step="1" cssClass="form-control" id="clientsWithExpiredLoanDaysTo" maxlength="100"></form:input>
                                <form:errors path="clientsWithExpiredLoanDaysTo"></form:errors>
                                <c:if test="${not empty clientsWithExpiredLoanDaysToError}">
                                    <p class="help-block error text-danger"><spring:message code="${clientsWithExpiredLoanDaysToError}"/></p>
                                </c:if>
                            </div>
                        </div>



                        <div class="form-group">
                            <label for="registeredClientsWithoutApplications" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.clientsWithoutApp"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:checkbox path="registeredClientsWithoutApplications" cssClass="form-control" id="registeredClientsWithoutApplications"></form:checkbox>
                                <form:errors path="registeredClientsWithoutApplications"></form:errors>
                                <c:if test="${not empty registeredClientsWithoutApplicationsError}">
                                    <p class="help-block error text-danger"><spring:message code="${registeredClientsWithoutApplicationsError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="registeredClientsWithoutApplicationsDaysFrom" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.clientsWithoutAppDaysFrom"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="registeredClientsWithoutApplicationsDaysFrom" type="number" min="0" step="1" cssClass="form-control" id="registeredClientsWithoutApplicationsDaysFrom" maxlength="100"></form:input>
                                <form:errors path="registeredClientsWithoutApplicationsDaysFrom"></form:errors>
                                <c:if test="${not empty registeredClientsWithoutApplicationsDaysFromError}">
                                    <p class="help-block error text-danger"><spring:message code="${registeredClientsWithoutApplicationsDaysFromError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="registeredClientsWithoutApplicationsDaysTo" class="col-sm-4 control-label">
                                <spring:message code="marketingPage.clientsWithoutAppDaysTo"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="registeredClientsWithoutApplicationsDaysTo" type="number" min="0" step="1" cssClass="form-control" id="registeredClientsWithoutApplicationsDaysTo" maxlength="100"></form:input>
                                <form:errors path="registeredClientsWithoutApplicationsDaysTo"></form:errors>
                                <c:if test="${not empty registeredClientsWithoutApplicationsDaysToError}">
                                    <p class="help-block error text-danger"><spring:message code="${registeredClientsWithoutApplicationsDaysToError}"/></p>
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
