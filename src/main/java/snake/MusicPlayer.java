package snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
            Music snackSound = StyleUtility.getSnackSound();
            if (snackSound.isPlaying()) {
                snackSound.stop();
            }
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
            Music multiplierSound = StyleUtility.getMultiplierSound();
            if (multiplierSound.isPlaying()) {
                multiplierSound.stop();
            }
            multiplierSound.play();
        }).start();
    }

    /**
     * Plays the music for the snack, checks first whether the sound if current playing.
     */
    public static void playSpeedMusic() {
        new Thread(() -> {
            Music speedUpSound = StyleUtility.getSpeedupSound();
            if (speedUpSound.isPlaying()) {
                speedUpSound.stop();
            }
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
