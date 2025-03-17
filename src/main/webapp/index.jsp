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
        <% Model model = ModelFactory.getModel(); %>

        <a class="button" id="start-button" href=<%="/indexView.html?path=" + model.getRootIndexTitle()%>>
            Click here to view notes
        </a>
    </nav>

</body>
</html>
