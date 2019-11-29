import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu implements Screen, InputProcessor {
    private final transient Game game;

    private transient Rectangle button;
    private transient GlyphLayout layout;

    /**
     * Creates the LoginScreen.
     *
     * @param game current instance of the game
     */
    public MainMenu(Game game) {
        Gdx.input.setInputProcessor(this);

        this.game = game;

        BitmapFont font = StyleUtility.getFont();
        font.getRegion().getTexture()
                .setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(1.5f);

        this.button = new Rectangle();
        this.layout = new GlyphLayout();
        layout.setText(font, "Play!");
        button.set(game.getWidth() / 2.35f, game.getHeight() / 1.75f, layout.width, layout.height);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
                || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(50, game));
        }
        //Set the background colour
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = StyleUtility.getBatch();

        batch.begin();
        batch.draw(StyleUtility.getBackground(),
                0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        StyleUtility.getFont()
                .draw(batch, "Play!", game.getWidth() / 2.35f, game.getHeight() / 1.75f);
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


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (isWithin(screenX, screenY)) {
            game.setScreen(new GameScreen(50, game));
            return true;
        }
        return false;
    }

    /**
     * Checks whether the cursor is within the play box.
     *
     * @param x x value of the cursor position
     * @param y value of the cursor position
     * @return true if cursor is within the box
     */
    private boolean isWithin(int x, int y) {
        //add offset
        y = (int) game.getHeight() + 50 - y + 22;
        return x >= button.x && y >= button.y && x <= button.x + layout.width
                && y <= button.y + layout.height;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
