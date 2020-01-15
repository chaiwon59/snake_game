package snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserTest {
    private transient User user;
    private transient Preferences prefs;

    /**
     * Sets up the test environment, including headless application.
     */
    @BeforeEach
    public void setUp() {
        HeadlessNativesLoader.load();

        Gdx.files = Mockito.mock(Files.class);
        Gdx.app = Mockito.mock(Application.class);
        prefs = Mockito.mock(Preferences.class);

        Mockito.doReturn(prefs).when(Gdx.app).getPreferences(any(String.class));
        doReturn(false).when(prefs).getBoolean("noWalls");
        doReturn(true).when(prefs).getBoolean("noGrid");
        this.user = new User();
    }

    @Test
    public void testConstructor() {
        assertEquals(null, user.getUsername());
        assertEquals(false, user.isNoWalls());
        assertEquals(true, user.isNoGrid());

        verify(prefs, Mockito.times(1)).getBoolean("noWalls");
        verify(prefs, Mockito.times(1)).getBoolean("noGrid");
    }

    @Test
    public void testUsername() {
        assertEquals(null, user.getUsername());
        user.setUsername("test");
        assertEquals("test", user.getUsername());
    }

    @Test
    public void testNoWalls() {
        assertEquals(false, user.isNoWalls());
        user.setNoWalls(true);
        assertEquals(true, user.isNoWalls());
    }

    @Test
    public void testFlushSettings() {
        user.flushSettings();

        verify(prefs, Mockito.times(1)).putBoolean("noWalls", false);
        verify(prefs, Mockito.times(1)).putBoolean("noGrid", true);
        verify(prefs, times(1)).flush();
    }
}
