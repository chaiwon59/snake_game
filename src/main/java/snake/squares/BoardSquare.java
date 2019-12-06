package snake.squares;

import com.badlogic.gdx.graphics.Color;

public class BoardSquare extends ColouredSquare {
    private static final Color boardColor = new Color(0.6f, 0.6f, 0.6f, 1);

    /**
     * Creates a ColouredSquare with the board color.
     * @param xvalue x coordinate of the square
     * @param yvalue y coordinate of the square
     * @param width  width of the window
     * @param height height of the window
     */
    public BoardSquare(float xvalue, float yvalue, float width, float height) {
        super(xvalue, yvalue, width, height, boardColor);
    }
}
