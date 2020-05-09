<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:masterpage title="Animals">
<jsp:attribute name="body">

    <h1>${authenticatedUser}</h1>
    <h1>${sessionScope['authenticatedUser']}</h1>
    <h1>${authenticatedUser.name}</h1>
    <h1>${sessionScope.authenticatedUser}</h1>
    <h1>${requestScope.authenticatedUser}</h1>
    <h1>${applicationScope.authenticatedUser}</h1>
    <my:a href="/animal/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New animal
    </my:a>

    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${animals}" var="animal">
            <tr>
                <td>${animal.id}</td>
                <td><c:out value="${animal.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:masterpage>