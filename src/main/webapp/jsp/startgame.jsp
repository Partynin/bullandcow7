<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Start Game</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="styles/style.css" type="text/css">
</head>
<body>

<div class="header">
    <a href="index.jsp" class="logo">Bull and Cow</a>
    <div class="header-right">
        <a class="active" href="index.jsp">Home</a>
        <a href="login.jsp">Login</a>
        <a href="registration.jsp">Registration</a>
    </div>
</div>
<br>
<h2 align="center">Bull and Cow Game.</h2>
<br>
<br>
<br>
<br>
<br>
<br>
<form align="center" action="game" method="get">
    <input type="submit" name="action" value="Start Game" class="btn btn-primary btn-lg">
</form>
</body>
</html>
