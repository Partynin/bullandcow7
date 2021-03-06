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
import java.util.*;

/**
 * The servlet provides methods for managing the game. Checks the data entered by
 * the player and sends the results to the DbBean.
 *
 * @author Partynin - <a href="mailto:partinin@bk.ru">partinin@bk.ru</a>
 */
public class GameServlet extends HttpServlet {

    /**
     * Determines the state of the game: the game is running the first time,
     * the game is restarted, the game is over. Starts a new game.
     * Otherwise, sends the number to the {@link #checkGuess(String,
     * HttpServletRequest, HttpServletResponse)} method.
     *
     * @param request  the request information for the servlet
     * @param response the response information for the servlet
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("gameStart") == null
                || session.getAttribute("gameStart").equals("false")
                || request.getParameter("action") != null) {
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
                if (request.getAttribute("wrongEnter") == null)
                    request.setAttribute("wrongEnter", "err");
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
        HttpSession session = request.getSession();
        String number = (String) session.getAttribute("number");
        Map<String, String> map = (Map<String, String>) session.getAttribute("guessNumbersMap");

        if (number.matches(guessNumber)) {
            map.put(guessNumber, obtainBullAndCow(guessNumber, request));
            request.setAttribute("gameEnd", "true");
            session.setAttribute("gameStart", "false");

            addResultToBD(map.size(), request);

            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("/jsp/game.jsp");
            requestDispatcher.forward(request, response);
        } else {
            map.put(guessNumber, obtainBullAndCow(guessNumber, request));
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("/jsp/game.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    /**
     * Starts a new game.
     *
     * @param request  the request information for the servlet
     * @param response the response information for the servlet
     */
    private void start(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String number = generateNumber();

        session.setAttribute("number", number);
        session.setAttribute("gameStart", "true");
        session.setAttribute("guessNumbersMap", new LinkedHashMap<String, String>());

        RequestDispatcher requestDispatcher =
                request.getRequestDispatcher("/jsp/game.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * Return the String representation of random number without duplicate digits.
     *
     * @return the string of four numbers
     */
    public String generateNumber() {
        List<Integer> numbers = new LinkedList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(numbers);

        StringBuilder number = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            number.append(numbers.get(i));
        }

        return number.toString();
    }

    /**
     * Returns a String representing the Bull and Cow in the guess number.
     *
     * @param guessNumber the number what was entered user like a guess
     * @param request     the request information for the servlet
     * @return the string represents the number of bulls and cows that the user has guessed
     */
    private String obtainBullAndCow(String guessNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();
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
     * Adds the results of the game to the database using the {@link DbBean}.
     *
     * @param request       the request information for the servlet
     * @param numberOfTries the number of attempts by the user to guess the number
     */
    private void addResultToBD(int numberOfTries, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        DbBean dbBean = (DbBean) context.getAttribute("dbBean");

        String userName = (String) session.getAttribute("userName");

        dbBean.addResultOfGameToDB(numberOfTries, userName);
    }
}
