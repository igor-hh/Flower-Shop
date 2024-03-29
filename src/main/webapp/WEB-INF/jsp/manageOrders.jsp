<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="fragments/header.jsp"/>

<html>
<head>
    <title>Order</title>
</head>
<body>
<div class="container">

    <jsp:include page="fragments/navbar.jsp"/>

    <c:choose>
        <c:when test="${not empty ordersEmpty}">
            <p class="text-info mt-5">${ordersEmpty}</p>
        </c:when>
        <c:when test="${not empty closeError}">
            <p class="text-info mt-5">${closeError}</p>
        </c:when>
        <c:otherwise>
            <p class="mt-3"><b>Flowers in cart:</b></p>
            <div>
                <table class="table table-sm table-hover">
                    <tr>
                        <th>Order ID</th>
                        <th>Creation Date</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>User Login</th>
                        <th>Close</th>
                    </tr>
                    <c:forEach items="${orders}" var="orders">
                        <form:form method="post" action="/manageOrders">
                            <tr>
                                <td>${orders.id}</td>
                                <td><fmt:formatDate value="${orders.creationDate}"
                                                    pattern="dd.MM.yyyy HH:mm:ss"/></td>
                                <td>${orders.totalPrice}</td>
                                <td>${orders.status}</td>
                                <td>${orders.owner.getLogin()}</td>
                                <input type="hidden" name="orderId" value="${orders.id}"/>
                                <td>
                                    <div>
                                        <c:if test="${orders.status != 'PAID'}">
                                            <button type="submit" class="btn btn-secondary btn-sm btn-block" disabled>
                                                Order closed
                                            </button>
                                        </c:if>
                                        <c:if test="${orders.status == 'PAID'}">
                                            <button type="submit" class="btn btn-success btn-sm btn-block">Close order</button>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </form:form>
                    </c:forEach>
                </table>
            </div>
        </c:otherwise>
    </c:choose>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>
</html>