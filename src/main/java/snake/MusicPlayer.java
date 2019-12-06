package snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import snake.gui.StyleUtility;

public final class MusicPlayer {

    /**
     * Used to make sure that the right music is stopped when calling the stop method.
     */
    private static transient Music inGameMusic;

    /**
     * Plays the music for the snack, checks first whether the sound if current playing.
     */
    public static void playSnackMusic() {
        new Thread(() -> {
            Gdx.app.postRunnable(() -> {
                Music snackSound = StyleUtility.getSnackSound();
                if (snackSound.isPlaying()) {
                    snackSound.stop();
                }
                snackSound.play();
            });
        }).start();
    }

    /**
     * Plays the sound when dying.
     */
    public static void playDeathMusic() {
        new Thread(() -> {
            Gdx.app.postRunnable(() -> StyleUtility.getDeathSound().play());
        }).start();
    }

    /**
     * Plays the sound when in game (randomly picked).
     */
    public static void playInGame() {
        new Thread(() -> {
            Gdx.app.postRunnable(() -> {
                inGameMusic = StyleUtility.getIngame();
                inGameMusic.play();
            });
        }).start();
    }

    /**
     * Stops the InGame music that is currently playing.
     */
    public static void stopInGame() {
        new Thread(() -> {
            Gdx.app.postRunnable(() -> {
                inGameMusic.stop();
            });
        }).start();
    }

    /**
     * Plays the menu music.
     */
    public static void playMenu() {
        new Thread(() -> {
            Gdx.app.postRunnable(() -> StyleUtility.getMenuSound1().play());
        }).start();
    }

    /**
     * Stops the menu music.
     */
    public static void stopMenu() {
        new Thread(() -> {
            Gdx.app.postRunnable(() -> StyleUtility.getMenuSound1().stop());
        }).start();
    }
}
