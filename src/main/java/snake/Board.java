package snake;

import java.util.ArrayList;
import java.util.List;

import snake.gui.Game;
import snake.squares.BoardSquare;
import snake.squares.Square;

public class Board {
    private transient List<Square> squares;

    private transient Game game;

    private transient float size;
    private transient float stepSize;

    /**
     * Constructs the board.
     *
     * @param squareSize determines how big the squares
     *                 are (the width and height should be divisible by this number)
     * @param stepSize determines how many squares
     *                 there are (the width and height should be divisible by this number)
     * @param game     Current game
     */
    public Board(Game game, int squareSize, int stepSize) {
        this.game = game;

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
        for (int i = 0; i < game.getWidth(); i += stepSize) {
            //Go through all of the rows
            for (int j = 0; j < game.getHeight(); j += stepSize) {
                currSquares.add(new BoardSquare(i, j, size, size));
            }
        }
        return currSquares;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public float getWidth() {
        return game.getWidth();
    }

    public float getHeight() {
        return game.getHeight();
    }

    public Game getGame() {
        return this.game;
    }
}
