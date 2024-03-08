package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import exceptions.MizdooniNotAuthorizedException;
import  service.*;
@WebServlet(name = "Logout Page", value = "/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Mizdooni mizdooni = MizdooniProvider.GetInstance();
        HttpSession session = request.getSession(false);
        try{
            mizdooni.Logout();
            response.sendRedirect(request.getContextPath() + "/login");
        }catch (MizdooniNotAuthorizedException ex){
            session.setAttribute("errorMessage", ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }
}
