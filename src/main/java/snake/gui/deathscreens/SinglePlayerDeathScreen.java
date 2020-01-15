package snake.gui.deathscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import snake.games.SinglePlayerGame;
import snake.gui.LauncherClass;
import snake.gui.gamescreens.SinglePlayerGameScreen;

public class SinglePlayerDeathScreen extends DeathScreen {

    /**
     * Initializes the Deathscreen.
     *
     * @param score         final score of the user
     * @param stepSize      stepSize to give to the new snake.Gui.GameScreen
     * @param launcherClass current instance of the game
     */
    public SinglePlayerDeathScreen(int score, int stepSize, LauncherClass launcherClass) {
        super(score, stepSize, launcherClass);
    }


    @Override
    public List<Actor> createActors() {
        List<Actor> result = new ArrayList<>();
        try {
            result.add(createAgainButton(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    getLauncherClass().setScreen(
                            new SinglePlayerGameScreen(
                                    new SinglePlayerGame(getLauncherClass(), stepSize)));
                }
            }));

            result.add(createMainMenuButton());
            result.add(createPrevScore());
            result.add(createHighScore());
            result.add(createGlobalHighScore());
            result.addAll(createTop5());
        } catch (SQLException e) {
            Gdx.app.exit();
        }
        return result;
    }


    /**
     * Creates label with your score of the game you just finished.
     *
     * @return
     */
    private Label createPrevScore() {
        return createLabel("Your score was: " + score,
                null, getLauncherClass().getHeight() / 1.333f);
    }

    /**
     * Creates label with your current highscore (after it was updated).
     *
     * @return Label with the given text
     * @throws SQLException when anything goes wrong in the Dao class
     */
    private Label createHighScore() throws SQLException {
        return createLabel("Your highscore is: "
                        + dao.getHighscore(getLauncherClass().getUser().getUsername()),
                null, getLauncherClass().getHeight() / 1.45f);
    }

    /**
     * Creates label with the "Global Leaderboard:" text.
     *
     * @return label with the given text.
     */
    private Label createGlobalHighScore() {
        return createLabel("Global Leaderboard:", null, getLauncherClass().getHeight() / 2.75f);
    }

    /**
     * Creates a list of labels with the current top 5 users in the database.
     *
     * @return a list of labels with the current top 5 users
     * @throws SQLException when anything goes wrong in the Dao class
     */
    private List<Label> createTop5() throws SQLException {
        List<Label> result = new ArrayList<>();

        List<String> top5 = dao.getTop5();
        height = getLauncherClass().getHeight() / 3.2f;
        for (int i = 0; i < top5.size(); i++) {
            Label newLabel = createLabel(i + 1 + ". " + top5.get(i), null, height);
            result.add(newLabel);
            height -= newLabel.getHeight() + 6;
        }
        return result;
    }
}
