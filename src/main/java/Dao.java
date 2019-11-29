import com.badlogic.gdx.Gdx;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Dao {
    private transient Connection conn;
    private transient BCryptPasswordEncoder encoder;
    private transient ResultSet result;

    /**
     * Default constructor for dao.
     *
     * @throws SQLException           if no connection can be made
     * @throws ClassNotFoundException if the driver cannot be found
     */
    public Dao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://projects-db.ewi.tudelft.nl:3306"
                            + "/projects_SEM-Group8?serverTimezone=UTC",
                    "pu_SEM-Group8", "i274X04mJFgt");
            this.encoder = new BCryptPasswordEncoder();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    /**
     * Creates dao with the given connection and encoder.
     *
     * @param conn    connection to be used
     * @param encoder encoder to be used
     * @throws ClassNotFoundException if the driver cannot be found
     */
    public Dao(Connection conn, BCryptPasswordEncoder encoder) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = conn;
        this.encoder = encoder;
    }

    /**
     * Checks whether the username/password combination is valid in the database.
     *
     * @param username username of the user
     * @param password password of the user
     * @return true if the combination is correct, false if incorrect
     * @throws SQLException when the resultset cannot be closed
     */
    public boolean checkUsernamePassword(String username, String password) throws SQLException {
        try {
            PreparedStatement query = conn.prepareStatement(
                    "SELECT password from user WHERE username=?");
            query.setString(1, username);

            result = query.executeQuery();
            if (result.next()
                    && encoder.matches(password, result.getString(1))) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }

    /**
     * Inserts user into the database.
     *
     * @param username username to be inserted
     * @param password password to be inserted
     * @return true if the user was successfully inserted,
     *      false if the username already existed
     */
    public boolean insertUser(String username, String password) {
        if (checkExistentUsername(username)) {
            return false;
        }
        try {
            PreparedStatement query = conn.prepareStatement(
                    "INSERT INTO user (username, password, highscore) VALUES (?, ?, 0)");
            query.setString(1, username);
            query.setString(2, encoder.encode(password));
            return query.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Checks whether the user already exists in the database.
     *
     * @param username username to be checked
     * @return true if the username already exists, false if the username doesn't exist
     */
    public boolean checkExistentUsername(String username) {
        try {
            PreparedStatement query = conn.prepareStatement(
                    "SELECT username FROM user WHERE username =?");
            query.setString(1, username);

            return query.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Gets the highscore of the specified user.
     *
     * @param username username of the user.
     * @return integer representing the high score of the user
     * @throws SQLException if the resultset cannot be closed
     */
    public int getHighscore(String username) throws SQLException {
        try {
            PreparedStatement query = conn.prepareStatement(
                    "SELECT highscore FROM user WHERE username=?");
            query.setString(1, username);

            result = query.executeQuery();
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }

    /**
     * Gets the global top 5 high scores in the database.
     *
     * @return list with integers representing the top 5 high scores
     * @throws SQLException if the result set cannot be closed
     */
    public List<String> getTop5() throws SQLException {
        List<String> resultList = new ArrayList<>();
        try {
            PreparedStatement query = conn.prepareStatement(
                    "SELECT highscore, username FROM user ORDER BY highscore DESC LIMIT 5");

            result = query.executeQuery();

            while (result.next()) {
                resultList.add(result.getString(2) + " - " + result.getInt(1));
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }

    /**
     * Method which updates the highscore of the given user.
     *
     * @param username username of the user
     * @param score    new score of the user
     * @return boolean indicating whether the update was succesfull
     */
    public boolean setHighscore(String username, int score) {
        try {
            PreparedStatement query = conn.prepareStatement(
                    "UPDATE user SET highscore=? WHERE username=?");
            query.setInt(1, score);
            query.setString(2, username);

            return query.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
