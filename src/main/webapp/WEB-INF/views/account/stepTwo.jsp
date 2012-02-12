<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Create account - Step Two</h1>
<form:form modelAttribute="accountForm" method="post">
    <form:errors path="" element="p" />
    <table>
        <tr>
            <td>Password</td>
            <td>
                <form:password path="password"></form:password> 
                <form:errors path="password"></form:errors>
            </td>
        </tr>
        <tr>
            <td>Confirm password</td>
            <td>
                <form:password path="confirmedPassword"></form:password> 
                <form:errors path="confirmedPassword"></form:errors>
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Next"></input></td>
        </tr>
    </table>
</form:form>