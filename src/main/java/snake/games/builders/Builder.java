package snake.games.builders;

import com.badlogic.gdx.graphics.Color;
import java.util.List;
import java.util.Random;
import snake.games.Game;
import snake.gui.LauncherClass;
import snake.squares.ColouredSquare;
import snake.squares.Square;

public abstract class Builder {
    protected class Pair {
        transient Square rendered;
        transient Square actual;

        public Pair(Square rendered, Square actual) {
            this.rendered = rendered;
            this.actual = actual;
        }
    }

    transient Game game;
    transient Random random;
    transient LauncherClass launcherClass;
    transient int stepSize;
    transient int squareSize;

    /**
     * Creates instance of the Builder class.
     *
     * @param launcherClass instance of the launcher class
     * @param stepSize      size of the steps used when creating squares
     * @param squareSize    size of the squares
     */
    public Builder(Game game, LauncherClass launcherClass, int stepSize, int squareSize) {
        this.game = game;
        this.random = new Random();
        this.launcherClass = launcherClass;
        this.stepSize = stepSize;
        this.squareSize = squareSize;
    }

    //New method for refactored create() method
    public int calculate() {
        return (int) (launcherClass.getWidth() - stepSize) / stepSize;
    }

    /**
     * Creates a snack which is not inside the given list.
     */
    public Pair create() {
        return create(random.nextInt(calculate()),
                random.nextInt(calculate()), game.getForbiddenSquares());
    }

    /**
     * Creates a new snack at the given location.
     *
     * @param random1          random x - value
     * @param random2          random y - value
     * @param forbiddenSquares list of forbidden squares
     */
    public Pair create(int random1, int random2, List<Square> forbiddenSquares) {
        Pair result = new Pair(newSquare(random1, random2),
                newColouredSquare(random1, random2));
        return forbiddenSquares.contains(result.actual) ? create() : result;
    }

    //New method for refactored create(int, int, List)
    public Square newSquare(int a, int b) {
        return new Square(a * stepSize + stepSize / 5,
                b * stepSize + stepSize / 5, 3 * stepSize / 5, 3 * stepSize / 5);
    }

    //New method for refactored create(int, int, List)
    public ColouredSquare newColouredSquare(int a, int b) {
        return new ColouredSquare(a * stepSize, b * stepSize,
                squareSize, squareSize, new Color(0, 0, 0, 1));
    }
}
