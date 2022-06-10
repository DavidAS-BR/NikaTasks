package glitch.me.nikatasks;

import glitch.me.nikatasks.dao.CompaniesDAO;
import glitch.me.nikatasks.entities.CompanieAccessEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;

@WebServlet("/companies")
public class Companies extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usertoken = (String) req.getSession().getAttribute("authtoken");

        if (usertoken == null || usertoken.isEmpty())
            resp.sendRedirect("/");
        else {
            req.setAttribute("Teste", "teste");

            int companieID = Integer.parseInt(req.getParameter("id"));

            try {
                CompanieAccessEntity companie = CompaniesDAO.getCompanieAccess(usertoken, companieID);

                if (companie == null) {
                    System.out.println("/");

                    resp.setStatus(404);
                } else {
//                    System.out.println("Not null " + companie.getCompanieID());
//                    System.out.println(companie.getTasklist().get(0).getTaskDescription());
                    req.setAttribute("accesscompanie", companie);
                }
            } catch (Exception e) {
                resp.setStatus(401);
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("companies.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usertoken = (String) req.getSession().getAttribute("authtoken");
        int companieID = Integer.parseInt(req.getParameter("id"));
        int taskID = Integer.parseInt(req.getParameter("task"));

        System.out.println("Toggle Task");
        try {
            System.out.println(usertoken);
            System.out.println(companieID);
            System.out.println(taskID);

            boolean updateTaskStatus = CompaniesDAO.toggleTaskStatus(companieID, taskID, usertoken);

            System.out.println("Query resolt " + updateTaskStatus);
            if (updateTaskStatus) {
                resp.setStatus(200);
            } else {
                resp.setStatus(401);

                System.out.println("Query error");

                RequestDispatcher dispatcher = req.getRequestDispatcher("companies.jsp");
                dispatcher.forward(req, resp);
            }

        } catch (Exception e) {
            resp.setStatus(401);
            System.out.println("Exception error");
            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
