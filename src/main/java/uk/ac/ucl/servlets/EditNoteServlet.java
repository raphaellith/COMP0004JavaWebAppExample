package uk.ac.ucl.servlets;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/noteEditView.html")
public class EditNoteServlet extends AbstractGetNoteServlet {
    public EditNoteServlet() {
        super("/noteEditView.jsp");
    }
}