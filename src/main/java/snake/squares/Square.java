package snake.squares;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Objects;

public class Square extends Actor {
    private transient float xvalue;
    private transient float yvalue;
    private transient float width;
    private transient float height;

    /**
     * Constructor of the snake.squares.Square class.
     *
     * @param xvalue x coordinate of the square
     * @param yvalue y coordinate of the square
     * @param width  width of the window
     * @param height height of the window
     */
    public Square(float xvalue, float yvalue, float width, float height) {
        this.xvalue = xvalue;
        this.yvalue = yvalue;
        this.width = width;
        this.height = height;
    }

    public float getXvalue() {
        return xvalue;
    }

    public float getYvalue() {
        return yvalue;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "x: " + xvalue + " y: " + yvalue + " width: " + width + " height: " + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Square)) {
            return false;
        }
        Square square = (Square) o;
        return Float.compare(square.getXvalue(), getXvalue()) == 0
                && Float.compare(square.getYvalue(), getYvalue()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getXvalue(), getYvalue(), getWidth(), getHeight());
    }
}
