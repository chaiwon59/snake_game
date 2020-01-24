package snake.games.levels;

import java.util.List;

import snake.Board;
import snake.MusicPlayer;
import snake.Snake;
import snake.games.Game;
import snake.gui.LauncherClass;
import snake.squares.Square;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public abstract class Level {
    private transient LauncherClass launcherClass;
    private transient Board board;

    private transient boolean paused;

    private transient int stepSize;
    private transient int squareSize;

    transient boolean died;

    private transient Snake currentSnake;

    private transient Game game;

    /**
     * Constructor of the level.
     *
     * @param launcherClass current instance of the game.
     * @param stepSize      distance between squares.
     */
    public Level(Game game, LauncherClass launcherClass, int stepSize) {
        this.launcherClass = launcherClass;
        this.squareSize = launcherClass.getUser().isNoGrid() ? stepSize : stepSize - 2;
        this.board = new Board(launcherClass, squareSize, stepSize);
        this.paused = false;
        this.stepSize = stepSize;
        this.died = false;
        this.game = game;
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
     *
     * @param snake snake to be moved
     */
    public void moveUp(Snake snake) {
        move(snake.getHead().getXvalue(), snake.getHead().getYvalue() + stepSize, snake);
    }

    /**
     * Moves the snake down by calling the move method.
     *
     * @param snake snake to be moved
     */
    public void moveDown(Snake snake) {
        move(snake.getHead().getXvalue(), snake.getHead().getYvalue() - stepSize, snake);
    }

    /**
     * Moves the snake right by calling the move method.
     *
     * @param snake snake to be moved
     */
    public void moveRight(Snake snake) {
        move(snake.getHead().getXvalue() + stepSize, snake.getHead().getYvalue(), snake);
    }

    /**
     * Moves the snake left by calling the move method.
     *
     * @param snake snake to be moved
     */
    public void moveLeft(Snake snake) {
        move(snake.getHead().getXvalue() - stepSize, snake.getHead().getYvalue(), snake);
    }

    /**
     * Moves the given snake to the new coordinates.
     *
     * @param newX  new x-value of the snake
     * @param newY  new y-value of the snake
     * @param snake snake to be moved
     */
    public void move(float newX, float newY, Snake snake) {
        if (paused) {
            return;
        }
        if (game.getSnack() == null) {
            game.createSnack();
        }

        this.currentSnake = snake;
        if (checkValidity(newX, newY)) {
            if (!snake.moveSnake(newX, newY)) {
                died = true;
            }

            //check whether a snack was eaten
            checkSnack(snake);

            //check whether a powerup was eaten
            eatPowerup(snake);
        }

        if (died) {
            game.gameOver(snake);
        }
    }

    /**
     * Checks whether the snake has eaten the snack.
     * @param snake snake to be checked for.
     */
    private void checkSnack(Snake snake) {
        if (game.getSnackSquare().equals(snake.getHead())) {
            snake.ateSnack();
            MusicPlayer.playSnackMusic();
            game.createSnack();
        }
    }

    /**
     * Checks whether the snake has eaten a powerup.
     * @param snake snake to be checked for.
     */
    private void eatPowerup(Snake snake) {
        if (game.getPowerUp() != null
                && !game.isActive() && game.getPowerUpSquare().equals(snake.getHead())) {
            game.getPowerUp().apply(snake.getPlayer());
            MusicPlayer.playPowerUpMusic(game.getPowerUp());
            game.resetNextPowerUpTime();
        }
    }

    public void setPaused() {
        this.paused = !this.paused;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public LauncherClass getLauncherClass() {
        return launcherClass;
    }

    public List<Square> getSquares() {
        return board.getSquares();
    }

    public int getStepSize() {
        return this.stepSize;
    }

    public void setDied() {
        this.died = true;
    }

    public Snake getCurrentSnake() {
        return this.currentSnake;
    }
}