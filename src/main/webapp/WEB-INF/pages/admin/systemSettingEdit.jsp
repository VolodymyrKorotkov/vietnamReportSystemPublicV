<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 28.10.2022
  Time: 14:52
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
                <h2><spring:message code="${systemSetting.title}"/> / <spring:message code="common.edit"/></h2>
                <h6><spring:message code="administratorPage.titleAdmin"/> / <spring:message code="admin.systemSettings"/> / <spring:message code="${systemSetting.title}"/> / <spring:message code="common.edit"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/admin/system-settings/${systemSetting.id}" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>
        </div>

<%--        <c:if test="${not empty message}">--%>
<%--            <div class="alert alert-danger alert-dismissable">--%>
<%--                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>--%>
<%--                <spring:message code="${message}"/>--%>
<%--            </div>--%>
<%--        </c:if>--%>

        <div class="col-lg-12" style="margin-bottom: 20px;">
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="systemSetting">
                <div class="clearfix">

                    <div class="col-lg-6">

                        <div class="form-group">
                            <label for="value" class="col-sm-4 control-label">
                                <spring:message code="common.value"/>:
                            </label>
                            <div class="col-sm-8">
                                <form:input path="value" type="text" cssClass="form-control not-empty required" id="value" required="" maxlength="100"></form:input>
                                <form:errors path="value"></form:errors>
                                <c:if test="${not empty valueError}">
                                    <p class="help-block error text-danger"><spring:message code="${valueError}"/></p>
                                </c:if>
                            </div>
                        </div>


                    </div>

                    <div class="col-lg-6">

                    </div>




                </div>

                <div class="clearfix"></div>
                <div class="hr-line-dashed"></div>
                <div class="col-lg-6">
                    <div class="form-group">
                        <div class="col-sm-8 col-sm-offset-4">
                            <a class="btn btn-white btn-back" href="/admin/system-settings/${systemSetting.id}"><spring:message code="common.cancelButton"/></a>
                            <button class="btn btn-primary" type="submit"><spring:message code="common.saveButton"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>



    </jsp:attribute>
</mytags:header-template>

</html>
