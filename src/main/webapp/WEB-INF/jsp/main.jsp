<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--Include the CSRF Token--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
<div>
    <form:form action="/main" method="post">
        Create test order: <input type="text" name="totalPrice" placeholder="total price" />
        <button type="submit">Create order</button>
    </form:form>
</div>

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
        <c:forEach items="${users}" var="users">
            <tr>
                <td>${users.id}</td>
                <td>${users.login}</td>
                <td>${users.password}</td>
                <td>${users.fullName}</td>
                <td>${users.phone}</td>
                <td>${users.address}</td>
                <td>${users.balance}</td>
                <td>${users.discount}</td>
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