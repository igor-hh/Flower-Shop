<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Order</title>
</head>
<body>
<div>List of orders:</div>
<div>
    <table border="1">
        <tr>
            <th>Id</th>
            <th>Creation Date</th>
            <th>Close Date</th>
            <th>Total Price</th>
            <th>Status</th>
        </tr>
    <c:forEach items="${orders}" var="orders">
        <tr>
            <td>${orders.id}</td>
            <td>${orders.creationDate}</td>
            <td>${orders.closeDate}</td>
            <td>${orders.totalPrice}</td>
            <td>${orders.status}</td>
        </tr>
    </c:forEach>
    </table>
</div>
</body>