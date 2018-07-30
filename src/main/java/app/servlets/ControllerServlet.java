package app.servlets;

import app.entities.DbBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The servlet controller receives requests from the Home JSP and redirects them
 * to the required pages. Initialises start parameters for the dbBean and loads JDBC driver.
 *
 * @author Partynin - <a href="mailto:partinin@bk.ru">partinin@bk.ru</a>
 */

public class ControllerServlet extends HttpServlet {

    /**
     * Creating an instance of DbBean and initializing its fields. Add dbBean to
     * ServletContext and making the bean available from JSP.
     *
     * @param config initializing parameters obtained from the descriptor file
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        DbBean dbBean = new DbBean();
        dbBean.setDbUrl(config.getInitParameter("dbUrl"));
        dbBean.setDbUserName(config.getInitParameter("dbUserName"));
        dbBean.setDbPassword(config.getInitParameter("dbPassword"));

        ServletContext context = config.getServletContext();
        context.setAttribute("dbBean", dbBean);

        try {
            // Load the JDBC driver
            Class.forName(config.getInitParameter("jdbcDriver"));
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex + "init method in ControllerServlet");
        }

        super.init(config);
    }

    /**
     * Forwards a request to the doGet method.
     *
     * @param req  the request information for the servlet
     * @param resp the response information for the servlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * Gets the parameter action for request and send to specified JSP.
     *
     * @param req  the request information for the servlet
     * @param resp the response information for the servlet
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String base = "/jsp/";
        String url = "index.jsp";
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "game":
                    url = base + "startgame.jsp";
                    break;
                case "registration":
                    url = "registration.jsp";
                    break;
                case "login":
                    url = "login.jsp";
                    break;
                case "results":
                    url = "results.jsp";
                    break;
                case "logout":
                    logout(req);
                    break;
            }
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(url);
        requestDispatcher.forward(req, resp);
    }

    /**
     * Terminates the current session.
     *
     * @param req the request information for the servlet
     */
    private void logout(HttpServletRequest req) {
        req.getSession().invalidate();
    }
}
