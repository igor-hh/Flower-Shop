<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--Include the CSRF Token--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>

<div>Users List:</div>
<div>
    <table border="1">
        <tr>
            <th>id</th>
            <th>login</th>
            <th>password</th>
            <th>fullName</th>
            <th>phone</th>
            <th>address</th>
            <th>balance</th>
            <th>discount</th>
        </tr>
        <c:forEach items="${arg}" var="arg">
            <tr>
                <td>${arg.id}</td>
                <td>${arg.login}</td>
                <td>${arg.password}</td>
                <td>${arg.fullName}</td>
                <td>${arg.phone}</td>
                <td>${arg.address}</td>
                <td>${arg.balance}</td>
                <td>${arg.discount}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<br>
<div>
    <form:form action="/logout" method="post">
        <input type="submit" value="Sign Out"/>
    </form:form>
</div>
</body>
</html>