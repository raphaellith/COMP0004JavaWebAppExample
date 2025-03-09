<%@ page import="uk.ac.ucl.model.ModelFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="uk.ac.ucl.model.Model" %>
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
        Model model = ModelFactory.getModel();
    %>
    <a href=<%="/indexView.html?path=" + model.getRootIndexName()%>> Click here!</a>
</nav>

</body>
</html>
