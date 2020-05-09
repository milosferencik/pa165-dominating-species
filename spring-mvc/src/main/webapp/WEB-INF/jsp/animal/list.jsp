<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="Animals">
    <jsp:attribute name="body">
        <c:if test="${not empty authenticatedUser && authenticatedUser.isAdmin}">
            <td><my:a href="/animal/create" class="btn btn-success"><f:message key="animals.create"/></my:a></td>
        </c:if>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><f:message key="name"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${animals}" var="animal">
                <tr>
                    <td>${animal.name}</td>
                    <td><my:a href="/animal/detail/${animal.id}" class="btn btn-primary"><f:message key="detail"/></my:a></td>
                    <c:if test="${not empty authenticatedUser && authenticatedUser.isAdmin}">
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/animal/delete/${animal.id}">
                                <button type="submit" class="btn btn-danger">
                                    <f:message key="delete" />
                                </button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:masterpage>