package app.servlets;

import app.entities.DbBean;
import app.entities.NumberGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The servlet provides methods for managing the game. Checks the data entered by
 * the player and sends the results to the DbBean.
 *
 * @author Partynin - <a href="mailto:partinin@bk.ru">partinin@bk.ru</a>
 */
public class GameServlet extends HttpServlet {

    private HttpSession session;

    /**
     * Determine if it is new game started and start new game or send
     * the guess number to check.
     *
     * @param request  the request information for the servlet
     * @param response the response information for the servlet
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();

        if (session.getAttribute("gameStart") == null ||
                session.getAttribute("gameStart").equals("false") ||
                request.getParameter("action") != null) {
            start(request, response);
        } else {
            String guessNumber = getGuessNumber(request);
            checkGuess(guessNumber, request, response);
        }
    }

    /**
     * Returns a String representing the guess number obtained from the request.
     *
     * @param request the request information for the servlet
     * @return the string what was entered user like a guess
     */
    private String getGuessNumber(HttpServletRequest request) {
        StringBuilder guessNumber = new StringBuilder();
        String digit;

        for (int i = 1; i <= 4; i++) {
            digit = request.getParameter("number" + i);
            if (digit.matches("[0-9]")) {
                guessNumber.append(digit);
            } else {
                guessNumber.append("0");
            }
        }

        return guessNumber.toString();
    }

    /**
     * Compares the generate number with the guess number what user enters.
     *
     * @param guessNumber the number what was entered user like a guess
     * @param request     the request information for the servlet
     * @param response    the response information for the servlet
     */
    private void checkGuess(String guessNumber, HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        String number = (String) session.getAttribute("number");
        Map<String, String> map = (Map<String, String>) session.getAttribute("guessNumbersMap");

        if (number.matches(guessNumber)) {
            map.put(guessNumber, obtainBullAndCow(guessNumber));
            request.setAttribute("gameEnd", "true");
            session.setAttribute("gameStart", "false");

            addResultToBD(map.size(), request);

            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("/jsp/game.jsp");
            requestDispatcher.forward(request, response);
        } else {
            map.put(guessNumber, obtainBullAndCow(guessNumber));
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("/jsp/game.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    /**
     * Starts new game.
     *
     * @param request  the request information for the servlet
     * @param response the response information for the servlet
     */
    private void start(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NumberGenerator numberGenerator = new NumberGenerator();
        String number = numberGenerator.generateNumber();

        session.setAttribute("number", number + "");
        session.setAttribute("gameStart", "true");
        session.setAttribute("guessNumbersMap", new LinkedHashMap<String, String>());

        RequestDispatcher requestDispatcher =
                request.getRequestDispatcher("/jsp/game.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * Returns a String representing the Bull and Cow in the guess number.
     *
     * @param guessNumber the number what was entered user like a guess
     * @return the string represents the number of bulls and cows that the user has guessed
     */
    private String obtainBullAndCow(String guessNumber) {
        String number = (String) session.getAttribute("number");

        int countBull = 0;
        int countCow = 0;
        for (int i = 0; i < 4; i++) {
            if (number.charAt(i) == guessNumber.charAt(i)) {
                countBull++;
            } else {
                for (int j = 0; j < 4; j++) {
                    if (number.charAt(j) == guessNumber.charAt(i)) {
                        countCow++;
                    }
                }
            }
        }

        return countBull + "B" + countCow + "C";
    }

    /**
     * Adds the results of the game to the database.
     *
     * @param request       the request information for the servlet
     * @param numberOfTries the number of attempts by the user to guess the number
     */
    private void addResultToBD(int numberOfTries, HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        DbBean dbBean = (DbBean) context.getAttribute("dbBean");

        String userName = (String) session.getAttribute("userName");

        dbBean.addResultOfGameToDB(numberOfTries, userName);
    }
}
