<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Create user</h1>
<form:form modelAttribute="profileForm">
    <form:errors path="" element="p" />
    <table>
        <tr>
            <td>Name</td>
            <td><form:input path="username" /> <form:errors path="username" /></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><form:input path="email" /> <form:errors path="email" /></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><form:password path="password" /> <form:errors path="password" /></td>
        </tr>
        <tr>
            <td>Confirm password</td>
            <td><form:password path="confirmedPassword" /> <form:errors path="confirmedPassword" /></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Create"></input></td>
        </tr>
    </table>
</form:form>