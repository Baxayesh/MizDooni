package controllers;

import service.*;
import exceptions.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login Page", value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("username");
        String password = request.getParameter("Password");
        Mizdooni mizdooni = MizdooniProvider.GetInstance();
        if(request.getParameter("login") != null){
            try {
                HttpSession session = request.getSession();
                mizdooni.Login(userId, password);
                String role = mizdooni.getUserRole(userId);
                if ("client".equals(role)) {
                    response.sendRedirect("client_home.jsp");
                } else if ("manager".equals(role)) {
                    response.sendRedirect("manager_home.jsp");
                } else {
                    response.sendRedirect("login.jsp");
                }

                session.setAttribute("username", userId);

                response.sendRedirect(request.getContextPath() + "/200");
            } catch (MizdooniNotAuthenticatedException e) {
                HttpSession session = request.getSession(false);
                session.setAttribute("errorMessage", e.getMessage());
                response.sendRedirect(request.getContextPath() + "/error");
            }
        }

    }
}
