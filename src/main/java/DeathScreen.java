import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.sql.SQLException;
import java.util.List;

public class DeathScreen implements Screen {
    private transient int score;
    private transient int stepSize;
    private transient Game game;
    private transient TextButton again;
    private transient TextButton mainMenu;
    private transient Stage stage;
    private transient Dao dao;

    //pmd du anomaly
    private transient float height;

    /**
     * Initializes the Deathscreen.
     *
     * @param score    final score of the user
     * @param stepSize stepSize to give to the new GameScreen
     * @param game     current instance of the game
     */
    public DeathScreen(int score, int stepSize, Game game) {
        this.score = score;
        this.game = game;
        this.dao = new Dao();
        this.stepSize = stepSize;

        updateScore();
    }

    private void updateScore() {
        try {
            if (dao.getHighscore(game.getUsername()) < score) {
                dao.setHighscore(game.getUsername(), score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    private void createStage() {
        Skin skin = StyleUtility.getSkin();
        this.again = new TextButton("Play Again", skin);
        this.again.setPosition(game.getWidth() / 2.56f, game.getHeight() / 1.778f);
        this.again.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(stepSize, game));
            }

            ;
        });

        this.mainMenu = new TextButton("Main Menu", skin);
        this.mainMenu.setPosition(game.getWidth() / 2.48f, game.getHeight() / 2.0f);
        this.mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }

            ;
        });

        this.stage = new Stage();
        stage.addActor(again);
        stage.addActor(mainMenu);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {
        createStage();
    }

    @Override
    public void render(float delta) {
        //Set the background colour
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = StyleUtility.getBatch();
        BitmapFont font = StyleUtility.getFont();
        //Draw the text at the appropriate position
        batch.begin();

        batch.draw(StyleUtility.getBackground(),
                0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.getRegion().getTexture()
                .setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(1.5f);
        font.draw(batch, "Your score was: " + score,
                getXString("Your score was: " + score), game.getWidth() / 1.333f);
        try {
            String highscore = "Your highscore is: " + dao.getHighscore(game.getUsername());
            font.draw(batch, highscore,
                    getXString(highscore), game.getWidth() / 1.45f);

            String leaderboard = "Global Leaderboard:";
            font.draw(batch, leaderboard,
                    getXString(leaderboard), game.getHeight() / 2.75f);

            List<String> top5 = dao.getTop5();
            height = game.getHeight() / 3.2f;
            for (int i = 0; i < top5.size(); i++) {
                String current = i + 1 + ". " + top5.get(i);
                GlyphLayout layout = new GlyphLayout(font, current);
                font.draw(batch, current,
                        (game.getWidth() - layout.width) / 2, height);
                height -= layout.height + 4;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }

        Array<Actor> actors = stage.getActors();
        for (int i = 0; i < actors.size; i++) {
            actors.get(i).draw(batch, 1);
        }

        batch.end();
    }

    private float getXString(String string) {
        GlyphLayout layout = new GlyphLayout(StyleUtility.getFont(), string);
        return (game.getWidth() - layout.width) / 2;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
