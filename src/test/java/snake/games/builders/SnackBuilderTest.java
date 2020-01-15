package snake.games.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.games.Game;
import snake.gui.LauncherClass;

public class SnackBuilderTest extends BuilderTest {
    transient SnackBuilder builder;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        this.builder = (SnackBuilder) super.builder;
    }

    @Override
    public Builder createBuilder(Game game, LauncherClass launcher, int stepSize, int squareSize) {
        return new SnackBuilder(game, launcher, stepSize, squareSize);
    }

    @Test
    public void testConstructor() {
        super.testConstructor();

        assertEquals(null, builder.snack);
        assertEquals(null, builder.snackSquare);
    }

    @Test
    public void testGetSnack() {
        assertEquals(null, builder.getSnack());
    }

    @Test
    public void testGetSnackSquare() {
        assertEquals(null, builder.getSnackSquare());
    }

    @Test
    public void testReset() {
        assertEquals(null, builder.snack);
        assertEquals(null, builder.snackSquare);

        builder.createSnack();

        assertNotNull(builder.snack);
        assertNotNull(builder.snackSquare);
    }
}
