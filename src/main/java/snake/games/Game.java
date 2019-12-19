package snake.games;

import java.util.List;
import snake.Snake;
import snake.games.levels.Level;
import snake.games.levels.NoWallsLevel;
import snake.games.levels.WalledLevel;
import snake.gui.LauncherClass;
import snake.squares.Square;

public abstract class Game {
    transient Level level;
    transient LauncherClass launcher;
    transient int stepSize;
    transient int squareSize;

    transient Snake player1;

    transient int score1;

    transient boolean gameIsOver;

    transient SnackBuilder builder;


    /**
     * Instantiates the game class.
     * @param launcher instance of the launcher
     * @param stepSize size of squares
     */
    public Game(LauncherClass launcher, int stepSize) {
        this.launcher = launcher;
        this.stepSize = stepSize;
        this.squareSize = launcher.getUser().isNoGrid() ? stepSize : stepSize - 2;

        this.player1 = new Snake(stepSize, squareSize, 0);

        this.level = launcher.getUser().isNoWalls()
                ? new NoWallsLevel(this, launcher, stepSize)
                : new WalledLevel(this, launcher, stepSize);
        this.score1 = 0;
        this.gameIsOver = false;
        this.builder = new SnackBuilder(launcher, stepSize, squareSize);
    }

    /**
     * Creates a new snack.
     */
    public abstract void createSnack();

    /**
     * Updates the score corresponding to the right snake.
     * @param snake player for which the score is updated
     */
    public abstract void updateScore(Snake snake);

    /**
     * Ends the game and sets the appropriate death screen.
     * @param losingSnake snake which lost
     */
    public abstract void gameOver(Snake losingSnake);

    public void moveUp(Snake snake) {
        level.moveUp(snake);
    }

    public void moveDown(Snake snake) {
        level.moveDown(snake);
    }

    public void moveRight(Snake snake) {
        level.moveRight(snake);
    }

    public void moveLeft(Snake snake) {
        level.moveLeft(snake);
    }

    public void setPaused() {
        level.setPaused();
    }

    public boolean isPaused() {
        return level.isPaused();
    }

    public Square getPlayer1Head() {
        return player1.getHead();
    }

    public List<Square> getPlayer1Body() {
        return player1.getBody();
    }

    public List<Square> getSquares() {
        return level.getSquares();
    }

    public int getScore1() {
        return score1;
    }

    public Square getSnack() {
        return builder.getSnack();
    }

    public Square getSnackSquare() {
        return builder.getSnackSquare();
    }

    public Snake getPlayer1() {
        return player1;
    }

    public boolean getGameIsOver() {
        return gameIsOver;
    }

    public LauncherClass getLauncher() {
        return this.launcher;
    }
}
