<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ attribute name="authenticatedUser" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width" initial-scale="1">
    <title><f:message key="navigation.title"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"  crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <jsp:invoke fragment="head"/>
</head>
<body>
<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
                <a class="navbar-brand" href="${pageContext.request.contextPath}"><f:message key="navigation.adminTitle"/></a>
            </c:if>
            <c:if test="${empty authenticatedUser || !authenticatedUser.admin}">
                <a class="navbar-brand" href="${pageContext.request.contextPath}"><f:message key="navigation.title"/></a>
            </c:if>

        </div>

        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><my:a href="/foodWeb/"><i class="fa fa-globe"></i> <f:message key="navigation.foodWeb"/></my:a></li>
                <li><my:a href="/foodChain/"><i class="fa fa-chain"></i> <f:message key="navigation.foodChains"/></my:a></li>
                <li><my:a href="/animal/"><i class="fa fa-paw"></i> <f:message key="navigation.animals"/></my:a></li>
                <li><my:a href="/environment/"><i class="fa fa-tree"></i> <f:message key="navigation.environments"/></my:a></li>
                <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
                    <li><my:a href="/user/"><i class="fa fa-users"></i> <f:message key="navigation.users"/></my:a></li>
                </c:if>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <c:if test="${empty authenticatedUser}">
                    <li><my:a href="/auth/login"><f:message key="navigation.login"/></my:a></li>
                </c:if>
                <c:if test="${not empty authenticatedUser}">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown">${authenticatedUser.name}&nbsp${authenticatedUser.surname}
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><my:a href="/user/detail/${authenticatedUser.id}"><f:message key="user.view"/></my:a></li>
                            <li><my:a href="/user/update/${authenticatedUser.id}"><f:message key="user.update"/></my:a></li>
                            <li><my:a href="/user/password/${authenticatedUser.id}"><f:message key="user.change.password"/></my:a></li>
                            <li class="divider"></li>
                            <li><my:a href="/auth/logout"><f:message key="navigation.logout"/></my:a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>


<div class="container">
    <c:if test="${not empty title}">
        <div class="page-header">
            <h1><c:out value="${title}"/></h1>
        </div>
    </c:if>

    <!-- alerts -->
    <c:if test="${not empty alert_danger}">
        <div class="alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <c:out value="${alert_danger}"/></div>
    </c:if>
    <c:if test="${not empty alert_info}">
        <div class="alert alert-info" role="alert"><c:out value="${alert_info}"/></div>
    </c:if>
    <c:if test="${not empty alert_success}">
        <div class="alert alert-success" role="alert"><c:out value="${alert_success}"/></div>
    </c:if>
    <c:if test="${not empty alert_warning}">
        <div class="alert alert-warning" role="alert"><c:out value="${alert_warning}"/></div>
    </c:if>

    <!-- page body -->
    <jsp:invoke fragment="body"/>

    <!-- footer -->
    <footer class="panel-footer panel-primary">
        <p><f:message key="footer"/> </p>
        <p>&copy;&nbsp;<%=java.time.Year.now().toString()%>&nbsp;Peter Kostka, Katarína Matúšová, Miloš Ferenčík, Ondřej Slimák</p>
    </footer>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</body>
</html>
