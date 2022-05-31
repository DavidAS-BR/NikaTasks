package glitch.me.nikatasks.controllers;

import glitch.me.nikatasks.dao.LoginDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usr = req.getParameter("emailorusername");
        String pwd = req.getParameter("password");

        LoginDAO login = new LoginDAO();

        try {
            String loginResult = login.authenticate(usr, pwd);

            HttpSession session = req.getSession();

            if (loginResult != null) {
                System.out.println("Logged! With session: " + loginResult + " forward to dashboard");

                session.setAttribute("authtoken", loginResult);

                resp.setStatus(200);
            } else {
                System.out.println("Usuário ou senha incorretos");

                req.setAttribute("error", "Usuário ou senha incorretos");

                resp.setStatus(401);

                RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");

                dispatcher.forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
