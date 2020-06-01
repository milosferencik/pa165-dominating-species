<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="Environments">
    <jsp:attribute name="body">
        <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
            <td><my:a href="/environment/create" class="btn btn-success"><f:message
                    key="environments.create"/></my:a></td>
        </c:if>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><f:message key="label.name"/></th>
                <th><f:message key="actions"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${environments.size() == 0}">
                <tr>
                    <td><f:message key="no_data"/></td>
                </tr>
            </c:if>
            <c:forEach items="${environments}" var="environment">
                <tr>
                    <td>${environment.name}</td>
                    <td><my:a href="/environment/detail/${environment.id}" class="btn btn-primary"><f:message
                            key="button.detail"/></my:a></td>
                    <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
                        <td>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/environment/delete/${environment.id}">
                                <button type="submit" class="btn btn-danger">
                                    <f:message key="button.delete"/>
                                </button>
                            </form>
                        </td>
                        <td><my:a href="/environment/update/${environment.id}" class="btn btn-success"><f:message
                                key="button.update"/></my:a></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:masterpage>