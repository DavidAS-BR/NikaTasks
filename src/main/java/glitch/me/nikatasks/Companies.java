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
                System.out.println(e);
                resp.setStatus(401);
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("companies.jsp");
            dispatcher.forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usertoken = (String) req.getSession().getAttribute("authtoken");

        if (req.getParameter("id") == null) {
            resp.setStatus(404);

            RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
            dispatcher.forward(req, resp);
        }

        int companieID = Integer.parseInt(req.getParameter("id"));

        if (req.getParameter("task") != null && req.getParameter("action") != null) {
            int taskID = Integer.parseInt(req.getParameter("task"));

            String action = req.getParameter("action");

            try {

                if (action.equals("update")) {
                    boolean updateTaskStatus = CompaniesDAO.toggleTaskStatus(companieID, taskID, usertoken);

                    System.out.println("Query result " + updateTaskStatus);
                    if (updateTaskStatus) {
                        resp.setStatus(200);
                    } else {
                        resp.setStatus(401);

                        System.out.println("Query error - 2");

                        RequestDispatcher dispatcher = req.getRequestDispatcher("companies.jsp");
                        dispatcher.forward(req, resp);
                    }
                }

                if (action.equals("delete")) {
                    boolean deleteTaskStatus = CompaniesDAO.deleteTask(companieID, taskID); // TODO: Validate with usertoken

                    System.out.println("Query result " + deleteTaskStatus);
                    if (deleteTaskStatus) {
                        resp.setStatus(200);
                    } else {
                        resp.setStatus(401);

                        System.out.println("Query error - 4");

                        RequestDispatcher dispatcher = req.getRequestDispatcher("companies.jsp");
                        dispatcher.forward(req, resp);
                    }
                }
            } catch (Exception e) {
                resp.setStatus(401);
                System.out.println("Exception 1");
                RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
                dispatcher.forward(req, resp);
            }
        }

        if (req.getParameter("taskdescription") != null && req.getParameter("action") == null) {
            String taskDescription = req.getParameter("taskdescription");

            try {

                boolean addTaskResult = false;

                if (taskDescription.length() > 0 && taskDescription.length() < 250) {
                    addTaskResult = CompaniesDAO.addTask(companieID, taskDescription);
                }

                if (addTaskResult) {
                    resp.setStatus(200);
                } else {
                    resp.setStatus(401);

                    System.out.println("Query error - 4");

                    RequestDispatcher dispatcher = req.getRequestDispatcher("companies.jsp");
                    dispatcher.forward(req, resp);
                }

            } catch (Exception ignored) {
                resp.setStatus(401);
                System.out.println("Exception 2");
                RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
                dispatcher.forward(req, resp);
            }
        }

        if (req.getParameter("adduserform") != null && req.getParameter("action") != null) {
            String action = req.getParameter("action");

            System.out.println("Adicionando usuário");

            String memberName = req.getParameter("adduserform");

            System.out.println("Nome: " + memberName);
            if (action.equals("add")) {

                System.out.println("Adding member");

                try {
                    boolean addMemberResult = CompaniesDAO.addMember(companieID, memberName);

                    if (addMemberResult) {
                        resp.setStatus(200);
                    } else {
                        resp.setStatus(401);

                        System.out.println("Query error - 1");

                        RequestDispatcher dispatcher = req.getRequestDispatcher("companies.jsp");
                        dispatcher.forward(req, resp);
                    }
                } catch (Exception ignored) {
                    resp.setStatus(401);
                    System.out.println("Exception 3"+ignored);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
                    dispatcher.forward(req, resp);
                }
            }
        }

        System.out.println("Toggle Task");
    }
}
