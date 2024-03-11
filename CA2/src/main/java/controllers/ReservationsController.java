package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.*;
import models.*;
import service.*;
import utils.UserRole;

@WebServlet("/reservations")
public class ReservationsController extends HttpServlet {

    Mizdooni service = MizdooniProvider.GetInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{

            service.EnsureLoggedIn(UserRole.Client);
            var client = service.getLoggedIn();
            var reserves = service.GetActiveReserves(client.getUsername());

            request.setAttribute("username", client.getUsername());
            request.setAttribute("reserves", reserves);
            request.getRequestDispatcher("reservations.jsp").forward(request, response);

        }catch (MizdooniException ex){
            throw new ServletException(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            var client = service.getLoggedIn();
            var reserveNumber = Integer.parseInt(request.getParameter("reserveNumber"));

            service.CancelReserve(client.getUsername(), reserveNumber);

            response.sendRedirect("/reservations");
        }
        catch (NumberFormatException ex){
            throw new ServletException(new MizdooniUserException("Invalid Number"));
        }
        catch (MizdooniException ex) {
            throw new ServletException(ex);
        }
    }
}
