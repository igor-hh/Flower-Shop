<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="fragments/header.jsp"/>

<html>
<head>
    <title>Flower shop</title>
</head>
<body>
<div class="container">

    <jsp:include page="fragments/navbar.jsp"/>

    <div class="row mt-3 ml-0">
        <form:form method="get" action="/">
            <div class="input-group input-group-sm">
                <input class="form-control bg-light text-dark" type="text" name="findString" placeholder="Flower name" value="${findString}">
                <input class="form-control bg-light text-dark ml-1" type="number" step="0.01" name="priceFrom" placeholder="Price from" value="${priceFrom}">
                <input class="form-control bg-light text-dark ml-1" type="number" step="0.01" name="priceTo" placeholder="Price to" value="${priceTo}">
                <button type="submit" class="btn btn-secondary btn-sm ml-1">Find flowers</button>
            </div>
        </form:form>
    </div>
    <div class="row mt-0 ml-0">
        <table class="table table-hover">
            <tr>
                <th scope="col">Flower</th>
                <th scope="col">Price per unit</th>
                <th scope="col">Available quantity</th>
                <sec:authorize access="isAuthenticated()">
                    <th scope="col">Quantity to buy</th>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <th scope="col">Cart</th>
                </sec:authorize>
            </tr>

            <c:forEach items="${flowers}" var="flowers">
                <form:form method="post" action="/cart">
                    <tr>
                        <td>${flowers.name}</td>
                        <td>${flowers.price}</td>
                        <c:if test="${flowers.quantity <= 0}">
                            <td>Out of stock :(</td>
                        </c:if>
                        <c:if test="${flowers.quantity > 0}">
                            <td>${flowers.quantity}</td>
                        </c:if>
                        <c:if test="${flowers.quantity <= 0}">
                            <td></td>
                        </c:if>
                        <c:if test="${flowers.quantity > 0}">
                            <sec:authorize access="isAuthenticated()">
                                <td><input type="text" name="cartQuantity" value="${flowers.quantity}"/></td>
                            </sec:authorize>
                        </c:if>
                        <input type="hidden" name="name" value="${flowers.name}"/>
                        <c:if test="${flowers.quantity <= 0}">
                            <td></td>
                        </c:if>
                        <c:if test="${flowers.quantity > 0}">
                            <sec:authorize access="isAuthenticated()">
                                <td><button type="submit" class="btn btn-success btn-sm">Add to cart</button></td>
                            </sec:authorize>
                        </c:if>
                    </tr>
                </form:form>
            </c:forEach>
        </table>
    </div>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>
</html>