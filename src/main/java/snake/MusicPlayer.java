package snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import snake.games.powerups.PowerUp;
import snake.games.powerups.ScoreIncreasePowerUp;
import snake.games.powerups.SpeedPowerUp;
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
            Sound snackSound = StyleUtility.getSnackSound();
            snackSound.stop();
            snackSound.play();
        }).start();
    }

    /**
     * Plays music depending on the powerup.
     *
     * @param powerUp powerup of which the music will be played.
     */
    public static void playPowerUpMusic(PowerUp powerUp) {
        if (powerUp instanceof ScoreIncreasePowerUp) {
            playMultiplierMusic();
        } else if (powerUp instanceof SpeedPowerUp) {
            playSpeedMusic();
        }
    }

    /**
     * Plays the music for the Score Increase, checks first whether the sound if current playing.
     */
    public static void playMultiplierMusic() {
        new Thread(() -> {
            Sound multiplierSound = StyleUtility.getMultiplierSound();
            multiplierSound.stop();
            multiplierSound.play();
        }).start();
    }

    /**
     * Plays the music for the snack, checks first whether the sound if current playing.
     */
    public static void playSpeedMusic() {
        new Thread(() -> {
            Sound speedUpSound = StyleUtility.getSpeedupSound();
            speedUpSound.stop();
            speedUpSound.play();
        }).start();
    }

    /**
     * Plays the sound when dying.
     */
    public static void playDeathMusic() {
        new Thread(() -> StyleUtility.getDeathSound().play()).start();
    }

    /**
     * Plays the sound when in game (randomly picked).
     */
    public static void playInGame() {
        new Thread(() -> {
            inGameMusic = StyleUtility.getIngame();
            inGameMusic.play();
        }).start();
    }

    /**
     * Stops the InGame music that is currently playing.
     */
    public static void stopInGame() {
        new Thread(() -> {
            inGameMusic.stop();
        }).start();
    }

    /**
     * Plays the menu music.
     */
    public static void playMenu() {
        new Thread(() -> StyleUtility.getMenuSound1().play()).start();
    }

    /**
     * Stops the menu music.
     */
    public static void stopMenu() {
        new Thread(() -> StyleUtility.getMenuSound1().stop()).start();
    }
}
