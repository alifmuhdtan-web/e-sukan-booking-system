<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create Booking</title>
</head>
<body>

<h2>Create Booking</h2>

<form action="CreateBookingServlet" method="post">

    User ID:
    <input type="number" name="userId" required><br><br>

    Facility ID:
    <input type="number" name="facilityId" required><br><br>

    Booking Date:
    <input type="date" name="bookingDate" required><br><br>

    Start Time:
    <input type="time" name="startTime" required><br><br>

    End Time:
    <input type="time" name="endTime" required><br><br>

    Total Cost:
    <input type="number" step="0.01" name="totalCost" required><br><br>

    <button type="submit">Book Now</button>

</form>

</body>
</html>
