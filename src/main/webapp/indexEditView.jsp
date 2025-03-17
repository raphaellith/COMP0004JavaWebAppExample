<%@ page import="java.util.ArrayList" %>
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

    <form method="POST" action="saveIndex.html">
        <% Index index = (Index) request.getAttribute("indexObj"); %>

        <h1>
            Index:
            <input type="text" class="indexTitleEditInput" name="indexNewTitle" value="<%=index.getTitle()%>">
        </h1>

        <div style="margin-bottom: 0.75em; font-weight: bold">
            The title of an index cannot contain the characters "/" or "!".
            Duplicate sub-index titles in the same index are not allowed.
        </div>

        <input type="submit" class="button ui-button" value="Save">

        <input type="hidden" name="currentPath" value="<%=request.getAttribute("currentPath")%>">
    </form>

    <div>
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
            <li><%=indexTitle%></li>
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
            <li><%=n.getTitle()%></li>
            <%}%>
        </ol>
        <%}%>
    </div>
</body>
</html>