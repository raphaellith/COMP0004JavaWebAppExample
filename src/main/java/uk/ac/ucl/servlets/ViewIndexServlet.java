package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import uk.ac.ucl.model.Index;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

// Displays a given note
// The url http://localhost:8080/indexView.html is mapped to calling doGet on the servlet object.
@WebServlet("/indexView.html")
public class ViewIndexServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get the data from the model
        Model model = ModelFactory.getModel();
        String path = request.getParameter("path");

        Index index = (Index) model.getEntryByPath(path);

        request.setAttribute("indexObj", index);
        request.setAttribute("currentPath", path);

        // Invoke the JSP
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/indexView.jsp");
        dispatch.forward(request, response);
    }
}