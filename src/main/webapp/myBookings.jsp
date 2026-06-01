<%@ page import="java.util.List" %>
<%@ page import="model.Booking" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Bookings</title>
</head>
<body>

<h2>My Bookings</h2>

<table border="1">

<tr>
    <th>ID</th>
    <th>Date</th>
    <th>Start</th>
    <th>End</th>
    <th>Cost</th>
    <th>Status</th>
    <th>Action</th>
</tr>

<%
List<Booking> bookingList =
        (List<Booking>) request.getAttribute("bookingList");

if (bookingList != null) {

    for (Booking b : bookingList) {
%>

<tr>

    <td><%= b.getId() %></td>

    <td><%= b.getBookingDate() %></td>

    <td><%= b.getStartTime() %></td>

    <td><%= b.getEndTime() %></td>

    <td>RM <%= b.getTotalCost() %></td>

    <td><%= b.getStatus() %></td>

    <td>
        <a href="CancelBookingServlet?id=<%= b.getId() %>">
            Cancel
        </a>
    </td>

</tr>

<%
    }
}
%>

</table>

</body>
</html>
