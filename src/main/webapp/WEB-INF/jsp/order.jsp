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

    <c:if test="${not empty payError}">
        <p class="text-danger mt-3">${payError}</p>
    </c:if>

    <c:if test="${empty payError}">
        <c:if test="${not empty ordersEmpty}">
            <p class="text-info mt-5">${ordersEmpty}</p>
            <p class="text-info">Add flowers to cart and create an order.</p>
        </c:if>
        <c:if test="${empty ordersEmpty}">
            <p class="mt-3"><b>Flowers in cart:</b></p>
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
                                <td><fmt:formatDate value="${orders.creationDate}" pattern="dd.MM.yyyy HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${orders.closeDate}" pattern="dd.MM.yyyy HH:mm:ss"/></td>
                                <td>${orders.totalPrice}</td>
                                <td>${orders.status}</td>
                                <input type="hidden" name="id" value="${orders.id}"/>
                                <div>
                                    <c:if test="${orders.status != 'CREATED'}">
                                        <td>
                                            <button type="submit" class="btn btn-secondary btn-sm btn-block" disabled>
                                                Paid
                                            </button>
                                        </td>
                                    </c:if>
                                    <c:if test="${orders.status == 'CREATED'}">
                                        <td>
                                            <button type="submit" class="btn btn-success btn-sm btn-block">Pay</button>
                                        </td>
                                    </c:if>
                                </div>
                            </tr>
                        </form:form>
                    </c:forEach>
                </table>
            </div>
            <div>
                <a href="/">Continue Shopping</a>
            </div>
        </c:if>
    </c:if>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>
</html>