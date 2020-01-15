package snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class User {
    private transient String username;
    private transient Preferences prefs;
    private transient boolean noWalls;
    private transient boolean noGrid;

    /**
     * Constructs a user with default value of false.
     *      Used to keep track of the settings of the current user.
     */
    public User() {
        this.prefs = Gdx.app.getPreferences("prefs");

        this.noWalls = prefs.getBoolean("noWalls");
        this.noGrid = prefs.getBoolean("noGrid");
    }

    /**
     * Makes sure that the settings are persisted.
     */
    public void flushSettings() {
        prefs.putBoolean("noWalls", noWalls);
        prefs.putBoolean("noGrid", noGrid);

        prefs.flush();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setNoWalls(boolean noWalls) {
        this.noWalls = noWalls;
    }

    public boolean isNoWalls() {
        return this.noWalls;
    }

    public void setNoGrid(boolean noGrid) {
        this.noGrid = noGrid;
    }

    public boolean isNoGrid() {
        return this.noGrid;
    }
}
