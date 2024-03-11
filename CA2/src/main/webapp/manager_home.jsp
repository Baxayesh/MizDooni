<%--
  Created by IntelliJ IDEA.
  User: Ali
  Date: 3/8/2024
  Time: 9:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Table" %>
<%@ page import="models.Restaurant" %>
<%@ page import="service.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<html lang="en"><head>
    <meta charset="UTF-8">
    <title>Manager Home</title>
</head>
<body>
<h1>Welcome <%= request.getAttribute("username") %> <a href="/logout" style="color: red">Log Out</a></h1>

<h2>Your Restaurant Information:</h2>
<% Restaurant restaurant = (Restaurant) request.getAttribute("restaurant"); %>

<ul>
    <li id="id">Id: 3</li>
    <li id="name">Name: <%=restaurant.getName()%></li>
    <li id="type">Type: <%=restaurant.getType()%></li>
    <li id="time">Time: <%=restaurant.getOpenTime()%> - <%=restaurant.getCloseTime()%></li>
    <li id="description">Description: <%=restaurant.getDescription()%></li>
    <li id="address">Address: <%=restaurant.getRestaurantAddress()%></li>
    <li id="tables">Tables:</li>
    <ul>
        <%
            for (Integer table : restaurant.getTableNumbers().toArray(Integer[]::new))
            {
        %>
        <li>Table <%= table %></li>
        <% } %>
    </ul>
</ul>

<table border="1" cellpadding="10">
    <tr>
        <td>

            <h3>Add Table:</h3>
            <form method="post" action="">
                <label>Table Number:</label>
                <input name="table_number" type="number" min="0"/>
                <br>
                <label>Seats Number:</label>
                <input name="seats_number" type="number" min="1"/>
                <br>
                <button type="submit">Add</button>
            </form>

        </td>
    </tr>
</table>


</body></html>