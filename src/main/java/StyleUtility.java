import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.File;

public final class StyleUtility {
    private static final transient BitmapFont font = new BitmapFont(
            Gdx.files.internal("commodore/commodore-64.fnt"));
    private static final transient  SpriteBatch batch = new SpriteBatch();
    private static final transient Skin skin = new Skin(
            Gdx.files.internal("commodore/uiskin.json"));
    private static final transient Texture background = new Texture(
            Gdx.files.internal("snake3.jpg"));
    private static final transient Texture snack = new Texture(
            Gdx.files.internal("apple.png"));
    private static final transient Music snackSound = Gdx.audio.newMusic(
            Gdx.files.internal("sfShort.mp3"));

    public static BitmapFont getFont() {
        return font;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static Skin getSkin() {
        return skin;
    }

    public static Texture getBackground() {
        return background;
    }

    public static Texture getSnack() {
        return snack;
    }

    public static Music getSnackSound() {
        return snackSound;
    }
}
