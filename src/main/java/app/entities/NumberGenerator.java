package app.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Creates a random number with no duplicates of digits
 * from 0 to 9 for the game.
 *
 * @author Partynin - <a href="mailto:partinin@bk.ru">partinin@bk.ru</a>
 */
public class NumberGenerator {

    private List<Integer> numbers;

    /**
     * Constructs a new NumberGenerator with the list fo digit from 0 - 9
     */
    public NumberGenerator() {
        numbers = new LinkedList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    /**
     * Return the String representation of random number without duplicate digits.
     *
     * @return the String of four numbers
     */
    public String generateNumber() {
        Collections.shuffle(numbers);

        StringBuilder number = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            number.append(numbers.get(i));
        }

        return number.toString();
    }
}
