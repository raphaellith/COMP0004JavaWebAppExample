package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import uk.ac.ucl.model.Index;
import uk.ac.ucl.model.IndexEntryPath;
import uk.ac.ucl.model.Model;

@WebServlet("/indexView.html")
public class ViewIndexServlet extends AbstractHttpServlet {
    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        IndexEntryPath path = new IndexEntryPath(request.getParameter("path"));
        Index index = (Index) model.getEntryByPath(path);

        request.setAttribute("indexObj", index);
        request.setAttribute("currentPath", path);

        return "/indexView.jsp";
    }
}