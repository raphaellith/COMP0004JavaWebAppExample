package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletRequest;
import uk.ac.ucl.model.IndexEntryPath;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.Note;

@WebServlet("/noteEditView.html")
public class EditNoteServlet extends AbstractHttpServlet {
    // Servlet that leads to a page where an existing note can be renamed or edited

    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        IndexEntryPath path = new IndexEntryPath(request.getParameter("path"));
        Note note = (Note) model.getEntryByPath(path);

        request.setAttribute("noteObj", note);
        request.setAttribute("currentPath", path);

        return "/noteEditView.jsp";
    }
}