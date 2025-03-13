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

@WebServlet("/addIndex.html")
public class AddIndexServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();

        IndexEntryPath currentPath = new IndexEntryPath(request.getParameter("path"));
        Index currentIndex = (Index) model.getEntryByPath(currentPath);

        // Avoids having duplicate titles called "New index"
        String newIndexDefaultTitle = "New index";
        String newIndexTitle = newIndexDefaultTitle;
        int n = 2;


        while (model.pathExists(currentPath.getIndexChildPath(newIndexTitle))) {
            System.out.println(currentPath.getIndexChildPath(newIndexTitle));
            newIndexTitle = newIndexDefaultTitle + " " + n;
            n++;
        }

        currentIndex.addIndex(newIndexTitle);
        model.addJsonNode(currentPath.getIndexChildPath(newIndexTitle));

        request.setAttribute("indexObj", currentIndex);
        request.setAttribute("currentPath", currentPath);

        // Invoke the JSP
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/indexView.jsp");
        dispatch.forward(request, response);
    }
}