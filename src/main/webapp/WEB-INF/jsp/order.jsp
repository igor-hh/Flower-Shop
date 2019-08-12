<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<html>
<head>
    <title>Order</title>
</head>
<body>
<div class="container">

    <jsp:include page="fragments/navbar.jsp"/>

    <c:if test="${not empty ordersEmpty}">
        <p class="text-info mt-5">${ordersEmpty}</p>
        <p class="text-info">Add flowers to cart and create an order.</p>
    </c:if>
    <c:if test="${empty ordersEmpty}">
        <div>List of orders:</div>
        <div>
            <table class="table table-sm table-hover">
                <tr>
                    <th>Id</th>
                    <th>Creation Date</th>
                    <th>Close Date</th>
                    <th>Total Price</th>
                    <th>Status</th>
                    <th>Pay</th>
                </tr>
                <c:forEach items="${orders}" var="orders">
                    <form:form method="post" action="/order/pay">
                        <tr>
                            <td>${orders.id}</td>
                            <td>${orders.creationDate}</td>
                            <td>${orders.closeDate}</td>
                            <td>${orders.totalPrice}</td>
                            <td>${orders.status}</td>
                            <input type="hidden" name="id" value="${orders.id}"/>
                            <c:if test="${orders.status != 'CREATED'}">
                                <td></td>
                            </c:if>
                            <c:if test="${orders.status == 'CREATED'}">
                                <td>
                                    <button type="submit" class="btn btn-success btn-sm">Pay</button>
                                </td>
                            </c:if>
                        </tr>
                    </form:form>
                </c:forEach>
            </table>
        </div>
        <div>
            <a href="/">Continue Shopping</a>
        </div>
    </c:if>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>
</html>