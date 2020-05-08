<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">

    <div class="jumbotron">
        <h1>Welcome to SpringMVC !</h1>
    </div>

    <div class="row">
        <c:forEach begin="1" end="12" var="i">
        <div class="col-xs-12 col-sm-6 col-md-2 col-lg-1">
            <p><button class="btn btn-default">Button ${i}</button></p>
        </div>
        </c:forEach>
    </div>

</jsp:attribute>
</my:pagetemplate>