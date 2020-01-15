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

    /**
     * Creates a snack which is not inside the given list.
     */
    public Pair create() {
        return create(
                random.nextInt(((int) (launcherClass.getWidth() - stepSize) / stepSize)),
                random.nextInt(((int) (launcherClass.getWidth() - stepSize) / stepSize)),
                game.getForbiddenSquares());
    }

    /**
     * Creates a new snack at the given location.
     *
     * @param random1          random x - value
     * @param random2          random y - value
     * @param forbiddenSquares list of forbidden squares
     */
    public Pair create(int random1, int random2, List<Square> forbiddenSquares) {
        Pair result = new Pair(new Square(random1 * stepSize + stepSize / 5,
                random2 * stepSize + stepSize / 5,
                3 * stepSize / 5, 3 * stepSize / 5),
                new ColouredSquare(random1 * stepSize, random2 * stepSize,
                        squareSize, squareSize, new Color(0, 0, 0, 1)));
        return forbiddenSquares.contains(result.actual) ? create() : result;
    }
}
