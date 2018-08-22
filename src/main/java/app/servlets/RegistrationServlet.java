package app.servlets;

import app.entities.DbBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Registers a new user if the name is not used.
 *
 * @author Partynin - <a href="mailto:partinin@bk.ru">partinin@bk.ru</a>
 */
public class RegistrationServlet extends HttpServlet {

    /**
     * Obtains the user name, password, email from request parameter and dbBean from context.
     * Checks if username is available and add the new user in the DB.
     *
     * @param request  the request information for the servlet
     * @param response the response information for the servlet
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        ServletContext context = request.getServletContext();
        DbBean dbBean = (DbBean) context.getAttribute("dbBean");

        if (!dbBean.isRegistered(userName)) {
            int i = dbBean.addUserDataInDB(userName, password, email);
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("registration.jsp");
            request.setAttribute("userAdded", i + "");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("registration.jsp");
            request.setAttribute("userNameUsed", "true");
            request.setAttribute("userName", userName);
            requestDispatcher.forward(request, response);
        }
    }
}
