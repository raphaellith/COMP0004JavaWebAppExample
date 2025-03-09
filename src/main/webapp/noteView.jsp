<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<%
    Note note = (Note) request.getAttribute("noteObj");
%>

<head>
    <meta charset="UTF-8">
    <title>Note: <%=note.getTitle()%></title>
    <jsp:include page="meta.jsp"/>
</head>

<body>
    <jsp:include page="header.jsp"/>

    <h1>Note: <span style="font-weight: normal"><%=note.getTitle()%></span></h1>

    <%=note.getContents()%>
</body>
</html>