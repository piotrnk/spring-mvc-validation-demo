<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>User list</h1>
<c:if test="${not empty message}">
    <p><c:out value="${message.key}" /></p>
</c:if>
<table>
    <tr>
        <td>Name</td>
        <td>Email</td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.name}" /></td>
            <td><c:out value="${user.email}" /></td>
        </tr>
    </c:forEach>
</table>
<a href="create">Create</a>
<a href="../account/stepOne">Wizard-like create</a>