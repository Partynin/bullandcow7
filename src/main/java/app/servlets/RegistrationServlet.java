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

    private String userName = "";
    private String password = "";
    private String email = "";
    private DbBean dbBean;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Obtains the user name, password, email from request parameter and dbBean from context
     * and invokes the method register user.
     *
     * @param request  the request information for the servlet
     * @param response the response information for the servlet
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        userName = request.getParameter("userName");
        password = request.getParameter("password");
        email = request.getParameter("email");

        ServletContext context = request.getServletContext();
        dbBean = (DbBean) context.getAttribute("dbBean");

        this.request = request;
        this.response = response;

        registerUser();
    }

    /**
     * Checks if username is available and add the new user in the DB.
     */
    private void registerUser()
            throws ServletException, IOException {
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
