<%--
  Created by IntelliJ IDEA.
  User: Ali
  Date: 3/8/2024
  Time: 4:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Restaurant" %>
<%@ page import="models.Review" %>
<%@ page import="java.util.ArrayList" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Restaurants</title>
</head>
<body>
<p id="username">username: <%= session.getAttribute("username") %>
  <a href="/">Home</a>
  <a href="/logout" style="color: red">Log Out</a>
</p>
<br><br>
<form action="" method="POST">
  <label>Search:</label>
  <input type="text" name="search" value="">
  <button type="submit" name="action" value="search_by_type">Search By Type</button>
  <button type="submit" name="action" value="search_by_name">Search By Name</button>
  <button type="submit" name="action" value="search_by_city">Search By City</button>
  <button type="submit" name="action" value="clear">Clear Search</button>
</form>
<br><br>
<form action="" method="POST">
  <label>Sort By:</label>
  <button type="submit" name="action" value="sort_by_rate">Overall Score</button>
</form>
<br><br>
<table style="width:100%; text-align:center;" border="1">
  <tr>
    <th>Id</th>
    <th>Name</th>
    <th>City</th>
    <th>Type</th>
    <th>Time</th>
    <th>Service Score</th>
    <th>Food Score</th>
    <th>Ambiance Score</th>
    <th>Overall Score</th>
  </tr>
  <%
    Restaurant[] restaurants = (Restaurant[]) request.getAttribute("restaurants");
    if (restaurants != null) {
      for (Restaurant restaurant : restaurants) {
  %>
  <tr>
    <td>
      <%= restaurant.getKey()%>
    </td>
    <td><a href=<%= request.getContextPath() + "/restaurant/" + restaurant.getKey()%>><%= restaurant.getName()%></a></td>
    <td><%=restaurant.getRestaurantAddress().city()%></td>
    <td><%=restaurant.getType()%></td>
    <td><%=restaurant.getOpenTime()%> - <%=restaurant.getCloseTime()%></td>
    <%
      }
      }
    %>
    <%
      Review[] reviews = (Review[]) request.getAttribute("reviews");
      if (reviews != null) {
      for (Review review : reviews) {
    %>
    <td><%= review.getServiceScore()%></td>
    <td><%= review.getFoodScore()%></td>
    <td><%= review.getAmbianceScore()%></td>
    <td><%= review.getOverallScore()%></td>
    <%
      }
      }
    %>
  </tr>
</table>
</body>
</html>