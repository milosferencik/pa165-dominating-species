<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="${user.name} ${user.surname}">

    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/user/update"
                   modelAttribute="userUpdate" cssClass="form-horizontal">
            <form:hidden path="id" value="${user.id}"></form:hidden>
            <div class="form-group ${name_error?'has-error':''}">
                <form:label path="name" cssClass="col-sm-2 control-label"><f:message key="user.firstname"/></form:label>
                <div class="col-sm-10">
                    <form:input path="name" cssClass="form-control"/>
                    <form:errors path="name" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${surname_error?'has-error':''}">
                <form:label path="surname" cssClass="col-sm-2 control-label"><f:message key="user.surname"/></form:label>
                <div class="col-sm-10">
                    <form:input path="surname" cssClass="form-control"/>
                    <form:errors path="surname" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${email_error?'has-error':''}">
                <form:label path="email" cssClass="col-sm-2 control-label"><f:message key="login.email"/></form:label>
                <div class="col-sm-10">
                    <form:input path="email" cssClass="form-control"/>
                    <form:errors path="email" cssClass="help-block"/>
                </div>
            </div>
            <button class="btn btn-primary" type="submit"><f:message key="button.update"/></button>
        </form:form>
    </jsp:attribute>

</my:masterpage>
