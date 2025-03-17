package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import uk.ac.ucl.model.Index;
import uk.ac.ucl.model.IndexEntryPath;
import uk.ac.ucl.model.Model;

@WebServlet("/addNote.html")
public class AddNoteServlet extends AbstractHttpServlet {
    // Servlet for adding a new note

    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("path"));
        Index currentIndex = (Index) model.getEntryByPath(currentPath);

        String newNoteTitle = newNoteTitle(currentPath, model);

        currentIndex.addNote(newNoteTitle);  // Update model
        model.addJsonNode(currentPath.getNoteChildPath(newNoteTitle));  // Update JSON

        request.setAttribute("indexObj", currentIndex);
        request.setAttribute("currentPath", currentPath);

        return "/indexView.jsp";
    }

    private String newNoteTitle(IndexEntryPath currentPath, Model model) {
        // Returns a possible new title for note in the Index specified by the currentPath
        // Notes in the same index should not share names

        String newNoteDefaultTitle = "New note";
        String newNoteTitle = newNoteDefaultTitle;
        int n = 2;

        while (model.pathExists(currentPath.getNoteChildPath(newNoteTitle))) {
            newNoteTitle = newNoteDefaultTitle + " " + n;
            n++;
        }

        return newNoteTitle;
    }
}