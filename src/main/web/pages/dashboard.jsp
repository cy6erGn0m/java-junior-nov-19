<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="transactions" scope="request" type="java.util.List<ru.levelp.junior.entities.Transaction>"/>
<html>
<head>
    <title>Dashboard</title>
    <script type="text/javascript">
        accountId = ${accountId};
    </script>
    <script type="text/javascript" src="/script/dashboard.js"></script>
</head>
<body onload="loadTransactions(accountId)">

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

    <tbody id="transactions-list">

    <c:forEach items="${transactions}" var="transaction">
    <tr>
        <td>${transaction.time}</td>
        <td>${transaction.amount}</td>
        <td>${transaction.origin.login}</td>
        <td>${transaction.receiver.login}</td>

        <%-- XSS <c:out value="${transaction.receiver.login}" escapeXml="true"/>--%>
    </tr>
    </c:forEach>

    <tr>
        <td colspan="4">
            Loading...
        </td>
    </tr>

    </tbody>
</table>

</body>
</html>
