package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.IndexEntryPath;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;

import java.io.IOException;

public class AbstractGetNoteServlet extends HttpServlet {
    private final String pathToDestinationJSP;

    public AbstractGetNoteServlet(String pathToDestinationJSP) {
        this.pathToDestinationJSP = pathToDestinationJSP;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();
        IndexEntryPath path = new IndexEntryPath(request.getParameter("path"));

        Note note = (Note) model.getEntryByPath(path);

        request.setAttribute("noteObj", note);
        request.setAttribute("currentPath", path);

        // Invoke the JSP
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(pathToDestinationJSP);
        dispatch.forward(request, response);
    }
}