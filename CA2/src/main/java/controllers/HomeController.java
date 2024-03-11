package controllers;

import models.Restaurant;
import models.User;
import service.*;
import exceptions.*;
import utils.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Home Page", value = "/")
public class HomeController extends HttpServlet {

    Mizdooni service = MizdooniProvider.GetInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            var current = service.getLoggedIn();

            request.setAttribute("username", current.getUsername());
            if(current.RoleIs(UserRole.Manager)){
                var restaurant = service.GetRestaurantFor(current.getUsername());

                request.setAttribute("restaurant", restaurant);
                request.getRequestDispatcher("/manager_home.jsp").forward(request, response);
            }else {
                request.getRequestDispatcher("/client_home.jsp").forward(request, response);
            }
        } catch (MizdooniNotAuthorizedException e) {
            response.sendRedirect("/login");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            var manager = service.getLoggedIn();

            var restaurant = service.GetRestaurantFor(manager.getUsername());
            int tableNumber = Integer.parseInt(request.getParameter("table_number"));
            int seatsNumber = Integer.parseInt(request.getParameter("seats_number"));

            service.AddTable(tableNumber, restaurant.getName(), manager.getUsername(), seatsNumber);

            request.getRequestDispatcher("/200.jsp").forward(request, response);
        } catch (NumberFormatException e){
            throw new ServletException(new MizdooniUserException("Invalid Number"));
        }
        catch (MizdooniException  e) {
            throw new ServletException(e);
        }
    }
}
