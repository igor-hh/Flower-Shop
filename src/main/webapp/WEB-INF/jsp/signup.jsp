<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
Add new user <br>
<c:if test="${not empty message}">
    ${message}
</c:if>
<br>
<div>
    <form:form action="/signup" method="post">
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

</body>
</html>