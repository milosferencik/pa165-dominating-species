<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="a" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="${environment.name}">
    <jsp:attribute name="body">
        <table class="table">
            <tbody>
            <tr>
                <td><b><f:message key="label.name"/>:</b> ${environment.name}</td>
            </tr>
            <tr>
                <td><b><f:message key="environment.description"/>:</b> ${environment.description}</td>
            </tr>
            </tbody>
        </table>
    </jsp:attribute>
</my:masterpage>