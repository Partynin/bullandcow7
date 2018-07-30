<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="styles/style.css" type="text/css">
</head>
<body>

<jsp:include page="header.jsp" flush="true"/>

<br>
<br>
<br>
<br>
<h1 align="center">Login</h1>
<br>
<div>
    <form action="loginservlet" method="post">
        <table align="center">
            <tr>
                <th align="right">UserName:</th>
                <td><input type="text" name="userName" required maxlength="15"></td>
            </tr>
            <tr>
                <th align="right">Password:</th>
                <td><input type="password" name="password" required maxlength="15"></td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <input type="submit" value="LonIn" class="btn">
                </td>
            </tr>
        </table>
    </form>
</div>

<%
    if (request.getAttribute("notLoggedIn") != null &&
            request.getAttribute("notLoggedIn") == "true") {
%>
<p align="center" class="text-danger">Wrong username or password</p>
<%
    }
%>

</body>
</html>
