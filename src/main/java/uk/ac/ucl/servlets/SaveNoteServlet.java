package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import uk.ac.ucl.model.*;

@WebServlet("/saveNote.html")
public class SaveNoteServlet extends AbstractHttpServlet {
    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        String newTitle = request.getParameter("noteNewTitle").strip();
        String newContents = request.getParameter("noteNewContents").strip();

        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("currentPath"));
        Note note = (Note) model.getEntryByPath(currentPath);
        String pathToDestinationJSP;

        IndexEntryPath potentialNewPath = currentPath.getParentPath().getNoteChildPath(newTitle);

        boolean validTitle = !(
                newTitle.contains("!") ||
                        newTitle.contains("/") ||
                        (!newTitle.equals(currentPath.getTitle()) && model.pathExists(potentialNewPath))
        );

        if (validTitle) {
            note.setTitle(newTitle);
            note.setContents(newContents);
            System.out.println("HERE");
            model.updateNoteJsonNode(currentPath, newTitle, newContents);

            currentPath = potentialNewPath;

            pathToDestinationJSP = "/noteView.jsp?path=" + currentPath.getURLEncoding();
        } else {
            pathToDestinationJSP = "/noteEditView.jsp?path=" + currentPath.getURLEncoding();
        }

        request.setAttribute("noteObj", note);
        request.setAttribute("currentPath", currentPath);

        return pathToDestinationJSP;
    }
}