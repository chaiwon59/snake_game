package snake.squares;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import snake.gui.StyleUtility;

public class ColouredSquare extends Square {
    private transient Color color;

    /**
     * Creates a square with the given color.
     * @param xvalue x coordinate of the square
     * @param yvalue y coordinate of the square
     * @param width  width of the window
     * @param height height of the window
     * @param color color of the square when drawn.
     */
    public ColouredSquare(float xvalue, float yvalue, float width, float height, Color color) {
        super(xvalue, yvalue, width, height);
        this.color = color;
    }

    /**
     * Used to draw the square with the given color.
     * @param batch not used since we're using shaperenderer.
     * @param alpha the alpha of the parent
     */
    @Override
    public void draw(Batch batch, float alpha) {
        ShapeRenderer renderer = StyleUtility.getRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.rect(getXvalue(), getYvalue(), getWidth(), getHeight());
        renderer.end();
    }
}
