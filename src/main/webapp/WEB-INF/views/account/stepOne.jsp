<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Create account - Step One</h1>
<form:form modelAttribute="accountForm" method="post">
    <form:errors path="" element="p" />
    <table>
        <tr>
            <td>Name</td>
            <td>
                <form:input path="username"></form:input>
                <form:errors path="username"></form:errors>
            </td>
        </tr>
        <tr>
            <td>Email</td>
            <td>
                <form:input path="email"></form:input>
                <form:errors path="email"></form:errors>
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Next"></input></td>
        </tr>
    </table>
</form:form>
