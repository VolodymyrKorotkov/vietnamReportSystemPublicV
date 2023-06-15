<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 27.06.2022
  Time: 13:04
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
                <h2><spring:message code="reportsPage.creatingNewPlanTitle"/></h2>
                <h6><spring:message code="reportsPage.reportsPageTitle"/> / <spring:message code="reportsPage.titleModelPlans"/> / <spring:message code="reportsPage.creatingNewPlanTitle"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/reports/model-plans" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>
        </div>

        <div class="col-lg-12" style="margin-bottom: 20px;">
            <p class="text-box" style="margin-bottom: 20px;">
                <spring:message code="reportsPage.modelPlanTextUnderChooseDate"/>
            </p>
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="modelPlan">
                <div class="clearfix">

                    <div class="col-lg-6">

                        <div class="form-group">
                            <label for="dateMonth" class="col-sm-6 control-label">
                                <spring:message code="reportsPage.modelPlanForDateMoth"/>
                            </label>
                            <div class="col-sm-6">
                                <div class="input-group date">
                                    <span class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </span>
                                    <form:input path="dateMonth" type="date" cssClass="form-control date-mask" id="dateMonth" required=""></form:input>
                                    <form:errors path="dateMonth"></form:errors>
                                    <c:if test="${not empty dateMonthError}">
                                    <p class="help-block error text-danger"><spring:message code="${dateMonthError}"/></p>
                                    </c:if>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="issuedAmountNew" class="col-sm-6 control-label">
                                <spring:message code="reportsPage.modelPlanIssuedAmountNew"/>:
                            </label>
                            <div class="col-sm-6">
                                <form:input path="issuedAmountNew" type="number" min="0" step="any" cssClass="form-control not-empty required" id="issuedAmountNew" required="" maxlength="100"></form:input>
                                <form:errors path="issuedAmountNew"></form:errors>
                                <c:if test="${not empty issuedAmountNewError}">
                                    <p class="help-block error text-danger"><spring:message code="${issuedAmountNewError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="issuedAmountRepeat" class="col-sm-6 control-label">
                                <spring:message code="reportsPage.modelPlanIssuedAmountRepeat"/>:
                            </label>
                            <div class="col-sm-6">
                                <form:input path="issuedAmountRepeat" type="number" min="0" step="any" cssClass="form-control not-empty required" id="issuedAmountRepeat" required="" maxlength="100"></form:input>
                                <form:errors path="issuedAmountRepeat"></form:errors>
                                <c:if test="${not empty issuedAmountRepeatError}">
                                    <p class="help-block error text-danger"><spring:message code="${issuedAmountRepeatError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="prolongedAmount" class="col-sm-6 control-label">
                                <spring:message code="reportsPage.modelPlanProlongedAmountTotal"/>:
                            </label>
                            <div class="col-sm-6">
                                <form:input path="prolongedAmount" type="number" min="0" step="any" cssClass="form-control not-empty required" id="prolongedAmount" required="" maxlength="100"></form:input>
                                <form:errors path="prolongedAmount"></form:errors>
                                <c:if test="${not empty prolongedAmountError}">
                                    <p class="help-block error text-danger"><spring:message code="${prolongedAmountError}"/></p>
                                </c:if>
                            </div>
                        </div>

                    </div>

                    <div class="col-lg-6">

                        <div class="form-group">
                            <label for="repaymentPrincipalAmountNew" class="col-sm-6 control-label">
                                <spring:message code="reportsPage.modelPlanRepaymentPrincipalNew"/>:
                            </label>
                            <div class="col-sm-6">
                                <form:input path="repaymentPrincipalAmountNew" type="number" min="0" step="any" cssClass="form-control not-empty required" id="repaymentPrincipalAmountNew" required="" maxlength="100"></form:input>
                                <form:errors path="repaymentPrincipalAmountNew"></form:errors>
                                <c:if test="${not empty repaymentPrincipalAmountNewError}">
                                    <p class="help-block error text-danger"><spring:message code="${repaymentPrincipalAmountNewError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="repaymentPrincipalAmountRepeat" class="col-sm-6 control-label">
                                <spring:message code="reportsPage.modelPlanRepaymentPrincipalRepeat"/>:
                            </label>
                            <div class="col-sm-6">
                                <form:input path="repaymentPrincipalAmountRepeat" type="number" min="0" step="any" cssClass="form-control not-empty required" id="repaymentPrincipalAmountRepeat" required="" maxlength="100"></form:input>
                                <form:errors path="repaymentPrincipalAmountRepeat"></form:errors>
                                <c:if test="${not empty repaymentPrincipalAmountRepeatError}">
                                    <p class="help-block error text-danger"><spring:message code="${repaymentPrincipalAmountRepeatError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="repaymentIncomeAmountNew" class="col-sm-6 control-label">
                                <spring:message code="reportsPage.modelPlanRepaymentIncomeNew"/>:
                            </label>
                            <div class="col-sm-6">
                                <form:input path="repaymentIncomeAmountNew" type="number" min="0" step="any" cssClass="form-control not-empty required" id="repaymentIncomeAmountNew" required="" maxlength="100"></form:input>
                                <form:errors path="repaymentIncomeAmountNew"></form:errors>
                                <c:if test="${not empty repaymentIncomeAmountNewError}">
                                    <p class="help-block error text-danger"><spring:message code="${repaymentIncomeAmountNewError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="repaymentIncomeAmountRepeat" class="col-sm-6 control-label">
                                <spring:message code="reportsPage.modelPlanRepaymentIncomeRepeat"/>:
                            </label>
                            <div class="col-sm-6">
                                <form:input path="repaymentIncomeAmountRepeat" type="number" min="0" step="any" cssClass="form-control not-empty required" id="repaymentIncomeAmountRepeat" required="" maxlength="100"></form:input>
                                <form:errors path="repaymentIncomeAmountRepeat"></form:errors>
                                <c:if test="${not empty repaymentIncomeAmountRepeatError}">
                                    <p class="help-block error text-danger"><spring:message code="${repaymentIncomeAmountRepeatError}"/></p>
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
                            <a class="btn btn-white btn-back" href="/reports/model-plans"><spring:message code="common.cancelButton"/></a>
                            <button class="btn btn-primary" type="submit"><spring:message code="common.saveButton"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>



    </jsp:attribute>
</mytags:header-template>

</html>
