<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>

    <style type="text/css">
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h1>Hello</h1>

<c:if test="${empty sessionScope['accountId']}">
<form method="post" action="/login">
    <p>Login: <input type="text" name="login" value="${param['login']}"></p>
    <p>Password: <input type="password" name="password"></p>

    <security:csrfInput/>

    <p><input type="submit"></p>
</form>

<a href="/register">Register</a>
</c:if>

<c:if test="${not empty sessionScope['accountId']}">
    <p><a href="/dashboard">Dashboard</a></p>
</c:if>

<c:if test="${not empty param['login']}">
<p class="error">
    Login or password is incorrect!
</p>
</c:if>

</body>
</html>
