<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/"><b>Flower Shop</b></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item">
                    <a class="nav-link" href="/cart">Shopping Cart</a>
                </li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item">
                    <a class="nav-link" href="/order">My Orders</a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAuthority('ADMIN')">
                <li class="nav-item">
                    <a class="nav-link text-success" href="/manageOrders"><b>Manage Orders</b></a>
                </li>
            </sec:authorize>
        </ul>
        <span class="navbar-text mr-3">
            <sec:authorize access="!isAuthenticated()">
                <span>Hello, Guest. Please login to purchase flowers</span>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <span>Greetings, <b><sec:authentication property="principal.login" /></b>!</span>
                <span> Your balance: <b><sec:authentication property="principal.balance" /></b>,</span>
                <span> your discount: <b><sec:authentication property="principal.discount" />%</b></span>
            </sec:authorize>
        </span>
        <sec:authorize access="isAuthenticated()">
            <form:form action="/logout" method="post">
                <button type="submit" class="btn btn-danger mt-2">Sign out</button>
            </form:form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a href="/login" class="btn btn-primary mt-2" role="button">Sign in</a>
        </sec:authorize>
    </div>
</nav>