package snake.games.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

import org.junit.jupiter.api.Test;
import snake.games.Game;
import snake.games.SnackBuilder;
import snake.gui.LauncherClass;
import snake.squares.ColouredSquare;
import snake.squares.Square;

public class NoWallsLevelTest extends LevelTest {
    private static final String MESSAGE = "Previous head should not be equal to the new square";

    public Level makeLevel(Game game, LauncherClass launcherClass, int stepsize) {
        return new NoWallsLevel(game, launcherClass, stepsize);
    }

    @Test
    public void testNoWallUp() {
        Square prevHead = snake.getHead();

        builder.createSnack((int) getWidth(), (int) getHeight(), snake.getBody());
        level.move(getWidth() - getStepsize(), getHeight(), snake);

        Square newSquare = new ColouredSquare(getWidth() - getStepsize(), 0,
                prevHead.getWidth(), prevHead.getHeight(), null);
        assertNotEquals(MESSAGE, prevHead, newSquare);
        assertEquals(newSquare, snake.getHead());
    }

    @Test
    public void testNoWallRight() {
        Square prevHead = snake.getHead();

        builder.createSnack((int) getWidth(), (int) getHeight(), snake.getBody());
        level.move(getWidth(), getHeight() - getStepsize(), snake);

        Square newSquare = new ColouredSquare(0, getHeight() - getStepsize(),
                prevHead.getWidth(), prevHead.getHeight(), null);
        assertNotEquals(MESSAGE, prevHead, newSquare);
        assertEquals(newSquare, snake.getHead());
    }

    @Test
    public void testNoWallDown() {
        Square prevHead = snake.getHead();

        builder.createSnack((int) getWidth(), (int) getHeight(), snake.getBody());
        getLevel().move(0, 0 - getStepsize(), snake);

        Square newSquare = new ColouredSquare(0, getHeight() - getStepsize(),
                prevHead.getWidth(), prevHead.getHeight(), null);
        assertNotEquals(MESSAGE, prevHead, newSquare);
        assertEquals(newSquare, snake.getHead());
    }

    @Test
    public void testNoWallLeft() {
        Square prevHead = snake.getHead();

        builder.createSnack((int) getWidth(), (int) getHeight(), snake.getBody());
        getLevel().move(0 - getStepsize(), 0, snake);

        Square newSquare = new ColouredSquare(getWidth() - getStepsize(), 0,
                prevHead.getWidth(), prevHead.getHeight(), null);
        assertNotEquals(MESSAGE, prevHead, newSquare);
        assertEquals(newSquare, snake.getHead());
    }
}
