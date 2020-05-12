<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="New User">

    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/user/create"
                   modelAttribute="createUser" cssClass="form-horizontal">
            <div class="form-group ${name_error?'has-error':''}">
                <form:label path="name" cssClass="col-sm-2 control-label"><f:message key="firstname"/></form:label>
                <div class="col-sm-10">
                    <form:input path="name" cssClass="form-control"/>
                    <form:errors path="name" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${surname_error?'has-error':''}">
                <form:label path="surname" cssClass="col-sm-2 control-label"><f:message key="surname"/></form:label>
                <div class="col-sm-10">
                    <form:input path="surname" cssClass="form-control"/>
                    <form:errors path="surname" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${email_error?'has-error':''}">
                <form:label path="email" cssClass="col-sm-2 control-label"><f:message key="email"/></form:label>
                <div class="col-sm-10">
                    <form:input path="email" cssClass="form-control"/>
                    <form:errors path="email" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${passwordHash_error?'has-error':''}">
                <form:label path="passwordHash" cssClass="col-sm-2 control-label"><f:message key="login.password"/></form:label>
                <div class="col-sm-10">
                    <form:input path="passwordHash" cssClass="form-control"/>
                    <form:errors path="passwordHash" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${admin_error?'has-error':''}">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="form-check">
                        <form:label path="admin"> Is admin
                        <form:checkbox path="admin" cssClass="form-check-input"/> </form:label>
                        <form:errors path="passwordHash" cssClass="help-block"/>
                    </div>
                </div>
            </div>
            <button class="btn btn-primary" type="submit"><f:message key="users.createNew"/></button>
        </form:form>
        <br>
    </jsp:attribute>

</my:masterpage>