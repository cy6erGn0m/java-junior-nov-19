<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="transactions" scope="request" type="java.util.List<ru.levelp.junior.entities.Transaction>"/>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>

<h1>Welcome</h1>

<table>
    <thead>
        <tr>
            <th>date</th>
            <th>amount</th>
            <th>from</th>
            <th>to</th>
        </tr>
    </thead>

    <tbody>

    <c:forEach items="${transactions}" var="transaction">
    <tr>
        <td>${transaction.time}</td>
        <td>${transaction.amount}</td>
        <td>${transaction.origin.login}</td>
        <td>${transaction.receiver.login}</td>
    </tr>
    </c:forEach>

    </tbody>
</table>

</body>
</html>
