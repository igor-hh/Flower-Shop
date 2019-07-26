<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
<form:form action="/login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
</form:form>
<br>
<div>
    <p>New to Flower Shop?</p>
    <p><a href="/signup">Sign up</a></p>
</div>
</body>
</html>