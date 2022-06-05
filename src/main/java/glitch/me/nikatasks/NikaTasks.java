package glitch.me.nikatasks;

import glitch.me.nikatasks.dao.CompaniesDAO;
import glitch.me.nikatasks.entities.CompanieEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet("/home")
public class NikaTasks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String usertoken = (String) request.getSession().getAttribute("authtoken");

        if (usertoken == null || usertoken.isEmpty())
            response.sendRedirect("/");
        else {
            CompaniesDAO getUserComanies = new CompaniesDAO();

            try {
                ArrayList<CompanieEntity> userCompanies = getUserComanies.getUserCompanies(usertoken);

                if (userCompanies == null) {
                    System.out.println("null");
                }

                request.setAttribute("userCompanies", userCompanies);

            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}