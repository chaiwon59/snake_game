package snake.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.List;

//suppress warning for enhanced for-loop
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public abstract class InputScreen implements Screen {
    private transient Game game;
    private transient Stage stage;

    /**
     * Creates the snake.Gui.LoginScreen.
     *
     * @param game current instance of the game
     */
    public InputScreen(Game game) {
        this.game = game;
    }

    /**
     * Creates a stage and sets the stage as the inputProcessor.
     * @param actors list of actors with which the stage is created.
     */
    public final void createInput(List<Actor> actors) {
        this.stage = new Stage();
        for (Actor actor : actors) {
            stage.addActor(actor);
        }
        Gdx.input.setInputProcessor(this.stage);
    }

    /**
     * Method for creating the actors which are set on the stage.
     * @return list of actors
     */
    public abstract List<Actor> createActors();

    /**
     * Creates a new label with the given text and y-coordinate.
     *
     * @param text text of the label
     * @param y    y-coordinate of the label
     * @return new label with these attributes
     */
    public Label createLabel(String text, float y) {
        Label label = new Label(text,
                StyleUtility.getSkin().get("optional", Label.LabelStyle.class));
        label.setAlignment(Align.center);
        label.setFontScale(1.5f);
        label.setPosition((getGame().getWidth() - label.getWidth()) / 2f, y);
        label.setBounds((getGame().getWidth() - label.getWidth() * 1.5f) / 2f, y,
                label.getWidth() * 1.5f, label.getHeight() * 1.5f);
        return label;
    }

    /**
     * Creates label with an on-click listener.
     *
     * @param text     text of the label
     * @param y        y-coordinate of the label
     * @param listener listener of the label
     * @return new label with these attributes + listener
     */
    public Label createLabelWithOnClick(String text, float y, ClickListener listener) {
        Label label = createLabel(text, y);
        label.addListener(listener);
        return label;
    }

    /**
     * Creates button with the given input.
     * @param text text of the button
     * @param x x-coordinate of the button,
     *          can be null to indicate that the button needs to be in the center
     * @param y y-coordinate of the button
     * @param listener listener of the button
     * @return button with the given attributes
     */
    public TextButton createButton(String text, Float x, float y, ClickListener listener) {
        TextButton button = new TextButton(text, getSkin());

        //default x, places it in the middle
        if (x == null) {
            x = (getGame().getWidth() - button.getWidth()) / 2f;
        }

        button.setPosition(x, y);
        button.addListener(listener);
        return button;
    }

    /**
     * Creates the username input field.
     *
     * @return textfield of the username
     */
    public TextField createUsernameField() {
        TextField username = new TextField("", getSkin());
        username.setWidth(300);
        username.setHeight(25f);
        username.setPosition(game.getWidth() / 3.33f, game.getHeight() / 1.455f);
        return username;
    }

    /**
     * Creates the password input field.
     *
     * @return textfield of the password
     */
    public TextField createPasswordField() {
        TextField password = new TextField("", getSkin());
        password.setPasswordMode(true);
        password.setHeight(25f);
        password.setPasswordCharacter('*');
        password.setWidth(300);
        password.setPosition(game.getWidth() / 3.33f, game.getHeight() / 1.6f);
        return password;
    }

    public Game getGame() {
        return this.game;
    }

    public Skin getSkin() {
        return StyleUtility.getSkin();
    }

    /**
     * Creates the stage with the given actors.
     */
    @Override
    public void show() {
        createInput(createActors());
    }

    /**
     * Renders the screen with the actors of the stage.
     * @param delta time between this frame and the previous one.
     */
    @Override
    public void render(float delta) {
        //Set the background colour
        Gdx.gl.glEnable(GL20.GL_BLEND);
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
