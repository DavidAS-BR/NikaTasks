package glitch.me.nikatasks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

            System.out.println(req.getParameter("id"));
            RequestDispatcher dispatcher = req.getRequestDispatcher("companies.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
