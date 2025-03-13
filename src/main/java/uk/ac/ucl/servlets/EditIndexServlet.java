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

@WebServlet("/editIndex.html")
public class EditIndexServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();
        IndexEntryPath path = new IndexEntryPath(request.getParameter("path"));

        Index index = (Index) model.getEntryByPath(path);

        request.setAttribute("indexObj", index);
        request.setAttribute("currentPath", path);

        // Invoke the JSP
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/indexEditView.jsp");
        dispatch.forward(request, response);
    }
}