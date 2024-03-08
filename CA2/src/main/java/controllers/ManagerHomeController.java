package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.*;
import service.*;
import  exceptions.*;

@WebServlet(name = "Manager Home Page", value = "/manager_home")
public class ManagerHomeController extends HttpServlet {
    private final List<Table> tables = new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("manager_home.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Mizdooni mizdooni = MizdooniProvider.GetInstance();
        Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
        String restaurantName = restaurant.getName();
        int tableNumber = Integer.parseInt(request.getParameter("table_number"));
        int seatsNumber = Integer.parseInt(request.getParameter("seats_number"));
        String rest_manager = request.getParameter("username");
        if((request.getParameter("table_number") != null) | (request.getParameter("seats_number") != null)){
            try{
                mizdooni.AddTable(tableNumber,restaurantName,rest_manager,seatsNumber);
            } catch (NotExistentRestaurant | NotExpectedUserRole | NotExistentUser | TableAlreadyExists | SeatNumNotPos  ex){
                session.setAttribute("errorMessage", ex.getMessage());
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }
        }
        request.setAttribute("username", rest_manager);
        request.getRequestDispatcher("manager_home.jsp").forward(request, response);
    }
}
