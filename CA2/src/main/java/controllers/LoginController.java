package controllers;

import service.*;
import models.*;
import exceptions.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login Page", value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String username = request.getParameter("username");
        String password = request.getParameter("Password");
        Mizdooni mizdooni = MizdooniProvider.GetInstance();

//        if(isValidUser(username, password)){
//            response.sendRedirect(request.getContextPath() + "/");
//            session.setAttribute("username", username);
//        } else {
//            response.sendRedirect(request.getContextPath() + "/login");
//        }

        String role = mizdooni.getUserRole(username);
        //if(isValidUser(username, password)){
        if(true){
            if(role.equals("client")) {
                response.sendRedirect(request.getContextPath() + "/client_home");

            } else if (role.equals("manager")) {
                response.sendRedirect(request.getContextPath() + "/manager_home");
            }
            session.setAttribute("username", username);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }

//            try {
//                //mizdooni.Login(username, password);
//
//
//                //response.sendRedirect(request.getContextPath() + "/200");
//            } catch (MizdooniNotAuthenticatedException e) {
//                session.setAttribute("errorMessage", e.getMessage());
//                response.sendRedirect("error.jsp");
//            }


    }
    private boolean isValidUser(String username, String password){
        Mizdooni mizdooni = MizdooniProvider.GetInstance();
        boolean flag = true;
        try {
            var user = mizdooni.FindUser(username);
            //User[] users =  mizdooni.getUsers();

//        for (User user: users){
            if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
                flag = true;
//                break;
            } else {
                flag = false;
//                break;
            }
        }
        catch (NotExistentUser ex){
            ex.getMessage();
        }

        return flag;
    }
}
