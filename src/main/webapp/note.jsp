<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Note</title>
</head>
<body>
    <%
          Note note = (Note) request.getAttribute("targetNote");
    %>

    <h2><%=note.getTitle()%></h2>
    <%=note.getContents()%>
</body>
</html>