<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
<div>
    <form:form method="post">
        <table>
            <tr>
                <td>Login: </td>
                <td><input type="text" name="login" placeholder="Enter your name"/></td>
            </tr>
            <tr>
                <td>Password: </td>
                <td><input type="text" name="password" placeholder="Enter your password"/></td>
            </tr>
            <tr>
                <td>Full Name: </td>
                <td><input type="text" name="fullName" placeholder="Enter your full name"/></td>
            </tr>
            <tr>
                <td>Phone Number: </td>
                <td><input type="text" name="phone" placeholder="Enter your phone"/></td>
            </tr>
            <tr>
                <td>Address: </td>
                <td><input type="text" name="address" placeholder="Enter your address"/></td>
            </tr>
        </table>
        <div>
            <button type="submit">Register</button>
        </div>
    </form:form>
</div>
<div>
    <p><a href="/">Go back</a></p>
</div>
<br>
<div>Registered Users:</div>
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
</body>
</html>