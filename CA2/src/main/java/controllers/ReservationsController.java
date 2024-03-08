package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.CancelingCanceledReserve;
import exceptions.CancelingExpiredReserve;
import exceptions.NotExistentReserve;
import exceptions.NotExistentUser;
import models.*;
import service.*;

@WebServlet("/reservations")
public class ReservationsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Mizdooni mizdooni = MizdooniProvider.GetInstance();
        Reserve[] reserves = mizdooni.getReserves();

        request.setAttribute("reserves", reserves);
        request.getRequestDispatcher("reservations.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        Mizdooni mizdooni = MizdooniProvider.GetInstance();
        Reserve reserve = (Reserve) request.getAttribute("reserve");
        if (action != null && action.equals("cancel")) {
           try{
               mizdooni.CancelReserve(reserve.getReserveeUsername(), reserve.getReserveNumber());
           }catch(NotExistentUser | NotExistentReserve | CancelingExpiredReserve | CancelingCanceledReserve ex){
               session.setAttribute("errorMessage", ex.getMessage());
               response.sendRedirect(request.getContextPath() + "/error");
               return;
           }


        }
        response.sendRedirect(request.getContextPath() + "/reservations");
    }
}
