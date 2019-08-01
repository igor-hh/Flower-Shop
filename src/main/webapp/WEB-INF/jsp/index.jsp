<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Getting Started: Serving Web Content</title>
</head>
<body>
    <h1>Greetings, User!</h1>
    <p><a href="/main">Main Page</a></p>

    <div>
        <form:form method="get" action="/">
            <input type="text" name="findString" placeholder="Flower name" value="${findString}"/>
            <input type="number" step="0.01" name="priceFrom"placeholder="Price from" value="${priceFrom}" style="width: 80px" />
            <input type="number" step="0.01" name="priceTo" placeholder="Price to" value="${priceTo}" style="width: 80px" />
            <button type="submit">Find flower</button>
        </form:form>
    </div>
<div>Flowers List:</div>
<div>
    <table border="1">
        <tr>
            <th>id</th>
            <th>name</th>
            <th>price</th>
            <th>Available quantity</th>
            <th>Enter quantity</th>
            <th>Cart</th>
        </tr>
        <c:forEach items="${flowers}" var="flowers">
            <tr>
                <td>${flowers.id}</td>
                <td>${flowers.name}</td>
                <td>${flowers.price}</td>
                <td>${flowers.quantity}</td>
                <td><input type="text" name="quantity" value="${flowers.quantity}" /></td>
                <td><button type="submit">Add to cart</button></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>