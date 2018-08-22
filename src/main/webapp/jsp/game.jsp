<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>Game Bull and Cow</title>
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

<div>
    <form action="game" method="get">
        <table align="center">
            <tr>
                <th>Enter your guess number:</th>
                <td style="border:1px solid darkred; padding: 3px">
                    <input type="number" name="number1" min="0" max="9" value="1">
                    <input type="number" name="number2" min="0" max="9" value="2">
                    <input type="number" name="number3" min="0" max="9" value="3">
                    <input type="number" name="number4" min="0" max="9" value="4">
                </td>
                <td><input type="submit" value="Submit" class="btn"></td>
            </tr>
        </table>
    </form>
</div>
<br>

<table align="center" style="width:50%; border:1px solid black">
    <tr>
        <th style="border:1px solid black; text-align: center">Guess number</th>
        <th style="border:1px solid black; text-align: center">Bulls and Cows</th>
    </tr>
    <%
        if (session.getAttribute("gameStart") != null) {
            Map<String, String> map = (Map<String, String>) session.getAttribute("guessNumbersMap");

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String guessNumber = entry.getKey();
                String bullAndCow = entry.getValue();
    %>
    <tr>
        <td style="border:1px solid black; text-align: center"><%=guessNumber%>
        </td>
        <td style="border:1px solid black; text-align: center"><%=bullAndCow%>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
<br>

<%
    if (request.getAttribute("gameEnd") != null &&
            request.getAttribute("gameEnd").equals("true")) {
%>
<p align="center" class="text-success">Congratulations, you guessed the number!</p>
<%
    }
%>

<%
    if (request.getAttribute("wrongEnter") != null
            && request.getAttribute("wrongEnter").equals("err")) {
%>
<p align="center" class="text-warning">Please, enter only digit from 0 to 9!</p>
<%
    }
%>
<div>
    <form action="game" method="get">
        <table align="center">
            <tr>
                <th>To start a new game, click</th>
                <td><input type="submit" name="action" value="Start New Game" class="btn">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
