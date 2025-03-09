<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="uk.ac.ucl.model.Index" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Note Collection System</title>
</head>
<body>
<h2>Note Collection System</h2>

<nav>

  <%
    Index index = (Index) request.getAttribute("indexObj");
    String currentPath = request.getAttribute("currentPath").toString();
  %>

  <h3>Indices:</h3>

  <ol>
    <%
      ArrayList<Index> indices = index.getIndexEntries();
      for (Index i : indices) {
    %>
    <li>
      <a href=<%="indexView.html?path=" + currentPath + URLEncoder.encode("/" + i.getTitle(), StandardCharsets.UTF_8)%>>
        <%=i.getTitle()%>
      </a>
    </li>
    <%
      }
    %>
  </ol>

  <h3>Notes:</h3>

  <ol>
    <%
      ArrayList<Note> notes = index.getNoteEntries();
      for (Note n : notes) {
    %>
    <li>
      <a href=<%="noteView.html?path=" + currentPath + URLEncoder.encode("/!" + n.getTitle(), StandardCharsets.UTF_8)%>>
        <%=n.getTitle()%>
      </a>
    </li>
    <%
      }
    %>
  </ol>
</nav>

</body>
</html>