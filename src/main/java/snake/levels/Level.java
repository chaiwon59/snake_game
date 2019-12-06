package snake.levels;

import com.badlogic.gdx.graphics.Color;
import java.util.List;
import java.util.Random;
import snake.Board;
import snake.Dao;
import snake.MusicPlayer;
import snake.Snake;
import snake.gui.DeathScreen;
import snake.gui.Game;
import snake.squares.ColouredSquare;
import snake.squares.Square;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public abstract class Level {
    private transient Game game;
    private transient Board board;
    private transient Snake snake;

    private transient boolean paused;

    private transient int score;
    private transient int stepSize;
    private transient int squareSize;

    private transient Random random;

    private transient Square snack;
    private transient Square snackSquare;

    private transient boolean died;

    private transient Dao dao;

    /**
     * Constructor of the level.
     * @param game current instance of the game.
     * @param stepSize distance between squares.
     */
    public Level(Game game, int stepSize) {
        this.game = game;
        this.squareSize = game.getUser().isNoGrid() ? stepSize : stepSize - 2;
        this.board = new Board(game, squareSize, stepSize);
        this.snake = new Snake(stepSize, squareSize);
        this.score = 0;
        this.paused = false;
        this.stepSize = stepSize;
        this.random = new Random();
        this.died = false;
        this.dao = new Dao();
    }

    /**
     * Checks whether the move is valid.
     *
     * @param newX new x value of the head of the snake.
     * @param newY new y value of the head of the snake.
     * @return true if the move is valid, false if it is not.
     */
    public abstract boolean checkValidity(float newX, float newY);

    /**
     * Moves the snake up by calling the move method.
     */
    public void moveUp() {
        move(snake.getHead().getXvalue(), snake.getHead().getYvalue() + stepSize);
    }

    /**
     * Moves the snake down by calling the move method.
     */
    public void moveDown() {
        move(snake.getHead().getXvalue(), snake.getHead().getYvalue() - stepSize);
    }

    /**
     * Moves the snake right by calling the move method.
     */
    public void moveRight() {
        move(snake.getHead().getXvalue() + stepSize, snake.getHead().getYvalue());
    }

    /**
     * Moves the snake left by calling the move method.
     */
    public void moveLeft() {
        move(snake.getHead().getXvalue() - stepSize, snake.getHead().getYvalue());
    }

    /**
     * Moves the snake, creating a snack when necessary and checking validity.
     * @param newX new x-coordinate of the head of the snake.
     * @param newY new y-coordinate of the head of the snake.
     */
    public void move(float newX, float newY) {
        if (paused) {
            return;
        }

        if (snack == null) {
            createSnack();
        }

        if (checkValidity(newX, newY)) {
            if (!snake.moveSnake(newX, newY)) {
                died = true;
            }

            if (snackSquare.equals(snake.getHead())) {
                snake.ateSnack();
                MusicPlayer.playSnackMusic();
                score += 10;
                createSnack();
            }
        }

        if (died) {
            MusicPlayer.stopInGame();
            MusicPlayer.playDeathMusic();
            dao.updateScore(game.getUser().getUsername(), score);
            game.setScreen(new DeathScreen(score, stepSize, game));
        }
    }

    /**
     * Creates a snack at a random location.
     */
    public void createSnack() {
        createSnack(random.nextInt(((int) (game.getWidth() - stepSize) / stepSize)),
                random.nextInt(((int) (game.getWidth() - stepSize) / stepSize)));
    }

    /**
     * Creates a random snack somewhere on the board (outside of the snake) with the given values.
     */
    public void createSnack(int random1, int random2) {
        //Set the value of the snack (add some offset to center it in the square)
        snack = new Square(random1 * stepSize + stepSize / 5,
                random2 * stepSize + stepSize / 5,
                3 * stepSize / 5, 3 * stepSize / 5);
        snackSquare = new ColouredSquare(random1 * stepSize, random2 * stepSize,
                squareSize, squareSize, new Color(0, 0, 0, 1));

        //Use the snackSquare for the equals method since the actual snack square uses some offset
        if (snake.getBody().contains(snackSquare)) {
            //If the snack is within the body of the snake, create a new square.
            createSnack();
        }
    }

    public void setPaused() {
        this.paused = !this.paused;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public int getScore() {
        return this.score;
    }

    public Game getGame() {
        return game;
    }

    public Square getSnack() {
        return this.snack;
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Square> getSquares() {
        return board.getSquares();
    }

    public List<Square> getSnakeBody() {
        return snake.getBody();
    }

    public Square getHead() {
        return snake.getHead();
    }

    public int getStepSize() {
        return this.stepSize;
    }

    public int getSnakeSize() {
        return snake.getSnakeSize();
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setDied() {
        this.died = true;
    }
}
