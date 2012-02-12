<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Create account - Summary</h1>
<table>
    <tr>
        <td>Name</td>
        <td>${accountForm.username}</td>
    </tr>
    <tr>
        <td>Email</td>
        <td>${accountForm.email}</td>
    </tr>
    <tr>
        <td>Password</td>
        <td>${accountForm.password}</td>
    </tr>
    <tr>
        <td colspan="2">
            <button onclick="location.href='create'">Create</button>
        </td>
    </tr>
</table>