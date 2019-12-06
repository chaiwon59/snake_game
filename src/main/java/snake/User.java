package snake;

public class User {
    private transient String username;
    private transient boolean noWalls;
    private transient boolean noGrid;

    /**
     * Constructs a user with default value of false.
     *      Used to keep track of the settings of the current user.
     */
    public User() {
        this.noWalls = false;
        this.noGrid = false;
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
