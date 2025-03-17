package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import uk.ac.ucl.model.*;

@WebServlet("/editIndex.html")
public class EditIndexServlet extends AbstractHttpServlet {
    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        IndexEntryPath path = new IndexEntryPath(request.getParameter("path"));
        Index index = (Index) model.getEntryByPath(path);

        request.setAttribute("indexObj", index);
        request.setAttribute("currentPath", path);

        return "/indexEditView.jsp";
    }
}