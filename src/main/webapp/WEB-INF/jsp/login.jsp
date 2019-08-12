<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/header.jsp"/>

<html>
<body>
<div class="container">

    <jsp:include page="fragments/navbar.jsp"/>

    <div>
        <c:if test="${not empty loginError}">
            <p class="text-danger mt-3">${loginError}</p>
            <p class="text-danger">Reason: ${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
        </c:if>
    </div>
    <form:form action="/login" method="post">
        <div class="form-group row mt-5">
            <label class="col-sm-2 col-form-label">Login: </label>
            <div class="col-sm-5">
                <input type="text" class="form-control" name="username" placeholder="Login"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password: </label>
            <div class="col-sm-5">
                <input type="password" class="form-control" name="password" placeholder="Password"/>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Sign in</button>
    </form:form>

    <div class="row mt-4 ml-0">
        <p>New to Flower Shop? <b><a href="/signup">Sign up</a></b></p>
    </div>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>
</html>