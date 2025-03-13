<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="uk.ac.ucl.model.Model" %>
<%@ page import="uk.ac.ucl.model.ModelFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="uk.ac.ucl.model.IndexEntryPath" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<%
    Note note = (Note) request.getAttribute("noteObj");
    IndexEntryPath currentPath = (IndexEntryPath) request.getAttribute("currentPath");
%>

<head>
    <meta charset="UTF-8">
    <title>Note: <%=note.getTitle()%></title>
    <jsp:include page="meta.jsp"/>
</head>

<body>
    <jsp:include page="header.jsp"/>

    <h1>
        Note: <span style="font-weight: normal"><%=note.getTitle()%></span>
    </h1>

    <%
        String editHref = "/noteEditView.html?path=" + currentPath.getURLEncoding();
        String backHref = "/indexView.html?path=" + currentPath.getParentPath().getURLEncoding();
    %>

    <a class="button ui-button" href=<%=backHref%>>
        Back
    </a>
    <a class="button ui-button" href=<%=editHref%>>
        Edit
    </a>

    <p>
        <%=note.getContents()%>
    </p>
</body>
</html>