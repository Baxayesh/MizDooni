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
    private static void loadPage(HttpServletRequest request, HttpServletResponse response, Restaurant[] restaurants, Review[] reviews) throws ServletException, IOException {
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
            Review[] reviews = mizdooni.getReviews();
            loadPage(request, response, restaurants, reviews);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Mizdooni mizdooni = MizdooniProvider.GetInstance();
        String action = request.getParameter("action");
        if (action == null) {
            doGet(request, response);
            return;
        }

        Restaurant[] restaurants = null;
        Review[] reviews = null;
        switch (action){
            case "search_by_type" -> {
                String type = request.getParameter("search");
                restaurants = mizdooni.SearchRestaurantByType(type);
                session.setAttribute("filteredRestuarants",restaurants);
            }
            case "search_by_name" -> {
                String name = request.getParameter("search");
                restaurants = mizdooni.SearchRestaurantByName(name);
                session.setAttribute("filteredRestuarants",restaurants);
            }
            case "search_by_city" -> {
                String city = request.getParameter("search");
                restaurants = mizdooni.SearchRestaurantByCity(city);
                session.setAttribute("filteredRestuarants",restaurants);
            }
            case "clear" -> {
                restaurants = mizdooni.getRestaurants();
                session.removeAttribute("filteredRestuarants");
            }
            case "sort_by_rate" -> {
                if (session.getAttribute("filteredRestuarants") != null)
                    restaurants = (Restaurant[]) session.getAttribute("filteredRestuarants");
                else restaurants = mizdooni.getRestaurants();
                //restaurants = mizdooni.
                //sort by rate?

            }
        }
        loadPage(request, response, restaurants, reviews);
    }
}
