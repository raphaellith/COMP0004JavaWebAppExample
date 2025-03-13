package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.*;

import java.io.IOException;

@WebServlet("/saveNote.html")
public class SaveNoteServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();

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
            model.updateNoteJsonNode(currentPath, newTitle, newContents);

            currentPath = potentialNewPath;

            pathToDestinationJSP = "/noteView.jsp?path=" + currentPath.getURLEncoding();
        } else {
            pathToDestinationJSP = "/noteEditView.jsp?path=" + currentPath.getURLEncoding();
        }

        request.setAttribute("noteObj", note);
        request.setAttribute("currentPath", currentPath);

        // Invoke the JSP
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(pathToDestinationJSP);
        dispatch.forward(request, response);
    }
}