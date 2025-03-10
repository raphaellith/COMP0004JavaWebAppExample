<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<%
    Note note = (Note) request.getAttribute("noteObj");
%>

<head>
    <meta charset="UTF-8">
    <title>Note: <%=note.getTitle()%>
    </title>
    <jsp:include page="meta.jsp"/>
</head>

<body>
<jsp:include page="header.jsp"/>

<form method="POST" action="/saveNote.html">
    <h1>
        Note:
        <input type="text" class="noteTitleEditInput" name="noteNewTitle" value="<%=note.getTitle()%>">
    </h1>

    <input type="submit" class="button ui-button" value="Save">

    <p>
        <textarea class="noteContentsEditInput" name="noteNewContents"><%=note.getContents()%></textarea>
    </p>

    <input type="hidden" name="currentPath" value="<%=request.getAttribute("currentPath").toString()%>">
</form>
</body>
</html>