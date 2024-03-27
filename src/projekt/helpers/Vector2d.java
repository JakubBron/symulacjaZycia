package projekt.helpers;

public class Vector2d {

    private int y, x;

    public Vector2d(int y, int x)
    {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean equals(Vector2d v) {
        return (y == v.y && x == v.x);
    }

    public Vector2d add(Vector2d v) {
        return new Vector2d(v.y + y, v.x + x);
    }

    public void push_back(Vector2d v) {
        y += v.y;
        x += v.x;
    }

    public boolean outOfBounds(int height, int width) {
        return (y < 0 || x < 0 || y >= height || x >= width);
    }

}
