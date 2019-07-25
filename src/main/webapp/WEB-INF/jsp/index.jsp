<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
    <div>
        <form method="post">
            <input type="text" name="name" placeholder="Enter name" />
            <input type="text" name="orderId" placeholder="Enter order number" />
            <button type="submit">Add new user</button>
        </form>
    </div>
    <div>Users List:</div>
    <div>
        <table border="1">
        <c:forEach items="${arg}" var="arg">
            <tr>
                <td>${arg.id}</td>
                <td>${arg.name}</td>
                <td>${arg.orderId}</td>
            </tr>
        </c:forEach>
        </table>
    </div>
</body>
</html>