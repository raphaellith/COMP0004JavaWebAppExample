package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import uk.ac.ucl.model.Index;
import uk.ac.ucl.model.IndexEntryPath;
import uk.ac.ucl.model.Model;

@WebServlet("/deleteNote.html")
public class DeleteNoteServlet extends AbstractHttpServlet {
    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("path"));
        IndexEntryPath parentIndexPath = currentPath.getParentPath();
        Index parentIndex = (Index) model.getEntryByPath(parentIndexPath);

        parentIndex.removeNoteByTitle(currentPath.getTitle());
        model.deleteJsonNode(currentPath);

        request.setAttribute("indexObj", parentIndex);
        request.setAttribute("currentPath", parentIndexPath);

        return "/indexView.jsp";
    }
}