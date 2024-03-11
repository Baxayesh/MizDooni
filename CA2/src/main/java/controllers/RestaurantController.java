package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import service.*;
import models.*;
import exceptions.*;
import utils.UserRole;

@WebServlet(name = "Restaurant Page", value = "/restaurant/*")
public class RestaurantController extends HttpServlet {

    Mizdooni service = MizdooniProvider.GetInstance();


    Restaurant getRestaurant(HttpServletRequest request) throws NotExistentRestaurant{

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String restaurantName = pathParts[1];
        return service.FindRestaurant(restaurantName);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            service.EnsureLoggedIn(UserRole.Client);
            var client = service.getLoggedIn();
            var restaurant = getRestaurant(request);
            var rating = service.GetRatingFor(restaurant.getName());
            var reviews = service.getReviews(restaurant.getName());
            var availableTables = service.GetAvailableTables(restaurant.getName());

            request.setAttribute("username", client.getUsername());
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("rating", rating);
            request.setAttribute("reviews", reviews);
            request.setAttribute("availableTables", availableTables);

            request.getRequestDispatcher("/restaurant_page.jsp").forward(request, response);

        }
        catch (MizdooniException e) {
            throw new ServletException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        response.sendRedirect(request.getPathInfo());
    }
}
