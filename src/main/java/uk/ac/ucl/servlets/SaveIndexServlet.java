package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.*;

import java.io.IOException;

@WebServlet("/saveIndex.html")
public class SaveIndexServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();

        String newTitle = request.getParameter("indexNewTitle").strip();

        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("currentPath"));
        Index index = (Index) model.getEntryByPath(currentPath);
        String pathToDestinationJSP;

        IndexEntryPath potentialNewPath = currentPath.getParentPath().getIndexChildPath(newTitle);

        boolean validTitle = !(
                newTitle.contains("!") ||
                newTitle.contains("/") ||
                (!newTitle.equals(currentPath.getTitle()) && model.pathExists(potentialNewPath))
        );

        if (validTitle) {
            index.setTitle(newTitle);
            model.updateIndexJsonNode(currentPath, newTitle);

            currentPath = potentialNewPath;

            pathToDestinationJSP = "/indexView.jsp?path=" + currentPath.getURLEncoding();
        } else {
            pathToDestinationJSP = "/indexEditView.jsp?path=" + currentPath.getURLEncoding();
        }

        request.setAttribute("indexObj", index);
        request.setAttribute("currentPath", currentPath);

        // Invoke the JSP
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(pathToDestinationJSP);
        dispatch.forward(request, response);
    }
}