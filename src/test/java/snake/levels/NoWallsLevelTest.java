package snake.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

import org.junit.jupiter.api.Test;
import snake.gui.Game;
import snake.squares.ColouredSquare;
import snake.squares.Square;

public class NoWallsLevelTest extends LevelTest {
    private static final String MESSAGE = "Previous head should not be equal to the new square";

    public Level makeLevel(Game game, int stepsize) {
        return new NoWallsLevel(game, stepsize);
    }

    @Test
    public void testNoWallUp() {
        Square prevHead = getLevel().getHead();

        getLevel().createSnack((int) getWidth(), (int) getHeight());
        getLevel().move(getWidth() - getStepsize(), getHeight());

        Square newSquare = new ColouredSquare(getWidth() - getStepsize(), 0,
                prevHead.getWidth(), prevHead.getHeight(), null);
        assertNotEquals(MESSAGE, prevHead, newSquare);
        assertEquals(newSquare, getLevel().getHead());
    }

    @Test
    public void testNoWallRight() {
        Square prevHead = getLevel().getHead();

        getLevel().createSnack((int) getWidth(), (int) getHeight());
        getLevel().move(getWidth(), getHeight() - getStepsize());

        Square newSquare = new ColouredSquare(0, getHeight() - getStepsize(),
                prevHead.getWidth(), prevHead.getHeight(), null);
        assertNotEquals(MESSAGE, prevHead, newSquare);
        assertEquals(newSquare, getLevel().getHead());
    }

    @Test
    public void testNoWallDown() {
        Square prevHead = getLevel().getHead();

        getLevel().createSnack((int) getWidth(), (int) getHeight());
        getLevel().move(0, 0 - getStepsize());

        Square newSquare = new ColouredSquare(0, getHeight() - getStepsize(),
                prevHead.getWidth(), prevHead.getHeight(), null);
        assertNotEquals(MESSAGE, prevHead, newSquare);
        assertEquals(newSquare, getLevel().getHead());
    }

    @Test
    public void testNoWallLeft() {
        Square prevHead = getLevel().getHead();

        getLevel().createSnack((int) getWidth(), (int) getHeight());
        getLevel().move(0 - getStepsize(), 0);

        Square newSquare = new ColouredSquare(getWidth() - getStepsize(), 0,
                prevHead.getWidth(), prevHead.getHeight(), null);
        assertNotEquals(MESSAGE, prevHead, newSquare);
        assertEquals(newSquare, getLevel().getHead());
    }
}
