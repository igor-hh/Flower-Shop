<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<html>
<head>
    <title>Cart</title>
</head>
<body>
<div class="container">

    <jsp:include page="fragments/navbar.jsp"/>

    <c:if test="${not empty cartEmpty}">
        <p class="text-info mt-5">${cartEmpty}</p>
        <p class="text-info">Add flowers to cart to buy them.</p>
    </c:if>
    <c:if test="${empty cartEmpty}">
        <div>Flowers in cart:</div>
        <div>

            <table class="table table-sm table-hover">
                <tr>
                    <th>Flower Name</th>
                    <th>Quantity</th>
                    <th>Item Price</th>
                    <th>Remove From Cart</th>
                </tr>
                <c:forEach items="${flowersInCart}" var="flowers">
                    <form:form method="post" action="/cart/remove">
                        <tr>
                            <td>${flowers.key.name}</td>
                            <td>${flowers.value}</td>
                            <td>${cart.getItemPrice(flowers.key.name)}</td>
                            <input type="hidden" name="name" value="${flowers.key.name}"/>
                            <td>
                                <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                            </td>
                        </tr>
                    </form:form>
                </c:forEach>
            </table>
            <div>
                <p>Total price with discount: ${cart.getTotalPrice()}</p>
            </div>
        </div>
        <br>
        <div>
            <form:form action="/order" method="post">
                <button type="submit" class="btn btn-success mr-3">Create Order</button>
                <a href="/">Continue Shopping</a>
            </form:form>
        </div>

        <jsp:include page="fragments/footer.jsp"/>
    </c:if>
</div>
</body>
</html>