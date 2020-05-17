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
<my:masterpage title="New FoodChain">

    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/animal/create"
                   modelAttribute="foodChainCreate" cssClass="form-horizontal">
            <form:label path="animalsInFoodChain" cssClass="col-sm-2 control-label"><f:message key="animal"/></form:label>
            <div class="col-sm-10">
                <form:select path="animalsInFoodChain" cssClass="form-control">
                    <c:forEach items="${animals}" var="c">
                        <form:option value="${c.id}">${c.name}</form:option>
                    </c:forEach>
                </form:select>
                <p class="help-block"><form:errors path="animalsInFoodChain" cssClass="error"/></p>
            </div>
            <button class="btn btn-primary" type="submit"><f:message key="foodChains.createNew"/></button>
        </form:form>
    </jsp:attribute>

</my:masterpage>
