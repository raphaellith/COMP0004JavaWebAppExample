<%@ page import="uk.ac.ucl.model.ModelFactory" %>
<%@ page import="uk.ac.ucl.model.Model" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Note Collection System</title>
    <jsp:include page="meta.jsp"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<nav>
    <%
        Model model = ModelFactory.getModel();
    %>
    <a href=<%="/indexView.html?path=" + model.getRootIndexName()%> class="button"> Click here to view notes</a>
</nav>

</body>
</html>
