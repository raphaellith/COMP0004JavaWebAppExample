<%@ page import="uk.ac.ucl.model.ModelFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Note Collection System</title>
</head>
<body>
<h2>Note Collection System</h2>

<nav>
    <ol>
        <%
            ArrayList<Note> notes = ModelFactory.getModel().getIndex().getNotes();
            for (Note note : notes) {
        %>
        <li>
            <a href=<%="note.html?noteTitle=" + URLEncoder.encode(note.getTitle(), StandardCharsets.UTF_8)%>>
                <%=note.getTitle()%>
            </a>
        </li>
        <%
            }
        %>
    </ol>
</nav>

</body>
</html>