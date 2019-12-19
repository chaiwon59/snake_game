package snake.games;

import com.badlogic.gdx.graphics.Color;
import java.util.List;
import java.util.Random;
import snake.gui.LauncherClass;
import snake.squares.ColouredSquare;
import snake.squares.Square;

public class SnackBuilder {
    transient Square snack;
    transient Square snackSquare;
    transient Random random;
    transient LauncherClass launcherClass;
    transient int stepSize;
    transient int squareSize;

    /**
     * Creates instance of the SnackBuilder class.
     * @param launcherClass instance of the launcher class
     * @param stepSize size of the steps used when creating squares
     * @param squareSize size of the squares
     */
    public SnackBuilder(LauncherClass launcherClass, int stepSize, int squareSize) {
        this.random = new Random();
        this.launcherClass = launcherClass;
        this.stepSize = stepSize;
        this.squareSize = squareSize;
    }

    /**
     * Creates a snack which is not inside the given list.
     * @param forbiddenSquares list of forbidden squares
     */
    public void createSnack(List<Square> forbiddenSquares) {
        createSnack(
                random.nextInt(((int) (launcherClass.getWidth() - stepSize) / stepSize)),
                random.nextInt(((int) (launcherClass.getWidth() - stepSize) / stepSize)),
                forbiddenSquares);
    }

    /**
     * Creates a new snack at the given location.
     * @param random1 random x - value
     * @param random2 random y - value
     * @param forbiddenSquares list of forbidden squares
     */
    public void createSnack(int random1, int random2, List<Square> forbiddenSquares) {
        //Set the value of the snack (add some offset to center it in the square)
        snack = new Square(random1 * stepSize + stepSize / 5,
                random2 * stepSize + stepSize / 5,
                3 * stepSize / 5, 3 * stepSize / 5);
        snackSquare = new ColouredSquare(random1 * stepSize, random2 * stepSize,
                squareSize, squareSize, new Color(0, 0, 0, 1));

        //Use the snackSquare for the equals method since the actual snack square uses some offset
        if (forbiddenSquares.contains(snackSquare)) {
            //If the snack is within the body of the snake, create a new square.
            createSnack(forbiddenSquares);
        }
    }

    public Square getSnack() {
        return snack;
    }

    public Square getSnackSquare() {
        return snackSquare;
    }
}
