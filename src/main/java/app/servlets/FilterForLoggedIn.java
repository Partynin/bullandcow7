package app.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The class filter protect the pages from accessing by unregistered users.
 *
 * @author Partynin - <a href="mailto:partinin@bk.ru">partinin@bk.ru</a>
 */

public class FilterForLoggedIn implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // don't need realisation
    }

    /**
     * Redirects users to the login page if they don't logged.
     *
     * @param servletRequest  the request information for the filter
     * @param servletResponse the response information for the filter
     * @param filterChain     teh chain information for the filter
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("loggedIn") == null) {
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(request, response);
        } else {
            String loggedIn = (String) session.getAttribute("loggedIn");
            if (!loggedIn.equals("true")) {
                RequestDispatcher requestDispatcher =
                        request.getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(request, response);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //don't need realisation
    }
}
