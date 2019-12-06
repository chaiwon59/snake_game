package snake.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import snake.Dao;
import snake.MusicPlayer;

public class DeathScreen extends InputScreen {
    private transient int score;
    private transient int stepSize;
    private transient Dao dao;

    //pmd du anomaly
    private transient float height;

    /**
     * Initializes the Deathscreen.
     *
     * @param score    final score of the user
     * @param stepSize stepSize to give to the new snake.Gui.GameScreen
     * @param game     current instance of the game
     */
    public DeathScreen(int score, int stepSize, Game game) {
        super(game);
        this.score = score;
        this.dao = new Dao();
        this.stepSize = stepSize;
    }

    @Override
    public List<Actor> createActors() {
        List<Actor> result = new ArrayList<>();
        try {
            result.add(createAgainButton());
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
     * Create button to start another game.
     * @return textbutton with a listener to change the screen on click.
     */
    private TextButton createAgainButton() {
        return createButton("Play Again", getGame().getWidth() / 2.56f,
                getGame().getHeight() / 1.778f, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getGame().setScreen(new GameScreen(stepSize, getGame()));
                    }
                });
    }

    /**
     * Creates button to go back to the main menu.
     * @return button with functionality
     */
    private TextButton createMainMenuButton() {
        return createButton("Main Menu", getGame().getWidth() / 2.48f, getGame().getHeight() / 2.0f,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        getGame().setScreen(new MainMenu(getGame()));
                    }
                });
    }

    /**
     * Creates label with your score of the game you just finished.
     * @return
     */
    private Label createPrevScore() {
        return createLabel("Your score was: " + score, getGame().getHeight() / 1.333f);
    }

    /**
     * Creates label with your current highscore (after it was updated).
     * @return Label with the given text
     * @throws SQLException when anything goes wrong in the Dao class
     */
    private Label createHighScore() throws SQLException {
        return createLabel("Your highscore is: "
                        + dao.getHighscore(getGame().getUser().getUsername()),
                getGame().getHeight() / 1.45f);
    }

    /**
     * Creates label with the "Global Leaderboard:" text.
     * @return label with the given text.
     */
    private Label createGlobalHighScore() {
        return createLabel("Global Leaderboard:", getGame().getHeight() / 2.75f);
    }

    /**
     * Creates a list of labels with the current top 5 users in the database.
     * @return a list of labels with the current top 5 users
     * @throws SQLException when anything goes wrong in the Dao class
     */
    private List<Label> createTop5() throws SQLException {
        List<Label> result = new ArrayList<>();

        List<String> top5 = dao.getTop5();
        height = getGame().getHeight() / 3.2f;
        for (int i = 0; i < top5.size(); i++) {
            Label newLabel = createLabel(i + 1 + ". " + top5.get(i), height);
            result.add(newLabel);
            height -= newLabel.getHeight() + 6;
        }
        return result;
    }
}
