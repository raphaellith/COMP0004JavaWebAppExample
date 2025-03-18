package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import uk.ac.ucl.model.*;

@WebServlet("/indexView.html")
public class ViewIndexServlet extends AbstractHttpServlet {
    // Servlet that leads to a page where an index can be viewed

    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        IndexEntryPath path = new IndexEntryPath(request.getParameter("path"));

        NoteOrdering ordering = getNoteOrderingFromRequest(request);

        Index index = (Index) model.getEntryByPath(path);

        request.setAttribute("indexObj", index);
        request.setAttribute("currentPath", path);
        request.setAttribute("ordering", ordering);

        return "/indexView.jsp";
    }

    protected NoteOrdering getNoteOrderingFromRequest(HttpServletRequest request) {
        String orderingRequested = request.getParameter("ordering");

        if (orderingRequested == null) {
            return NoteOrdering.NONE;
        }

        return switch (orderingRequested) {
            case "az" -> NoteOrdering.A_TO_Z;
            case "za" -> NoteOrdering.Z_TO_A;
            case "sl" -> NoteOrdering.SHORTEST_TO_LONGEST;
            case "ls" -> NoteOrdering.LONGEST_TO_SHORTEST;
            default -> NoteOrdering.NONE;
        };
    }
}