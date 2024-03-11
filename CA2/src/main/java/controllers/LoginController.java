package controllers;

import service.*;
import models.*;
import exceptions.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login Page", value = "/login")
public class LoginController extends HttpServlet {

    Mizdooni service = MizdooniProvider.GetInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            service.Login(username, password);
            response.sendRedirect("/");

        }catch (MizdooniException ex){
            throw new ServletException(ex);
        }

    }
}
