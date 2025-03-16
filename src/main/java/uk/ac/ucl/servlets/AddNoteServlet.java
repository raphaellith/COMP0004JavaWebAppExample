package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import uk.ac.ucl.model.Index;
import uk.ac.ucl.model.IndexEntryPath;
import uk.ac.ucl.model.Model;

@WebServlet("/addNote.html")
public class AddNoteServlet extends AbstractHttpServlet {
    protected String respondAndGetJSP(HttpServletRequest request, Model model) {
        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("path"));
        Index currentIndex = (Index) model.getEntryByPath(currentPath);

        // Avoids having duplicate titles called "New note"
        String newNoteDefaultTitle = "New note";
        String newNoteTitle = newNoteDefaultTitle;
        int n = 2;

        while (model.pathExists(currentPath.getNoteChildPath(newNoteTitle))) {
            System.out.println(currentPath.getNoteChildPath(newNoteTitle));
            newNoteTitle = newNoteDefaultTitle + " " + n;
            n++;
        }

        currentIndex.addNote(newNoteTitle);
        model.addJsonNode(currentPath.getNoteChildPath(newNoteTitle));

        request.setAttribute("indexObj", currentIndex);
        request.setAttribute("currentPath", currentPath);

        return "/indexView.jsp";
    }
}