<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="uk.ac.ucl.model.Index" %>
<%@ page import="uk.ac.ucl.model.Model" %>
<%@ page import="uk.ac.ucl.model.ModelFactory" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Note Collection System</title>
    <jsp:include page="meta.jsp"/>
</head>

<body>

<jsp:include page="header.jsp"/>

<%
    Index index = (Index) request.getAttribute("indexObj");
    Model model = ModelFactory.getModel();
    String currentPath = request.getAttribute("currentPath").toString();
%>

<h1>Index: <span style="font-weight: normal"><%=index.getTitle()%></span></h1>

<nav>
    <%
        ArrayList<String> parsedPath = model.parsePath(currentPath);
        parsedPath.removeLast();
        if (!parsedPath.isEmpty()) {
            String backHref = "/indexView.html?path=" + model.unparsePath(parsedPath);
    %>
        <a class="button ui-button" href=<%=backHref%>>
            Back
        </a>
    <%}%>

    <h3>Sub-indices:</h3>

    <%
        ArrayList<Index> indices = index.getIndexEntries();
        if (indices.isEmpty()) {
    %>

    <i>No sub-indices here.</i>

    <% } else { %>

    <ol>
        <% for (Index i : indices) { %>
        <li>
            <a href=<%="indexView.html?path=" + currentPath + URLEncoder.encode("/" + i.getTitle(), StandardCharsets.UTF_8)%>>
                <%=i.getTitle()%>
            </a>
        </li>
        <% } %>
    </ol>
    <% } %>

    <div style="margin-bottom: 3em;"></div>

    <h3>Notes:</h3>

    <%
        ArrayList<Note> notes = index.getNoteEntries();
        if (notes.isEmpty()) {
    %>

    <i>No notes here.</i>

    <% } else { %>

    <ol>
        <% for (Note n : notes) { %>
        <li>
            <a href=<%="noteView.html?path=" + currentPath + URLEncoder.encode("/!" + n.getTitle(), StandardCharsets.UTF_8)%>>
                <%=n.getTitle()%>
            </a>
        </li>
        <%}%>
    </ol>
    <%}%>
</nav>

</body>
</html>