package snake.squares;

import com.badlogic.gdx.graphics.Color;

public class SnakeSquare extends ColouredSquare {
    private static final Color snakeColor = new Color(0, 0, 0, 1);

    /**
     * Creates a ColouredSquare with the default snake colour.
     * @param xvalue x coordinate of the square
     * @param yvalue y coordinate of the square
     * @param width  width of the window
     * @param height height of the window
     */
    public SnakeSquare(float xvalue, float yvalue, float width, float height) {
        super(xvalue, yvalue, width, height, snakeColor);
    }
}
