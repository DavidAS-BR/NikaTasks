package glitch.me.nikatasks.Controllers;

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
        String email = req.getParameter("email");
        String senha = req.getParameter("password");

        if (Objects.equals(email, "admin@admin.com") && Objects.equals(senha, "admin")) {
            HttpSession session = req.getSession();

            session.setAttribute("authtoken", "adminadmin");

            resp.sendRedirect("/home");
        } else {
            System.out.println("Error");
        }
    }
}
