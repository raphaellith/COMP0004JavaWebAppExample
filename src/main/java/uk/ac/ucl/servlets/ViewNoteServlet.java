package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/noteView.html")
public class ViewNoteServlet extends AbstractGetNoteServlet {
    public ViewNoteServlet() {
        super("/noteView.jsp");
    }
}