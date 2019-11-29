import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.sql.SQLException;

public class LoginScreen implements Screen {
    private final transient Game game;
    private transient Stage stage;
    private transient TextField username;
    private transient TextField password;
    private transient TextButton submit;
    private transient TextButton register;

    /**
     * Creates the LoginScreen.
     *
     * @param game current instance of the game
     */
    public LoginScreen(Game game) {
        this.game = game;
        createInput();
    }

    /**
     * Method which creates all the input fields/buttons.
     */
    private final void createInput() {
        Skin skin = StyleUtility.getSkin();
        this.username = new TextField("", skin);
        this.username.setWidth(300);
        this.username.setHeight(25f);
        this.username.setPosition(game.getWidth() / 3.33f, game.getHeight() / 1.455f);

        this.password = new TextField("", skin);
        this.password.setPasswordMode(true);
        this.password.setHeight(25f);
        password.setPasswordCharacter('*');
        this.password.setWidth(300);
        this.password.setPosition(game.getWidth() / 3.33f, game.getHeight() / 1.6f);

        this.submit = new TextButton("Log In", skin);
        this.submit.setPosition(game.getWidth() / 3.33f, game.getWidth() / 1.778f);
        this.submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (new Dao().checkUsernamePassword(username.getText(), password.getText())) {
                        game.setUsername(username.getText());
                        game.setScreen(new MainMenu(game));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Gdx.app.exit();
                }
            }
        });

        this.register = new TextButton("Register", skin);
        this.register.setPosition(game.getWidth() / 2.057f, game.getHeight() / 1.778f);
        this.register.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen(game));
            }

            ;
        });

        this.stage = new Stage();
        stage.addActor(username);
        stage.addActor(password);
        stage.addActor(submit);
        stage.addActor(register);

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Set the background colour
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = StyleUtility.getBatch();

        batch.begin();
        batch.draw(StyleUtility.getBackground(),
                0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Array<Actor> actors = stage.getActors();
        for (int i = 0; i < actors.size; i++) {
            actors.get(i).draw(batch, 1);
        }
        batch.end();
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
