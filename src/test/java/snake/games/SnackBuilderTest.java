package snake.games;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.gui.LauncherClass;
import snake.squares.Square;

public class SnackBuilderTest {

    transient LauncherClass launcher;
    transient Random random;
    transient int stepSize = 50;
    transient int squareSize = 50;

    transient SnackBuilder builder;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        this.launcher = mock(LauncherClass.class);
        doReturn(800f).when(launcher).getWidth();
        doReturn(800f).when(launcher).getHeight();

        this.random = mock(Random.class);
        doReturn(100).when(random).nextInt(any(Integer.class));

        builder = spy(new SnackBuilder(launcher, stepSize, squareSize));
        builder.random = random;
    }

    @Test
    public void testConstructor() {
        assertEquals(null, builder.snack);
        assertEquals(null, builder.snackSquare);

        assertEquals(random, builder.random);
        assertEquals(launcher, builder.launcherClass);
        assertEquals(stepSize, builder.stepSize);
        assertEquals(squareSize, builder.squareSize);
    }

    @Test
    public void testCreateSnackInForbidden() {
        int random1 = 0;
        int random2 = 0;
        List<Square> forbiddenSquares = new ArrayList<>(
                Arrays.asList(new Square(0, 0, 0, 0)));

        assertEquals(null, builder.snack);
        assertEquals(null, builder.snackSquare);

        builder.createSnack(random1, random2, forbiddenSquares);

        assertNotNull(builder.snack);
        assertNotNull(builder.snackSquare);
        assertEquals(false, forbiddenSquares.contains(builder.snackSquare));
        verify(builder, times(1)).createSnack(forbiddenSquares);
    }

    @Test
    public void testCreateSnack() {
        int random1 = 5;
        int random2 = 5;
        List<Square> forbiddenSquares = new ArrayList<>(
                Arrays.asList(new Square(0, 0, 0, 0)));

        assertEquals(null, builder.snack);
        assertEquals(null, builder.snackSquare);

        builder.createSnack(random1, random2, forbiddenSquares);

        assertNotNull(builder.snack);
        assertNotNull(builder.snackSquare);
        assertEquals(false, forbiddenSquares.contains(builder.snackSquare));
        verify(builder, times(0)).createSnack(forbiddenSquares);
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

        builder.createSnack(new ArrayList<>());

        assertNotNull(builder.snack);
        assertNotNull(builder.snackSquare);
    }
}
