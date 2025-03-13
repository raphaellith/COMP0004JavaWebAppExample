<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="uk.ac.ucl.model.*" %>

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
    IndexEntryPath currentPath = (IndexEntryPath) request.getAttribute("currentPath");
%>

<h1>Index: <span style="font-weight: normal"><%=index.getTitle()%></span></h1>

<nav>
    <%
        IndexEntryPath parentPath = currentPath.getParentPath();
        if (!parentPath.isEmpty()) {
            String backHref = "/indexView.html?path=" + parentPath.getUnparsed();
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
        <% for (Index i : indices) {
            String indexTitle = i.getTitle();
        %>
        <li>
            <a href=<%="indexView.html?path=" + currentPath.getChildPath(indexTitle).getURLEncoding()%>>
                <%=indexTitle%>
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
            <a href=<%="noteView.html?path=" + currentPath.getChildPath("!" + n.getTitle()).getURLEncoding()%>>
                <%=n.getTitle()%>
            </a>
        </li>
        <%}%>
    </ol>
    <%}%>
</nav>

</body>
</html>