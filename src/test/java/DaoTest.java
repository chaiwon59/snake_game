import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DaoTest {
    private transient Dao dao;

    private transient BCryptPasswordEncoder encoder;
    private transient PreparedStatement query;
    private transient Connection conn;
    private transient ResultSet result;
    private transient Random random;

    /**
     * Setup method.
     * @throws SQLException never thrown since we're mocking
     * @throws ClassNotFoundException thrown if the driver cannot be found
     */
    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        this.encoder = mock(BCryptPasswordEncoder.class);
        this.query = mock(PreparedStatement.class);
        this.conn = mock(Connection.class);
        this.result = mock(ResultSet.class);

        this.dao = new Dao(conn, encoder);
        this.random = new Random();

        doReturn(query).when(conn).prepareStatement(any(String.class));
        doReturn(result).when(query).executeQuery();
    }

    @Test
    public void testCheckNonExistentUserName() throws SQLException {
        doReturn(false).when(result).next();
        assertFalse(dao.checkExistentUsername(RandomString.make()));
    }

    @Test
    public void testCheckExistentUserName() throws SQLException {
        doReturn(true).when(result).next();
        assertTrue(dao.checkExistentUsername(RandomString.make()));
    }

    @Test
    public void testCheckUsernameError() throws SQLException {
        doThrow(SQLException.class).when(query).setString(any(Integer.class), any(String.class));
        assertEquals(true, dao.checkExistentUsername(RandomString.make()));
    }


    @Test
    public void insertUserExistentUsername() throws SQLException {
        doReturn(true).when(result).next();
        assertEquals(false, dao.insertUser(RandomString.make(), RandomString.make()));
    }

    @Test
    public void insertUserNonExistentUsername() throws SQLException {
        doReturn(false).when(result).next();
        doReturn(1).when(query).executeUpdate();
        assertEquals(true, dao.insertUser(RandomString.make(), RandomString.make()));
    }

    @Test
    public void insertUserFailedUpdate() throws SQLException {
        doReturn(false).when(result).next();
        doReturn(0).when(query).executeUpdate();
        assertEquals(false, dao.insertUser(RandomString.make(), RandomString.make()));
    }

    @Test
    public void insertUserError() throws SQLException {
        doThrow(SQLException.class).when(conn).prepareStatement(any(String.class));
        assertEquals(false, dao.insertUser(RandomString.make(), RandomString.make()));
    }



    @Test
    public void checkUserNamePasswordCorrect() throws SQLException {
        String password = RandomString.make();
        String passwordEncoded = encoder.encode(password);

        doReturn(true).when(result).next();
        doReturn(passwordEncoded).when(result).getString(1);
        doReturn(true).when(encoder).matches(password, passwordEncoded);

        assertEquals(true, dao.checkUsernamePassword(RandomString.make(), password));
    }

    @Test
    public void checkUserNamePasswordInCorrect() throws SQLException {
        doReturn(true).when(result).next();
        doReturn(false).when(encoder).matches(any(String.class), any(String.class));

        assertEquals(false, dao.checkUsernamePassword(RandomString.make(), RandomString.make()));
    }

    @Test
    public void checkUserNamePasswordIncorrectUsername() throws SQLException {
        doReturn(true).when(result).next();
        doReturn(true).when(encoder).matches(any(String.class), any(String.class));

        assertEquals(false, dao.checkUsernamePassword(RandomString.make(), RandomString.make()));
    }

    @Test
    public void checkUserNamePasswordError() throws SQLException {
        doThrow(SQLException.class).when(conn).prepareStatement(any(String.class));

        assertEquals(false, dao.checkUsernamePassword(RandomString.make(), RandomString.make()));
    }


    @Test
    public void getHighscore() throws SQLException {
        doReturn(true, false).when(result).next();
        doReturn(25).when(result).getInt(1);

        assertEquals(25, dao.getHighscore(RandomString.make()));
    }

    @Test
    public void getHighscoreError() throws SQLException {
        doThrow(SQLException.class).when(conn).prepareStatement(any(String.class));

        assertEquals(-1, dao.getHighscore(RandomString.make()));
    }

    @Test
    public void getTop5_5people() throws SQLException {
        doReturn(true, true, true, true, true, false).when(result).next();
        doReturn(25, 10, 30, 20, 15).when(result).getInt(1);
        doReturn("test1", "test2", "test3", "test4", "test5").when(result).getString(2);

        List<String> result = dao.getTop5();

        assertEquals(5, result.size());
        assertEquals("test1 - 25", result.get(0));
        assertEquals("test2 - 10", result.get(1));
        assertEquals("test3 - 30", result.get(2));
        assertEquals("test4 - 20", result.get(3));
        assertEquals("test5 - 15", result.get(4));
    }

    @Test
    public void getTop5_1people() throws SQLException {
        doReturn(true, false).when(result).next();
        doReturn(25).when(result).getInt(1);
        doReturn("test").when(result).getString(2);

        List<String> result = dao.getTop5();

        assertEquals(1, result.size());
        assertEquals("test - 25", result.get(0));
    }

    @Test
    public void getTop5Error() throws SQLException {
        doThrow(SQLException.class).when(conn).prepareStatement(any(String.class));

        assertEquals(0, dao.getTop5().size());
    }


    @Test
    public void updateHighscoreSuccess() throws SQLException {
        doReturn(1).when(query).executeUpdate();

        assertEquals(true, dao.setHighscore(RandomString.make(), random.nextInt()));
    }

    @Test
    public void updateHighscoreFailure() throws SQLException {
        doReturn(0).when(query).executeUpdate();

        assertEquals(false, dao.setHighscore(RandomString.make(), random.nextInt()));
    }

    @Test
    public void updateHighscoreError() throws SQLException {
        doThrow(SQLException.class).when(conn).prepareStatement(any(String.class));

        assertEquals(false, dao.setHighscore(RandomString.make(), random.nextInt()));
    }
}
