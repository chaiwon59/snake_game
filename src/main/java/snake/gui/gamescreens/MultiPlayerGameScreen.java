package snake.gui.gamescreens;

import com.badlogic.gdx.Input;

import java.util.ArrayList;

import snake.Direction;
import snake.Snake;
import snake.games.Game;
import snake.games.MultiPlayerGame;

public class MultiPlayerGameScreen extends SinglePlayerGameScreen {
    transient Snake player2;
    transient ArrayList<Integer> keys2;
    transient Direction lastPressed2;

    /**
     * Initializes the snake.Gui.GameScreen.
     *
     * @param game current instance of the game.
     */
    public MultiPlayerGameScreen(Game game) {
        super(game);

        this.keys2 = createKeys2();
        this.player2 = ((MultiPlayerGame) game).getPlayer2();
    }

    @Override
    public void move() {
        for (int i = 0; i < player1.getNumberOfMoves(); i++) {
            move(player1, this.lastPressed);
            ((MultiPlayerGame) game).checkSnakeCollision();
        }
        for (int i = 0; i < player2.getNumberOfMoves(); i++) {
            move(player2, lastPressed2);
            ((MultiPlayerGame) game).checkSnakeCollision();
        }
    }

    @Override
    public void checkLastPressed() {
        super.checkLastPressed();
        Direction newPressed = checkQueue(player2, keys2);
        lastPressed2 = newPressed != null ? newPressed : lastPressed2;
    }

    private ArrayList<Integer> createKeys2() {
        ArrayList<Integer> result = new ArrayList<>();

        result.add(Input.Keys.UP);
        result.add(Input.Keys.DOWN);
        result.add(Input.Keys.RIGHT);
        result.add(Input.Keys.LEFT);

        return result;
    }
}
