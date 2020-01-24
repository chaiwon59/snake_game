package snake.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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
    private transient LauncherClass launcherClass;
    private transient Stage stage;
    private transient GuiCreator creator;

    /**
     * Creates the snake.Gui.LoginScreen.
     *
     * @param launcherClass current instance of the game
     */
    public InputScreen(LauncherClass launcherClass) {
        this.launcherClass = launcherClass;
        this.creator = new GuiCreator(launcherClass);
    }

    /**
     * Creates a stage and sets the stage as the inputProcessor.
     *
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
     *
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
    public Label createLabel(String text, Float x, float y) {
        return creator.createLabel(text, x, y);
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
        return creator.createLabelWithOnClick(text, y, listener);
    }

    /**
     * Creates button with the given input.
     *
     * @param text     text of the button
     * @param x        x-coordinate of the button,
     *                 can be null to indicate that the button needs to be in the center
     * @param y        y-coordinate of the button
     * @param listener listener of the button
     * @return button with the given attributes
     */
    public TextButton createButton(String text, Float x, float y, ClickListener listener) {
        return creator.createButton(text, x, y, listener);
    }

    /**
     * Creates a new dialog with the given error message.
     * @param text text of the error message
     */
    public void createDialog(String text, String title) {
        creator.createDialog(text, title, stage);
    }

    public LauncherClass getLauncherClass() {
        return this.launcherClass;
    }

    public Skin getSkin() {
        return StyleUtility.getSkin();
    }

    public Stage getStage() {
        return stage;
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
     *
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
        batch.end();

        stage.act();
        stage.draw();
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
