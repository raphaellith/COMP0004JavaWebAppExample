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

        // Avoids having duplicate titles called "New index"
        String newIndexDefaultTitle = "New index";
        String newIndexTitle = newIndexDefaultTitle;
        int n = 2;

        while (model.pathExists(currentPath.getIndexChildPath(newIndexTitle))) {
            System.out.println(currentPath.getIndexChildPath(newIndexTitle));
            newIndexTitle = newIndexDefaultTitle + " " + n;
            n++;
        }

        currentIndex.addIndex(newIndexTitle);
        model.addJsonNode(currentPath.getIndexChildPath(newIndexTitle));

        request.setAttribute("indexObj", currentIndex);
        request.setAttribute("currentPath", currentPath);

        return "/indexView.jsp";
    }
}