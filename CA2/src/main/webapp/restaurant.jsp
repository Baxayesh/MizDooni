<%--
  Created by IntelliJ IDEA.
  User: Ali
  Date: 3/8/2024
  Time: 6:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Restaurant" %>
<%@ page import="models.Review" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Restaurant</title>
</head>
<body>
<p id="username">username: <%= session.getAttribute("username") %>
    <a href="/">Home</a>
    <a href="/logout" style="color: red">Log Out</a>
</p>
<br>
<% Restaurant restaurant = (Restaurant) request.getAttribute("restaurant"); %>
<% Review review = (Review) request.getAttribute("review"); %>
<h2>Restaurant Info:</h2>
<ul>
    <li id="id">Id: <%= restaurant.getKey()%></li>
    <li id="name">Name: <%=restaurant.getName()%></li>
    <li id="type">Type: <%=restaurant.getType()%></li>
    <li id="time">Time: <%=restaurant.getOpenTime()%> - <%=restaurant.getCloseTime()%></li>
    <li id="rate">Scores:</li>
    <ul>
        <li>Food: <%= review.getFoodScore()%></li>
        <li>Service: <%= review.getServiceScore()%></li>
        <li>Ambiance: <%= review.getAmbianceScore()%></li>
        <li>Overall: <%= review.getOverallScore()%></li>
    </ul>
    <li id="address">Address: <%= restaurant.getRestaurantAddress()%></li>
    <li id="description">Description: <%= restaurant.getDescription()%></li>
</ul>


<table border="1" cellpadding="10">
    <tr>
        <td>
            <label>Reserve Table:</label>
            <form action="" method="post">
                <label>Table:</label>
                <select id="table_number" name="table_number">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                </select>
                <label>Date & Time:</label>
                <input type="datetime-local" id="date_time" name="date_time">
                <br>
                <button type="submit" name="action" value="reserve">Reserve</button>
            </form>
        </td>
    </tr>
</table>

<table border="1" cellpadding="10">
    <tr>
        <td>
            <label>Feedback:</label>
            <form action="" method="post">
                <label>Food Rate:</label>
                <input type="number" id="food_rate" name="food_rate" step="0.1" min="0" max="5">
                <label>Service Rate:</label>
                <input type="number" id="service_rate" name="service_rate" step="0.1" min="0" max="5">
                <label>Ambiance Rate:</label>
                <input type="number" id="ambiance_rate" name="ambiance_rate" step="0.1" min="0" max="5">
                <label>Overall Rate:</label>
                <input type="number" id="overall_rate" name="overall_rate" step="0.1" min="0" max="5">
                <br>
                <label>Comment:</label>
                <textarea name="comment"  id="" cols="30" rows="5" placeholder="Enter your comment"></textarea>
                <br>
                <button type="submit" name="action" value="feedback">Submit</button>
            </form>
        </td>
    </tr>
</table>
<br>

<br/>
<table style="width: 100%; text-align: center;" border="1">
    <caption><h2>Feedbacks</h2></caption>
    <tr>
        <th>Username</th>
        <th>Comment</th>
        <th>Date</th>
        <th>Food Rate</th>
        <th>Service Rate</th>
        <th>Ambiance Rate</th>
        <th>Overall Rate</th>
    </tr>
    <% if (review != null) {
        for (Review feedback : (Review[]) request.getAttribute("feedback")) {
    %>
    <tr>
        <td><%=feedback.getIssuerUsername()%></td>
        <td><%=feedback.getComment()%></td>
        <td><%=feedback.getIssueTime()%></td>
        <td><%=feedback.getFoodScore()%></td>
        <td><%= feedback.getServiceScore()%></td>
        <td><%=feedback.getAmbianceScore()%></td>
        <td><%=feedback.getOverallScore()%></td>
    </tr>
    <% }
    }%>
</table>
<br><br>
</body>
</html>

