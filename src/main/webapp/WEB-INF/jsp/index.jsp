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
    <jsp:include page="fragments/footer.jsp"/>

    <script>
        $(function () {
            $('[data-toggle="popover"]').popover();
            $('[data-toggle="tooltip"]').tooltip();
        })
    </script>

    <c:choose>
        <c:when test="${not empty cartError}">
            <p class="text-danger mt-3">${cartError}</p>
            <a href="/">Continue Shopping</a>
        </c:when>
        <c:otherwise>
            <div class="row mt-3 ml-0">
                <form:form method="get" action="/">
                    <div class="input-group input-group-sm">
                        <input class="form-control bg-light text-dark" type="text" name="findString"
                               placeholder="Flower name" value="${filter.findString}">
                        <input class="form-control bg-light text-dark ml-1" type="number" min="0" step="0.01"
                               name="priceFrom"
                               placeholder="Price from" value="${filter.priceFrom}">
                        <input class="form-control bg-light text-dark ml-1" type="number" min="0" step="0.01"
                               name="priceTo"
                               placeholder="Price to" value="${filter.priceTo}">
                        <button type="submit" class="btn btn-secondary btn-sm ml-1">Find flowers</button>
                    </div>
                </form:form>
            </div>
            <div class="row mt-0 ml-0">
                <c:choose>
                <c:when test="${not empty filterError}">
                    <p class="text-danger mt-3">${filterError}</p>
                </c:when>
                <c:otherwise>
                    <table class="table table-hover mr-5">
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
                                <form:form method="post" action="/cart">
                                    <c:if test="${flowers.quantity > 0}">
                                        <sec:authorize access="isAuthenticated()">
                                            <td><input type="number" min="1" max="2147483647" name="cartQuantity"
                                                       required="required"
                                                       value="${flowers.quantity}"/></td>
                                        </sec:authorize>
                                    </c:if>

                                    <input type="hidden" name="flowerId" value="${flowers.id}"/>

                                    <div class="btn-group">
                                        <c:if test="${flowers.quantity <= 0}">
                                            <sec:authorize access="isAuthenticated()">
                                                <td>
                                            <span class="d-inline-block btn-block" data-toggle="tooltip"
                                                  title="No flowers on stock" data-placement="right">
                                                <button type="button" style="pointer-events: none;"
                                                        class="btn btn-secondary btn-sm btn-block"
                                                        disabled>Out of stock</button>
                                            </span>
                                                </td>
                                            </sec:authorize>
                                        </c:if>
                                        <c:if test="${flowers.quantity > 0}">
                                            <sec:authorize access="isAuthenticated()">
                                                <td>
                                                    <c:if test="${flowers.id == addedFlower}">
                                                        <button id="${addedFlower}" type="submit"
                                                                class="btn btn-success btn-sm btn-block"
                                                                data-toggle="popover"
                                                                data-content="${cartSuccess}">Add to cart
                                                        </button>
                                                    </c:if>
                                                    <c:if test="${flowers.id != addedFlower}">
                                                        <button type="submit" class="btn btn-success btn-sm btn-block">
                                                            Add to cart
                                                        </button>
                                                    </c:if>
                                                    <script>
                                                        $("#${addedFlower}").popover("show");
                                                    </script>
                                                </td>
                                            </sec:authorize>
                                        </c:if>
                                    </div>
                                </form:form>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
                </c:choose>
            </div>
        </c:otherwise>
    </c:choose>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>
</html>