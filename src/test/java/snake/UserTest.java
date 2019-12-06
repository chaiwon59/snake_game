package snake;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private transient User user;

    @BeforeEach
    public void setUp() {
        this.user = new User();
    }

    @Test
    public void testConstructor() {
        assertEquals(null, user.getUsername());
        assertEquals(false, user.isNoWalls());
    }

    @Test
    public void testSetUsername() {
        assertEquals(null, user.getUsername());
        user.setUsername("test");
        assertEquals("test", user.getUsername());
    }

    @Test
    public void testSetNoWalls() {
        assertEquals(false, user.isNoWalls());
        user.setNoWalls(true);
        assertEquals(true, user.isNoWalls());
    }
}
