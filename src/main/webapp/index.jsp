<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% //todo make rechecking load driver in ControllerServlet %>
<html>
<head>
    <title>Index Page</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="styles/style.css" type="text/css">
</head>
<style>
    th, td {
        padding: 8px;
    }
</style>
<body>

<jsp:include page="header.jsp" flush="true"/>

<br>
<h1 align="center">Welcome to Bull and Cow game.</h1>
<br>
<br>
<br>
<br>
<br>
<div align="center">
    <form action="controller">
        <table>
            <tr>
                <td>Registration of new users:</td>
                <td><input type="submit" name="action" value="registration" class="btn btn-block"></td>
            </tr>
            <tr>
                <td>Login for registered users:</td>
                <td><input type="submit" name="action" value="login" class="btn btn-block"></td>
            </tr>
            <tr>
                <td>Logout for users:</td>
                <td><input type="submit" name="action" value="logout" class="btn btn-block"></td>
            </tr>
            <tr>
                <td>Start a new game for registered users:</td>
                <td><input type="submit" name="action" value="game" class="btn btn-block"></td>
            </tr>
            <tr>
                <td>Table of the best results of our users:</td>
                <td><input type="submit" name="action" value="results" class="btn btn-block"></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
