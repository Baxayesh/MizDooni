package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import service.*;
import models.*;

@WebServlet(name = "Restaurants Page", value = "/restaurants")
public class RestaurantsController extends HttpServlet {
    private static void loadPage(HttpServletRequest request, HttpServletResponse response, ArrayList<Restaurant> restaurants, ArrayList<Review> reviews) throws ServletException, IOException {
        request.setAttribute("restaurants", restaurants);
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("Restaurants.jsp").forward(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Mizdooni mizdooni = MizdooniProvider.GetInstance();
        if (session.getAttribute("username") == null) response.sendRedirect(request.getContextPath() + "/login");
        else {
            Restaurant[] restaurants = mizdooni.getRestaurants();
            //loadPage(request, response, restaurants, );
        }
    }
}
