package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import uk.ac.ucl.model.*;

import java.util.Optional;

@WebServlet("/saveIndex.html")
public class SaveIndexServlet extends AbstractHttpServlet {
    // Servlet that saves changes made to the name of an index

    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        String newTitle = request.getParameter("indexNewTitle").strip();
        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("currentPath"));
        Index index = (Index) model.getEntryByPath(currentPath);
        String pathToDestinationJSP;

        Optional<IndexEntryPath> potentialNewPath = model.getPathIfNewTitleValid(newTitle, currentPath);

        if (potentialNewPath.isPresent()) {
            IndexEntryPath newPath = potentialNewPath.get();

            index.setTitle(newTitle);  // Update model
            model.updateIndexJsonNode(currentPath, newTitle);  // Update JSON

            currentPath = newPath;
            pathToDestinationJSP = "/indexView.jsp?path=" + currentPath.getURLEncoding();
        } else {
            pathToDestinationJSP = "/indexEditView.jsp?path=" + currentPath.getURLEncoding();
        }

        request.setAttribute("indexObj", index);
        request.setAttribute("currentPath", currentPath);

        return pathToDestinationJSP;
    }
}