<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Hello</title>
</head>

<style>
<%--    Add css for table, rows, column here--%>
</style>
<body>

<div>
    <b>
        ${user}
    </b>
</div>

<div>
    <h1>System Users</h1>
</div>
<div>
    <table>
        <tr>
            <td>User Id</td>
            <td>User Name</td>
            <td>User Surname</td>
            <td>Birth date</td>
            <%--            <td>Gender</td>--%>
            <td>Is Deleted</td>
            <td>Created</td>
            <td>Changed</td>
            <td>Weight</td>
            <td>Edit</td>
            <td>Delete</td>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.getId()}</td>
                <td>${user.getUsername()}</td>
                <td>${user.getEmail()}</td>
                <td>${user.getRegistration_date()}</td>
                <td>${user.getPhone_number()}</td>
                <td><fmt:formatDate value="${user.getCreation_date()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><fmt:formatDate value="${user.getModification_date()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${user.getLocation_id()}"/></td>
                <td>
                    <button>Edit</button>
                </td>
                <td>
                    <button>Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
