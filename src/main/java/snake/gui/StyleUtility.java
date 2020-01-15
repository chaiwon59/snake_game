package snake.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.ArrayList;
import java.util.LinkedList;
import snake.games.powerups.PowerUp;
import snake.games.powerups.ScoreIncreasePowerUp;
import snake.games.powerups.SpeedPowerUp;

public final class StyleUtility {
    private static final transient BitmapFont font = new BitmapFont(
            Gdx.files.internal("commodore/commodore-64.fnt"));
    private static final transient SpriteBatch batch = new SpriteBatch();
    private static final transient Skin skin = new Skin(
            Gdx.files.internal("commodore/uiskin.json"));
    private static final transient Texture background = new Texture(
            Gdx.files.internal("snake3.jpg"));
    private static final transient Texture snack = new Texture(
            Gdx.files.internal("apple.png"));
    private static final transient Music deathSound = Gdx.audio.newMusic(
            Gdx.files.internal("robloxdeathsound.mp3"));
    private static final transient Music snackSound = Gdx.audio.newMusic(
            Gdx.files.internal("coin.mp3"));
    private static final transient Music menuSound1 = Gdx.audio.newMusic(
            Gdx.files.internal("riders.mp3"));
    private static final transient Music ingame1 = Gdx.audio.newMusic(
            Gdx.files.internal("better.mp3"));
    private static final transient Music ingame2 = Gdx.audio.newMusic(
            Gdx.files.internal("sinatra.mp3"));
    private static final transient Texture multiplier = new Texture(
            Gdx.files.internal("2x.png"));
    private static final transient Music multiplierSound = Gdx.audio.newMusic(
            Gdx.files.internal("sfShort.mp3"));
    private static final transient Texture speedup = new Texture(
            Gdx.files.internal("speedup.png"));
    private static final transient Music speedupSound = Gdx.audio.newMusic(
            Gdx.files.internal("speed.mp3"));

    private static final transient ShapeRenderer renderer = new ShapeRenderer();
    private static final Color green = new Color(0, 1, 0, 1);
    private static final Color lightGrey = new Color(0.2f, 0.2f, 0.2f, 1);
    private static final Color pausedColor = new Color(0.6f, 0.6f, 0.6f, 0.7f);

    public static ShapeRenderer getRenderer() {
        return renderer;
    }

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

    public static Music getDeathSound() {
        return deathSound;
    }

    public static Color getGreen() {
        return green;
    }

    public static Color getLightGrey() {
        return lightGrey;
    }

    public static Color getPausedColor() {
        return pausedColor;
    }

    public static Music getMenuSound1() {
        return menuSound1;
    }

    public static Texture getMultiplier() {
        return multiplier;
    }

    public static Music getMultiplierSound() {
        return multiplierSound;
    }

    public static Texture getSpeedup() {
        return speedup;
    }

    public static Music getSpeedupSound() {
        return speedupSound;
    }

    /**
     * Returns the appropriate texture of the given power up.
     * @param power powerup of which the texture will be provided
     * @return texture of the power up
     */
    public static Texture getPowerUpTexture(PowerUp power) {
        if (power instanceof ScoreIncreasePowerUp) {
            return multiplier;
        } else if (power instanceof SpeedPowerUp) {
            return speedup;
        }
        return null;
    }


    /**
     * Gets a randomized music sound for the music.
     *
     * @return given music
     */
    public static Music getIngame() {
        ArrayList<Music> musicList = new ArrayList<>();
        LinkedList<Music> ll = new LinkedList<>();
        ll.add(ingame2);
        ll.add(ingame1);
        musicList.add(ingame1);
        musicList.add(ingame2);
        int randomIndex = (int) (Math.random() * musicList.size());
        return musicList.get(randomIndex);
        //return ll.get(randomIndex);
    }

}
