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

@WebServlet("/deleteNote.html")
public class DeleteNoteServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();

        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("path"));
        IndexEntryPath parentIndexPath = currentPath.getParentPath();
        Index parentIndex = (Index) model.getEntryByPath(parentIndexPath);

        parentIndex.removeNoteByTitle(currentPath.getTitle());
        model.deleteNoteJsonNode(currentPath);

        request.setAttribute("indexObj", parentIndex);
        request.setAttribute("currentPath", parentIndexPath);

        // Invoke the JSP
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/indexView.jsp");
        dispatch.forward(request, response);
    }
}