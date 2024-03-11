<%--
  Created by IntelliJ IDEA.
  User: Ali
  Date: 3/8/2024
  Time: 10:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Reserve" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Reserve" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Reservations</title>
</head>
<body>
<p id="username">username: <%= request.getAttribute("username") %>
  <a href="/">Home</a>
  <a href="/logout" style="color: red">Log Out</a>
</p>
<h1>Your Reservations:</h1>
<br><br>
<br><br>
<table style="width:100%; text-align:center;" border="1">
  <tr>
    <th>Reservation Id</th>
    <th>Restaurant Name</th>
    <th>Table Number</th>
    <th>Date & Time</th>
    <th>Canceling</th>
  </tr>
  <%
    Reserve[] reserves = (Reserve[]) request.getAttribute("reserves");
    if (reserves != null) {
    for (Reserve reserve: reserves){
  %>
  <tr>
    <td><%=reserve.getReserveNumber()%></td>
    <td><a href=<%= request.getContextPath() + "/restaurants/" + reserve.getTable().getRestaurant().getName()%>><%= reserve.getTable().getRestaurant().getName()%></a></td>
    <td><%=reserve.getTable().getTableNumber()%></td>
    <td><%=reserve.GetReserveTime()%></td>
    <td>
      <form action="" method="POST">
        <input type="hidden" name="reserveNumber" value=<%=reserve.getReserveNumber()%>>
        <button type="submit" name="action" value="cancel">Cancel This</button>
      </form>
    </td>
  </tr>
  <%}
  }
  %>
</table>
</body>
</html>
