<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="a" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="${user.name} ${user.surname}">
    <jsp:attribute name="body">
        <table class="table">
            <tbody>
            <tr>
                <td><b><f:message key="user.firstname"/>:</b> ${user.name}</td>
            </tr>
            <tr>
                <td><b><f:message key="user.surname"/>:</b> ${user.surname}</td>
            </tr>
            <tr>
                <td><b><f:message key="login.email"/>:</b> ${user.email}</td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${user.admin}">
                        <td><b><f:message key="user.role"/>:</b> Administrator </td>
                    </c:when>
                    <c:otherwise>
                        <td><b><f:message key="user.role"/>:</b> User </td>
                    </c:otherwise>
                </c:choose>
            </tr>
            </tbody>
        </table>
    </jsp:attribute>
</my:masterpage>