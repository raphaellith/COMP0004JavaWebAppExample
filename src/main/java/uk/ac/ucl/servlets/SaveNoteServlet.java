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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/saveNote.html")
public class SaveNoteServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();

        String newTitle = request.getParameter("noteNewTitle");
        String newContents = request.getParameter("noteNewContents");

        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("currentPath"));

        Note note = (Note) model.getEntryByPath(currentPath);

        note.setTitle(newTitle.strip());
        note.setContents(newContents.strip());

        model.updateNoteJsonNode(currentPath, newTitle.strip(), newContents.strip());

        request.setAttribute("noteObj", note);
        request.setAttribute("currentPath", currentPath);

        // Invoke the JSP
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(
                "/noteView.jsp?path=" + URLEncoder.encode(currentPath.getUnparsed(), StandardCharsets.UTF_8)
        );
        dispatch.forward(request, response);
    }
}