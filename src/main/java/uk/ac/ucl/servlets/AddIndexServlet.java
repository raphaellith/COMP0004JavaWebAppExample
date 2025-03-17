package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import uk.ac.ucl.model.Index;
import uk.ac.ucl.model.IndexEntryPath;
import uk.ac.ucl.model.Model;

@WebServlet("/addIndex.html")
public class AddIndexServlet extends AbstractHttpServlet {
    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("path"));
        Index currentIndex = (Index) model.getEntryByPath(currentPath);

        String newIndexTitle = newIndexTitle(currentPath, model);

        currentIndex.addIndex(newIndexTitle);  // Update model
        model.addJsonNode(currentPath.getIndexChildPath(newIndexTitle));  // Update JSON

        request.setAttribute("indexObj", currentIndex);
        request.setAttribute("currentPath", currentPath);

        return "/indexView.jsp";
    }

    private String newIndexTitle(IndexEntryPath currentPath, Model model) {
        // Returns a possible new title for a sub-index of the Index specified by the currentPath
        // Sub-indices of the same index should not share names

        String newIndexDefaultTitle = "New index";
        String newIndexTitle = newIndexDefaultTitle;
        int n = 2;

        while (model.pathExists(currentPath.getIndexChildPath(newIndexTitle))) {
            System.out.println(currentPath.getIndexChildPath(newIndexTitle));
            newIndexTitle = newIndexDefaultTitle + " " + n;
            n++;
        }

        return newIndexTitle;
    }
}