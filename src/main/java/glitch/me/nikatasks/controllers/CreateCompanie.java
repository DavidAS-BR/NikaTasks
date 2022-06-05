package glitch.me.nikatasks.controllers;

import glitch.me.nikatasks.dao.CompaniesDAO;
import glitch.me.nikatasks.entities.CompanieEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/create-companie")
public class CreateCompanie extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companieName = req.getParameter("companieName");
        String userauthtoken = (String) req.getSession().getAttribute("authtoken");
        CompaniesDAO companies = new CompaniesDAO();

        try {
            if (companieName != null) {
                boolean userCompanies = companies.createCompanie(userauthtoken, companieName);

                if (userCompanies) {
                    resp.setStatus(200);
                } else {
                    req.setAttribute("error", "Algum erro ocorreu");

                    resp.setStatus(401);

                    RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
                    dispatcher.forward(req, resp);
                }
                    
            } else {
                req.setAttribute("error", "Algum erro ocorreu");

                resp.setStatus(401);

                RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
                dispatcher.forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Create companie " + companieName + " " + userauthtoken);
    }
}
