package snake;

import java.util.ArrayList;
import java.util.List;

import snake.gui.LauncherClass;
import snake.squares.BoardSquare;
import snake.squares.Square;

public class Board {
    private transient List<Square> squares;

    private transient LauncherClass launcherClass;

    private transient float size;
    private transient float stepSize;

    /**
     * Constructs the board.
     *
     * @param squareSize determines how big the squares
     *                 are (the width and height should be divisible by this number)
     * @param stepSize determines how many squares
     *                 there are (the width and height should be divisible by this number)
     * @param launcherClass     Current game
     */
    public Board(LauncherClass launcherClass, int squareSize, int stepSize) {
        this.launcherClass = launcherClass;

        this.size = squareSize;
        this.stepSize = stepSize;

        squares = createBoard();
    }

    /**
     * Creates all the squares present in the board and the snake.
     *
     * @return a list of all the board squares.
     */
    public final List<Square> createBoard() {
        List<Square> currSquares = new ArrayList<>();
        //Go through all of the columns
        for (int i = 0; i < launcherClass.getWidth(); i += stepSize) {
            //Go through all of the rows
            for (int j = 0; j < launcherClass.getHeight(); j += stepSize) {
                currSquares.add(new BoardSquare(i, j, size, size));
            }
        }
        return currSquares;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public float getWidth() {
        return launcherClass.getWidth();
    }

    public float getHeight() {
        return launcherClass.getHeight();
    }

    public LauncherClass getLauncherClass() {
        return this.launcherClass;
    }
}
