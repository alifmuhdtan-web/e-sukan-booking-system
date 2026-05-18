<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>E-Sukan Test Page</title>
    </head>
    <body>
        <h1>E-Sukan System is Working!</h1>
        <p>Current time: <%= new java.util.Date() %></p>
        <p>Server: <%= application.getServerInfo() %></p>
    </body>
</html>