package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import uk.ac.ucl.model.*;

@WebServlet("/saveIndex.html")
public class SaveIndexServlet extends AbstractHttpServlet {
    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
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

        return pathToDestinationJSP;
    }
}