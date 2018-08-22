<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="styles/style.css" type="text/css">
</head>
<body>

<jsp:include page="header.jsp" flush="true"/>

<br>
<br>
<br>
<br>
<h1 align="center">Registration</h1>
<br>

<form action="registrationservlet" method="post">
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
            <th align="right">Email:</th>
            <td><input type="email" name="email"></td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="reset" class="btn" value="Reset">
                <input type="submit" class="btn" value="Submit">
            </td>
        </tr>
    </table>
</form>

<%
    if (request.getAttribute("userNameUsed") != null &&
            request.getAttribute("userNameUsed").equals("true")) {
%>
<p align=center class=text-danger>
    The user name <b><%=request.getAttribute("userName")%>
</b> has been taken. Please select another name.
</p>
<%
    }
%>
<%
    if (request.getAttribute("userAdded") != null &&
            request.getAttribute("userAdded").equals("1")) {
%>
<p align=center class=text-success>Successfully added one user.</p>
<%
    }
%>

</body>
</html>
