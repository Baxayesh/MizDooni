package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import service.*;
import models.*;
import exceptions.*;
import utils.RestaurantViewModel;
import utils.UserRole;

@WebServlet(name = "Restaurant Page", value = "/restaurants/*")
public class RestaurantController extends HttpServlet {

    Mizdooni service = MizdooniProvider.GetInstance();
    RestaurantViewModel[] activeRestaurants;

    RestaurantViewModel[] CreateViews(Restaurant[] input) throws NotExistentRestaurant {
        var viewModel = new RestaurantViewModel[input.length];
        for(int i = 0; i < input.length; i++){
            viewModel[i] = new RestaurantViewModel(
                    input[i],
                    service.GetRatingFor(input[i].getName())
            );
        }
        return viewModel;
    }

    String getPathArg(HttpServletRequest request){
        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            return null;
        }
        String[] pathParts = pathInfo.split("/");
        if(pathParts.length < 2){
            return null;
        }
        return pathParts[1];
    }



    void showRestaurantPage(String restaurantName, HttpServletRequest request, HttpServletResponse response)
            throws MizdooniException, ServletException, IOException {

        var client = service.getLoggedIn();
        var restaurant = service.FindRestaurant(restaurantName);
        var rating = service.GetRatingFor(restaurant.getName());
        var reviews = service.getReviews(restaurant.getName());
        var availableTables = service.GetAvailableTables(restaurant.getName());

        request.setAttribute("username", client.getUsername());
        request.setAttribute("restaurant", restaurant);
        request.setAttribute("rating", rating);
        request.setAttribute("reviews", reviews);
        request.setAttribute("availableTables", availableTables);

        request.getRequestDispatcher("/restaurant.jsp").forward(request, response);

    }


    void showAllRestaurants(HttpServletRequest request, HttpServletResponse response)
            throws MizdooniException, ServletException, IOException {

        activeRestaurants = (RestaurantViewModel[]) request.getAttribute("restaurants");
        if(activeRestaurants == null){
            activeRestaurants = CreateViews(service.getRestaurants());
        }
        request.setAttribute("restaurants", activeRestaurants);
        request.setAttribute("username", service.getLoggedIn().getUsername());
        request.getRequestDispatcher("/restaurants.jsp").forward(request, response);
    }

    void makeReserve(HttpServletRequest request, HttpServletResponse response) throws MizdooniException, IOException {
        var table = Integer.parseInt((String) request.getParameter("tableNumber"));
        var reserveTime = LocalDateTime.parse(request.getParameter("reserveTime"));
        var restaurant = (String) request.getParameter("restaurant");
        var user = service.getLoggedIn();
        service.ReserveATable(user.getUsername(), restaurant, table, reserveTime);
        response.sendRedirect("/reservations");
    }

    void registerFeedback(HttpServletRequest request, HttpServletResponse response) throws MizdooniException, IOException {

        var issuer = service.getLoggedIn();
        var food_rate = (Double) Double.parseDouble((String) request.getParameter("food_rate"));
        var service_rate = (Double) Double.parseDouble((String) request.getParameter("service_rate"));
        var ambiance_rate = (Double) Double.parseDouble((String) request.getParameter("ambiance_rate"));
        var overall_rate = (Double) Double.parseDouble((String) request.getParameter("overall_rate"));
        var comment = (String) request.getParameter("comment");
        var restaurant = (String) request.getParameter("restaurant");

        service.AddReview(
                issuer.getUsername(),
                restaurant,
                food_rate,
                service_rate,
                ambiance_rate,
                overall_rate,
                comment);

        response.sendRedirect("/restaurants/%s".formatted(restaurant));
    }

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
            var pathArg = getPathArg(request);
            if(pathArg == null){
                showAllRestaurants(request, response);
            }else {
                showRestaurantPage(pathArg, request, response);
            }
        }
        catch (MizdooniException e) {
            throw new ServletException(e);
        }

    }

    void redecorateRestaurants(String action, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NotExistentRestaurant {
        var query = request.getParameter("search");
        switch (action){
            case "search_by_type":
                activeRestaurants = CreateViews(service.SearchRestaurantByType(query));
                break;
            case "search_by_name":
                activeRestaurants = CreateViews(service.SearchRestaurantByName(query));
                break;
            case "search_by_city":
                activeRestaurants = CreateViews(service.SearchRestaurantByCity(query));
                break;
            case "clear":
                activeRestaurants = null;
                break;
            case "sort_by_rate":
                Arrays.sort(activeRestaurants);
                break;
        }
        request.setAttribute("restaurants", activeRestaurants);
        request.getRequestDispatcher("/restaurants").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            service.EnsureLoggedIn(UserRole.Client);
            var action = request.getParameter("action");
            var page = getPathArg(request);
            var restaurants = request.getAttribute("restaurants");
            if(restaurants != null){
                doGet(request, response);
            }
            if(page == null){
                redecorateRestaurants(action, request, response);
            }else {
                switch (action){
                    case "reserve":
                        makeReserve(request, response);
                        return;
                    case "feedback":
                        registerFeedback(request, response);
                        return;
                }
            }
            throw new MizdooniNotFoundException("Action Not Found");
        } catch (NumberFormatException | NullPointerException | DateTimeParseException ex){
            throw new ServletException(new MizdooniUserException("Invalid Input"));
        }
        catch (MizdooniException e) {
            throw new ServletException(e);
        }
    }
}
