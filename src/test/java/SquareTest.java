import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SquareTest {
    transient int xvalue;
    transient int yvalue;
    transient int width;
    transient int height;
    transient Square square;

    /**
     * Sets up the environment.
     */
    @BeforeEach
    public void setUp() {
        this.xvalue = 0;
        this.yvalue = 10;
        this.width = 10;
        this.height = 50;

        this.square = new Square(xvalue, yvalue, width, height);
    }

    @Test
    public void testConstructor() {
        assertEquals(xvalue, square.getXvalue());
        assertEquals(yvalue, square.getYvalue());
        assertEquals(width, square.getWidth());
        assertEquals(height, square.getHeight());
    }

    @Test
    public void testSetWidth() {
        assertEquals(width, square.getWidth());

        square.setWidth(width + 10);

        assertEquals(width + 10, square.getWidth());
    }

    @Test
    public void testSetHeight() {
        assertEquals(height, square.getHeight());

        square.setHeight(height + 10);

        assertEquals(height + 10, square.getHeight());
    }
}
