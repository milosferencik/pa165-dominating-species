<%--
  Created by IntelliJ IDEA.
  User: Kostka
  Date: 16/05/2020
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="${foodChain.id} update">

    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/foodChain/update"
                   modelAttribute="foodChainUpdate" cssClass="form-horizontal">
            <form:hidden path="id" value="${foodChain.id}"></form:hidden>
            <div class="form-group ${animals_error?'has-error':''}">
                <form:label path="animals" cssClass="col-sm-2 control-label"><f:message key="foodChain.animals"/></form:label>
                <div class="col-sm-10">
                    <form:input path="animals" cssClass="form-control"/>
                    <form:errors path="animals" cssClass="help-block"/>
                </div>
            </div>
            <button class="btn btn-primary" type="submit"><f:message key="button.update"/></button>
        </form:form>
    </jsp:attribute>

</my:masterpage>
