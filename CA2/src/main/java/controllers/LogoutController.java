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

    Mizdooni service = MizdooniProvider.GetInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            service.Logout();
            response.sendRedirect("/");
        } catch (MizdooniNotAuthorizedException e) {
            throw new ServletException(e);
        }

    }
}
