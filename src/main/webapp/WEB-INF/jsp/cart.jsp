<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Cart</title>
</head>
<body>
<div>Flowers in cart:</div>
<div>
    <form:form method="post" action="/cart">
        <table border="1">
            <tr>
                <th>Flower Name</th>
                <th>Quantity</th>
                <th>Remove From Cart</th>
            </tr>
        <c:forEach items="${flowersInCart}" var="flowers">

                <tr>
                    <td>${flowers.key.name}</td>
                    <td>${flowers.value}</td>
                    <input type="hidden" name="name" value="${flowers.key.name}" />
                    <td><button type="submit">Remove</button></td>
                </tr>
        </c:forEach>
        </table>
    </form:form>
</div>
<div>
    <form:form action="/order" method="post">
        <button type="submit">Create Order</button>
    </form:form>
</div>
<div>
    <a href="/">Continue Shopping</a>
</div>
</body>
</html>