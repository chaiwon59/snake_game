package snake.gui.deathscreens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import java.util.List;
import snake.games.MultiPlayerGame;
import snake.gui.GameScreen;
import snake.gui.LauncherClass;

public class MultiPlayerDeathScreen extends DeathScreen {
    transient String winningPlayer;

    /**
     * Initializes the Deathscreen.
     *
     * @param score         final score of the user
     * @param stepSize      stepSize to give to the new snake.Gui.GameScreen
     * @param launcherClass current instance of the game
     * @param winningPlayer String containing the winner of the game
     */
    public MultiPlayerDeathScreen(int score, int stepSize,
                                  LauncherClass launcherClass, String winningPlayer) {
        super(score, stepSize, launcherClass);
        this.winningPlayer = winningPlayer;
    }

    @Override
    public List<Actor> createActors() {
        List<Actor> result = new ArrayList<>();

        result.add(createAgainButton(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getLauncherClass().setScreen(
                        new GameScreen(new MultiPlayerGame(getLauncherClass(), stepSize)));
            }
        }));
        result.add(createMainMenuButton());
        result.add(createLabel(winningPlayer, null, getLauncherClass().getHeight() / 1.5f));

        return result;
    }
}
