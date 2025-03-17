package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import uk.ac.ucl.model.*;

import java.util.Optional;

@WebServlet("/saveNote.html")
public class SaveNoteServlet extends AbstractHttpServlet {
    // Servlet that saves changes made to the name/contents of a note

    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        String newTitle = request.getParameter("noteNewTitle").strip();  // Strip title to remove whitespaces
        String newContents = request.getParameter("noteNewContents");
        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("currentPath"));
        Note note = (Note) model.getEntryByPath(currentPath);
        String pathToDestinationJSP;

        Optional<IndexEntryPath> potentialNewPath = model.getPathIfNewTitleValid(newTitle, currentPath);

        if (potentialNewPath.isPresent()) {
            IndexEntryPath newPath = potentialNewPath.get();

            // Escape HTML to prevent injection
            newContents = TextFormatter.escapeHTML(newContents);

            // Update model
            note.setTitle(newTitle);
            note.setContents(newContents);

            // Update JSON
            model.updateNoteJsonNode(currentPath, newTitle, newContents);

            currentPath = newPath;
            pathToDestinationJSP = "/noteView.jsp?path=" + currentPath.getURLEncoding();
        } else {
            pathToDestinationJSP = "/noteEditView.jsp?path=" + currentPath.getURLEncoding();
        }

        request.setAttribute("noteObj", note);
        request.setAttribute("currentPath", currentPath);

        return pathToDestinationJSP;
    }
}