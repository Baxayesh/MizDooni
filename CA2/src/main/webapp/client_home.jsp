<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Client Home</title>
</head>
<body>
<h1>Welcome <%= request.getAttribute("username") %> <a href="${pageContext.request.contextPath}/logout" style="color: red">Log Out</a></h1>

<ul type="square">
    <li>
        <a href="http://localhost:8080/restaurants">Restaurants</a>
    </li>
    <li>
        <a href="http://localhost:8080/reservations">Reservations</a>
    </li>
</ul>

</body>
</html>