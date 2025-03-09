<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<%
    Note note = (Note) request.getAttribute("noteObj");
%>

<head>
    <meta charset="UTF-8">
    <title>Note: <%=note.getTitle()%></title>
</head>
<body>
    <h2><%=note.getTitle()%></h2>
    <%=note.getContents()%>
</body>
</html>