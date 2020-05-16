<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="Users">
    <jsp:attribute name="body">
        <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
            <td><my:a href="/user/create" class="btn btn-success"><f:message key="users.create"/></my:a></td>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><f:message key="name"/></th>
                    <th><f:message key="login.email"/></th>
                    <th><f:message key="actions"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.name} ${user.surname}
                            <c:if test="${authenticatedUser.email == user.email}">
                                <span class="glyphicon glyphicon-user"></span>
                            </c:if>
                            <c:if test="${user.admin}">
                                <span class="badge badge-dark" > Admin </span>
                            </c:if>
                        </td>
                        <td>${user.email}
                        </td>
                        <td><my:a href="/user/detail/${user.id}" class="btn btn-primary"><f:message key="detail"/></my:a></td>
                        <td>
                            <c:choose>
                                <c:when test="${authenticatedUser.email != user.email}">
                                    <form method="post" action="${pageContext.request.contextPath}/user/delete/${user.id}">
                                        <button type="submit" class="btn btn-danger">
                                            <f:message key="delete"/>
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn btn-danger disabled">
                                        <f:message key="delete"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <my:a href="/user/update/${user.id}" class="btn btn-success"><f:message key="button.update"/></my:a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </jsp:attribute>
</my:masterpage>
