<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="Change Password">

    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/user/password/${passwordChangeDto.id}"
                   modelAttribute="passwordChangeDto" cssClass="form-horizontal">
            <form:hidden path="id" value="${passwordChangeDto.id}"/>
            <div class="form-group ${password_error?'has-error':''}">
                <form:label path="password" cssClass="col-sm-2 control-label"><f:message
                        key="login.password"/></form:label>
                <div class="col-sm-10">
                    <form:password path="password" cssClass="form-control"/>
                    <form:errors path="password" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${newPassword_error?'has-error':''}">
                <form:label path="newPassword" cssClass="col-sm-2 control-label"><f:message
                        key="user.new.password"/></form:label>
                <div class="col-sm-10">
                    <form:password path="newPassword" cssClass="form-control"/>
                    <form:errors path="newPassword" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${repeatedPassword_error?'has-error':''}">
                <form:label path="repeatedPassword" cssClass="col-sm-2 control-label"><f:message
                        key="user.repeat.password"/></form:label>
                <div class="col-sm-10">
                    <form:password path="repeatedPassword" cssClass="form-control"/>
                    <form:errors path="repeatedPassword" cssClass="help-block"/>
                </div>
            </div>
            <button class="btn btn-primary" type="submit">Change password</button>
        </form:form>
        <br>
    </jsp:attribute>
</my:masterpage>