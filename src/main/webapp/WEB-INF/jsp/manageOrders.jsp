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
    </c:if>
    <c:if test="${empty ordersEmpty}">
    <div>List of orders to close:</div>
    <div>
        <table class="table table-sm table-hover">
            <tr>
                <th>Id</th>
                <th>Creation Date</th>
                <th>Close Date</th>
                <th>Total Price</th>
                <th>Status</th>
                <th>Owner id</th>
                <th>Close</th>
            </tr>
            <c:forEach items="${orders}" var="orders">
                <form:form method="post" action="/manageOrders">
                    <tr>
                        <td>${orders.id}</td>
                        <td>${orders.creationDate}</td>
                        <td>${orders.closeDate}</td>
                        <td>${orders.totalPrice}</td>
                        <td>${orders.status}</td>
                        <td>${orders.owner.getLogin()}</td>
                        <input type="hidden" name="id" value="${orders.id}"/>
                        <td><button type="submit" class="btn btn-success btn-sm">Close order</button></td>
                    </tr>
                </form:form>
            </c:forEach>
        </table>
    </div>
    </c:if>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>
</html>