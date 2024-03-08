<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Client Home</title>
</head>
<body>
<h1>Welcome <%= session.getAttribute("username") %> <a href="${pageContext.request.contextPath}/logout" style="color: red">Log Out</a></h1>

<ul type="square">
    <li>
        <a href="${pageContext.request.contextPath}/restaurants">Restaurants</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/reservations">Reservations</a>
    </li>
</ul>

</body>
</html>