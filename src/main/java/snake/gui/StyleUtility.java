package snake.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import snake.games.powerups.PowerUp;
import snake.games.powerups.ScoreIncreasePowerUp;
import snake.games.powerups.SpeedPowerUp;

public final class StyleUtility {
    private static HashMap<String, Sound> music = new HashMap<>();
    private static HashMap<String, Texture> texture = new HashMap<>();

    private static final transient SpriteBatch batch = new SpriteBatch();
    private static final transient Skin skin = new Skin(
            Gdx.files.internal("commodore/uiskin.json"));

    private static final transient Music ingame1 = Gdx.audio.newMusic(
            Gdx.files.internal("better.mp3"));
    private static final transient Music ingame2 = Gdx.audio.newMusic(
            Gdx.files.internal("sinatra.mp3"));

    private static final transient ShapeRenderer renderer = new ShapeRenderer();
    private static final Color green = new Color(0, 1, 0, 1);
    private static final Color lightGrey = new Color(0.2f, 0.2f, 0.2f, 1);
    private static final Color pausedColor = new Color(0.6f, 0.6f, 0.6f, 0.7f);

    private static FileHandle getFile(String fileName) {
        return Gdx.files.internal(fileName);
    }

    private static Sound getMusic(String fileName) {
        return music.getOrDefault(fileName,
                music.putIfAbsent(fileName, Gdx.audio.newSound(getFile(fileName))));
    }

    private static Texture getTexture(String fileName) {
        return texture.getOrDefault(fileName,
                texture.putIfAbsent(fileName, new Texture(getFile(fileName))));
    }

    public static ShapeRenderer getRenderer() {
        return renderer;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static Skin getSkin() {
        return skin;
    }

    public static Texture getBackground() {
        return getTexture("snake3.jpg");
    }

    public static Texture getSnack() {
        return getTexture("apple.png");
    }

    public static Sound getSnackSound() {
        return getMusic("coin.mp3");
    }

    public static Sound getDeathSound() {
        return getMusic("robloxdeathsound.mp3");
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

    public static Sound getMenuSound1() {
        return getMusic("riders.mp3");
    }

    public static Sound getMultiplierSound() {
        return getMusic("sfShort.mp3");
    }

    public static Sound getSpeedupSound() {
        return getMusic("speed.mp3");
    }

    /**
     * Returns the appropriate texture of the given power up.
     * @param power powerup of which the texture will be provided
     * @return texture of the power up
     */
    public static Texture getPowerUpTexture(PowerUp power) {
        if (power instanceof ScoreIncreasePowerUp) {
            return getTexture("2x.png");
        } else if (power instanceof SpeedPowerUp) {
            return getTexture("speedup.png");
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
    }

}
