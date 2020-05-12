<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="Animals">
    <jsp:attribute name="body">
        <div>
            <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
                <td><my:a href="/animal/create" class="btn btn-success"><f:message key="animals.create"/></my:a></td>
            </c:if>
        </div>
        <div>
            <form:form method="post" action="${pageContext.request.contextPath}/animal/environment"
                       modelAttribute="environments" cssClass="form-inline">
            <label class="control-label" for="environmentId"><f:message key="environment"/>:
                <select name="environmentId" class="form-control" id="environmentId">
                    <option value="0" <c:if test="${selectedEnvironmentId == 0}">selected="selected"</c:if>>
                        -
                    </option>
                    <c:forEach items="${environments}" var="env">
                        <option value="${env.id}"
                                <c:if test="${selectedEnvironmentId == env.id}">selected="selected"</c:if>>
                                ${env.name}
                        </option>
                    </c:forEach>
                </select>
            </label>
            <button type="submit" class="btn btn-default"><f:message key="button.filter_by_environment"/></button>
            </form:form>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><f:message key="label.name"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${animals}" var="animal">
                <tr>
                    <td>${animal.name}</td>
                    <td><my:a href="/animal/detail/${animal.id}" class="btn btn-primary"><f:message key="detail"/></my:a></td>
                    <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
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