package app.servlets;

import app.entities.DbBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Verifies the correctness of the user input for registration and redirect to
 * the start game JSP.
 *
 * @author Partynin - <a href="mailto:partinin@bk.ru">partinin@bk.ru</a>
 */
public class LoginServlet extends HttpServlet {

    /**
     * Obtains the DbBean object from the context and the user name and password
     * from the query parameter.Invokes the checkLogin method.
     *
     * @param request  the request information for the servlet
     * @param response the response information for the servlet
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        DbBean dbBean = (DbBean) context.getAttribute("dbBean");

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        checkLogin(userName, password, request, response, dbBean);
    }

    /**
     * Checks user name and password if it is correct set the loggedIn attribute of session
     * <code>true</code> and send redirect to the start game JSP.
     *
     * @param userName the name of user
     * @param password password of user
     * @param request  the request information for the servlet
     * @param response the response information for the servlet
     * @param dbBean the database bean for connecting and execute query to the DB
     */
    private void checkLogin(String userName, String password,
                            HttpServletRequest request, HttpServletResponse response, DbBean dbBean)
            throws ServletException, IOException {
        if (dbBean.login(userName, password)) {
            request.getSession().invalidate();
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedIn", "true");
            session.setAttribute("userName", userName);

            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("/jsp/startgame.jsp");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("login.jsp");
            request.setAttribute("notLoggedIn", "true");
            requestDispatcher.forward(request, response);
        }
    }
}
