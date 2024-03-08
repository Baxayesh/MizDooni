package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Error Page", value = "/error")
public class ErrorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String error_msg = (String) session.getAttribute("errorMessage");
        request.setAttribute("errorMessage", error_msg);
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }
}

