<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="dbBean" scope="application" class="app.entities.DbBean"/>
<html>
<head>
    <title>Results</title>
    <link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="styles/style.css" type="text/css">
</head>
<body>

<jsp:include page="header.jsp" flush="true"/>

<br>
<br>
<br>
<br>
<h1 align="center">Users results and High scores.</h1>
<br>
<table align="center" style="width:50%; border:1px solid black">
    <tr>
        <th style="border:1px solid black; text-align: center">Position</th>
        <th style="border:1px solid black; text-align: center">User Name</th>
        <th style="border:1px solid black; text-align: center">Average number of attempts</th>
    </tr>
    <%
        Map<String, Integer> map = dbBean.getResultsMap();
        int position = 0;
        if (map != null) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                int average = entry.getValue();
                String userName = entry.getKey();
                position++;
    %>
    <tr>
        <td style="border:1px solid black; text-align: center"><%=position%>
        </td>
        <td style="border:1px solid black; text-align: center"><%=userName%>
        </td>
        <td style="border:1px solid black; text-align: center"><%=average%>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>
