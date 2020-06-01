<%--
  Created by IntelliJ IDEA.
  User: Kostka
  Date: 16/05/2020
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="FoodChain List">
    <jsp:attribute name="body">
        <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
            <td><my:a href="/foodChain/create" class="btn btn-success"><f:message key="foodChains.create"/></my:a></td>
        </c:if>
        <div>
            <form:form method="post" action="${pageContext.request.contextPath}/foodChain/animal"
                       modelAttribute="animals" cssClass="form-inline">
            <label class="control-label" for="animalId"><f:message key="animal"/>:
                <select name="animalId" class="form-control" id="animalId">
                    <option value="0" <c:if test="${selectedAnimalId == 0}">selected="selected"</c:if>>
                        -
                    </option>
                    <c:forEach items="${animals}" var="animal">
                        <option value="${animal.id}"
                                <c:if test="${selectedAnimalId == animal.id}">selected="selected"</c:if>>
                                ${animal.name}
                        </option>
                    </c:forEach>
                </select>
            </label>
            <button type="submit" class="btn btn-default"><f:message key="button.filter_by_animal"/></button>
            </form:form>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><f:message key="foodChain"/></th>
                <th><f:message key="actions" /></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${foodChains.size() == 0}">
                <tr>
                    <td><f:message key="no_data"/></td>
                </tr>
            </c:if>
            <c:forEach items="${foodChains}" var="foodChain">
                <tr>
                    <td>[${foodChain.id}] <c:forEach items="${foodChain.animalsInFoodChain}" var="animal">${animal.indexInFoodChain + 1}.${animal.animal.name} </c:forEach> </td>
                    <td><my:a href="/foodChain/detail/${foodChain.id}" class="btn btn-primary"><f:message key="button.detail"/></my:a></td>
                    <c:if test="${not empty authenticatedUser && authenticatedUser.admin}">
                        <td>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/foodChain/delete/${foodChain.id}">
                                <button type="submit" class="btn btn-danger">
                                    <f:message key="button.delete"/>
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
