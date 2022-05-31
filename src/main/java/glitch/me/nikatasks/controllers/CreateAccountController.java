package glitch.me.nikatasks.controllers;

import glitch.me.nikatasks.dao.RegisterDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create-account")
public class CreateAccountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("create-account.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("confirmpassword");

        if (!password.equals(passwordConfirm)) {
            req.setAttribute("error", "As senhas digitadas não coincidem");

            RequestDispatcher dispatcher = req.getRequestDispatcher("create-account.jsp");
            dispatcher.forward(req, resp);
        } else {
            try {
                RegisterDAO register = new RegisterDAO();

                String registerResult = register.registrar(username, email, password);

                if (registerResult.equals("Conta criada com sucesso!")) {
                    req.setAttribute("success", "Conta criada com sucesso!");
                } else {
                    req.setAttribute("error", registerResult);
                }

            } catch (Exception e) {

                req.setAttribute("error", "Algum erro desconhecido ocorreu, tente novamente mais tarde!");
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("create-account.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
