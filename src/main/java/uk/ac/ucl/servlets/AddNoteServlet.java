package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import uk.ac.ucl.model.Index;
import uk.ac.ucl.model.IndexEntryPath;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/addNote.html")
public class AddNoteServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();

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

        // Invoke the JSP
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/indexView.jsp");
        dispatch.forward(request, response);
    }
}