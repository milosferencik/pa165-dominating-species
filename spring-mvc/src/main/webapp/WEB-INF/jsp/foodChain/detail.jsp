<%--
  Created by IntelliJ IDEA.
  User: Kostka
  Date: 16/05/2020
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="a" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="${foodChain.id} detail">
    <jsp:attribute name="body">

        <table class="table table-striped">

            <tbody>

            <c:forEach items="${animalsInFoodChain}" var="animal">
                <tr>
                    <td>${animal.animal.name}</td>
                    <td><my:a href="/animal/detail/${animal.animal.id}" class="btn btn-primary"><f:message key="button.detail"/></my:a></td>
                    <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
                        <td><my:a href="/animal/update/${animal.animal.id}" class="btn btn-success"><f:message key="button.update"/></my:a></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:masterpage>