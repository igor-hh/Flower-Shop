<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="fragments/header.jsp"/>

<html>
<body>
<div class="container">

    <jsp:include page="fragments/navbar.jsp"/>

    <div class="row mt-4 ml-0">
        Registration Form:
    </div>

    <form:form action="/signup" method="post">
        <div class="form-group row mt-5">
            <label class="col-sm-2 col-form-label">Login: </label>
            <div class="col-sm-5">
                <input id="loginInput" type="text" class="form-control${not empty userError ? " is-invalid" : ""}"
                       name="login" placeholder="Enter login"/>
                <div class="invalid-feedback">${not empty userError ? userError : ""}</div>
            </div>
            <div class="col">
                <span id="loginError"></span>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password: </label>
            <div class="col-sm-5">
                <c:if test="${(not empty passwordError) or (not empty passwordBlank)}">
                    <input type="password" class="form-control is-invalid" name="password"
                           placeholder="Enter your password"/>
                    <div class="invalid-feedback">${passwordBlank}${passwordError}</div>
                </c:if>
                <c:if test="${(empty passwordError) and (empty passwordBlank)}">
                    <input type="password" class="form-control" name="password" placeholder="Enter your password"/>
                </c:if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Confirm password: </label>
            <div class="col-sm-5">
                <c:if test="${not empty passwordError}">
                    <input type="password" class="form-control is-invalid" name="password2"
                           placeholder="Confirm your password"/>
                    <div class="invalid-feedback">${passwordError}</div>
                </c:if>
                <c:if test="${empty passwordError}">
                    <input type="password" class="form-control" name="password2" placeholder="Confirm your password"/>
                </c:if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">E-mail: </label>
            <div class="col-sm-5">
                <input type="email" class="form-control" name="address" placeholder="Enter your e-mail"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Full Name: </label>
            <div class="col-sm-5">
                <input type="text" class="form-control" name="fullName" placeholder="Enter your full name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Phone Number: </label>
            <div class="col-sm-5">
                <input type="text" class="form-control" name="phone" placeholder="Enter your phone"/>
            </div>
        </div>
        <button id="submitButton" type="submit" class="btn btn-primary">Submit</button>
    </form:form>

    <jsp:include page="fragments/footer.jsp"/>

</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#loginInput").keyup(function postinput() {
            var loginValue = $("#loginInput").val();
            var loginMessage = document.getElementById("loginError");
            var submitButton = document.getElementById("submitButton");
            $.ajax({
                url: 'http://localhost:8080/user/' + loginValue,
                type: 'GET',
                success: function (response) {
                    if (response) {
                        loginMessage.innerText = "Login is already in use";
                        loginMessage.setAttribute("class", "text-danger mt-3");
                        submitButton.setAttribute("class", "btn btn-secondary");
                        submitButton.setAttribute("disabled", "disabled");
                    } else {
                        loginMessage.innerText = "Login is available";
                        loginMessage.setAttribute("class", "text-success mt-3");
                        submitButton.setAttribute("class", "btn btn-primary");
                        submitButton.removeAttribute("disabled");
                    }
                }
            });
        })
    });
</script>
</body>
</html>