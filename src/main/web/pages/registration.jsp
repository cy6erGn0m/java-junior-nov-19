<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form:form action="/register" method="post" modelAttribute="form">
    <p>Login:
        <form:input type="text" path="login" />

        <form:errors path="login" cssStyle="color: red" />
    </p>
    <p>
        Password:
        <form:password path="password" />

        <form:errors path="password" cssStyle="color: red" />
    </p>
    <p>
        Confirm password:
        <form:password path="passwordConfirmation" />

        <form:errors path="passwordConfirmation" cssStyle="color: red" />
    </p>

    <input type="submit" value="Register">
</form:form>

<c:if test="${not empty error}">
    <p style="color: red">${error}</p>
</c:if>
</body>
</html>
