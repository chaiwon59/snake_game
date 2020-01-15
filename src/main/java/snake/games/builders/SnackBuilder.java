package snake.games.builders;

import java.util.List;

import snake.games.Game;
import snake.gui.LauncherClass;
import snake.squares.Square;

public class SnackBuilder extends Builder {
    transient Square snack;
    transient Square snackSquare;

    /**
     * Creates instance of the SnackBuilder class.
     *
     * @param launcherClass instance of the launcher class
     * @param stepSize      size of the steps used when creating squares
     * @param squareSize    size of the squares
     */
    public SnackBuilder(Game game, LauncherClass launcherClass, int stepSize, int squareSize) {
        super(game, launcherClass, stepSize, squareSize);
    }

    /**
     * Creates a snack at a random location.
     */
    public void createSnack() {
        Pair newSnack = create();

        this.snack = newSnack.rendered;
        this.snackSquare = newSnack.actual;
    }

    /**
     * Create a snack at the given location outside of the given squares.
     * @param random1 random x-value
     * @param random2 random y-value
     * @param forbiddenSquares forbidden squares
     */
    public void createSnack(int random1, int random2, List<Square> forbiddenSquares) {
        Pair newSnack = create(random1, random2, forbiddenSquares);

        this.snack = newSnack.rendered;
        this.snackSquare = newSnack.actual;
    }

    public Square getSnack() {
        return snack;
    }

    public Square getSnackSquare() {
        return snackSquare;
    }
}
