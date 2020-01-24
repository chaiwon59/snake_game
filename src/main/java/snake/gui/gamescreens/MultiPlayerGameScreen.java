package snake.gui.gamescreens;

import com.badlogic.gdx.Input;

import java.util.ArrayList;

import snake.Direction;
import snake.Snake;
import snake.games.Game;
import snake.games.MultiPlayerGame;

public class MultiPlayerGameScreen extends SinglePlayerGameScreen {
    transient Snake player2;
    transient Direction lastPressed2;

    /**
     * Initializes the snake.Gui.GameScreen.
     *
     * @param game current instance of the game.
     */
    public MultiPlayerGameScreen(Game game) {
        super(game);

        this.player2 = ((MultiPlayerGame) game).getPlayer2();
    }

    @Override
    public void move() {
        for (int i = 0; i < player1.getPlayer().getNumberOfMoves(); i++) {
            move(player1, this.lastPressed);
            ((MultiPlayerGame) game).checkSnakeCollision();
        }
        if(game.getGameIsOver()) {
            return;
        }
        for (int i = 0; i < player2.getPlayer().getNumberOfMoves(); i++) {
            move(player2, lastPressed2);
            ((MultiPlayerGame) game).checkSnakeCollision();
        }
    }

    @Override
    public void checkLastPressed() {
        super.checkLastPressed();
        Direction newPressed = reader.checkQueue(player2, reader.getKeys2());
        lastPressed2 = newPressed != null ? newPressed : lastPressed2;
    }
}
