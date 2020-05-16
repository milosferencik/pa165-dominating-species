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

<my:masterpage title="FoodChains">
    <jsp:attribute name="body">
        <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
            <td><my:a href="/foodChain/create" class="btn btn-success"><f:message key="foodChains.create"/></my:a></td>
        </c:if>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>FoodChains</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${foodChains}" var="foodChain">
                <tr>
                    <td>${foodChain.id}</td>
                    <td><my:a href="/foodChain/detail/${foodChain.id}" class="btn btn-primary"><f:message key="button.detail"/></my:a></td>
                    <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/foodChain/delete/${foodChain.id}">
                                <button type="submit" class="btn btn-danger">
                                    <f:message key="button.delete" />
                                </button>
                            </form>
                        </td>
                        <td><my:a href="/foodChain/update/${foodChain.id}" class="btn btn-success"><f:message key="button.update"/></my:a></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:masterpage>
