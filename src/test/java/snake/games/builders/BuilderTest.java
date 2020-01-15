package snake.games.builders;

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
import snake.games.Game;
import snake.gui.LauncherClass;
import snake.squares.Square;

public abstract class BuilderTest {
    transient LauncherClass launcher;
    transient Random random;
    transient int stepSize = 50;
    transient int squareSize = 50;

    transient Builder builder;

    transient Game game;

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

        this.game = mock(Game.class);

        builder = spy(createBuilder(game, launcher, stepSize, squareSize));
        builder.random = random;
    }

    public abstract Builder createBuilder(
            Game game, LauncherClass launcher, int stepSize, int squareSize);

    @Test
    public void testConstructor() {
        assertEquals(random, builder.random);
        assertEquals(launcher, builder.launcherClass);
        assertEquals(stepSize, builder.stepSize);
        assertEquals(squareSize, builder.squareSize);
    }

    @Test
    public void testCreateInForbidden() {
        int random1 = 0;
        int random2 = 0;
        List<Square> forbiddenSquares = new ArrayList<>(
                Arrays.asList(new Square(0, 0, 0, 0)));
        doReturn(forbiddenSquares).when(game).getForbiddenSquares();

        Builder.Pair result = builder.create(random1, random2, forbiddenSquares);

        assertNotNull(result.rendered);
        assertNotNull(result.actual);
        assertEquals(false, forbiddenSquares.contains(result.actual));
        verify(builder, times(1)).create();
    }

    @Test
    public void testCreate() {
        int random1 = 5;
        int random2 = 5;
        List<Square> forbiddenSquares = new ArrayList<>(
                Arrays.asList(new Square(0, 0, 0, 0)));

        Builder.Pair result = builder.create(random1, random2, forbiddenSquares);

        assertNotNull(result.rendered);
        assertNotNull(result.actual);
        assertEquals(false, forbiddenSquares.contains(result.actual));
        verify(builder, times(0)).create();
    }
}
