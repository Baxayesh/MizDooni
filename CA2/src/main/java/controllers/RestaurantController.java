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

@WebServlet(name = "Restaurant Page", value = "/restaurants/*")
public class RestaurantController extends HttpServlet {
    private void loadPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        try {
            Mizdooni mizdooni = MizdooniProvider.GetInstance();

            String restaurant = mizdooni.getRestaurants().toString();
            Review[] feedback = mizdooni.getReviews();

            request.setAttribute("restaurant", restaurant);
            request.setAttribute("comment",Review.getComment());
            request.setAttribute("rate", mizdooni.GetRatingFor(restaurant));
            request.setAttribute("feedback", feedback);

            request.getRequestDispatcher("/restaurant.jsp").forward(request, response);
        } catch (NotExistentRestaurant e) {
            session.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("username") == null) response.sendRedirect(request.getContextPath() + "/login");
        else {
            String[] split_url = request.getRequestURI().split("/");
            loadPage(request, response, session);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(request.getParameter("comment")!= null){
            String username = (String) session.getAttribute("username");
            String commentText = request.getParameter("comment");
        }
        if (request.getParameter("date_time")!= null){
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = dateFormat.format(currentDate);
        }
        loadPage(request, response, session);
    }
}
