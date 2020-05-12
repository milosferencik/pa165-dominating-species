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
        </c:if>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><f:message key="name"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}
                        <c:if test="${user.admin}">
                            <span class="badge badge-dark"> Admin </span>
                        </c:if>
                    </td>
                    <td><my:a href="/user/detail/${user.id}" class="btn btn-primary"><f:message key="detail"/></my:a></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/user/delete/${user.id}">
                            <button type="submit" class="btn btn-danger">
                                <f:message key="delete"/>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:masterpage>