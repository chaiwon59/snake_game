package snake.levels;

import snake.MusicPlayer;
import snake.gui.DeathScreen;
import snake.gui.Game;

public class WalledLevel extends Level {

    /**
     * Constructs the board.
     * @param stepSize determines how many squares
     *                 there are (the width and height should be divisible by this number)
     * @param game Current game
     */
    public WalledLevel(Game game, int stepSize) {
        super(game,stepSize);
    }

    /**
     * Checks whether the move is valid.
     *
     * @param newX new x value of the head of the snake.
     * @param newY new y value of the head of the snake.
     * @return true if the move is valid, false if it is not.
     */
    @Override
    public boolean checkValidity(float newX, float newY) {
        if (newY > getGame().getHeight() - getStepSize()
                || newX > getGame().getWidth() - getStepSize()
                || newX < 0
                || newY < 0) {
            setDied();
            return false;
        }
        return true;
    }
}