package utils;

public class Direction {

    public final static Direction[] ANGLE_DIRECTIONS = {
            new Direction(0, 1), new Direction(1, 0), new Direction(1, 1), new Direction(-1, 1)
    };

    private final int dx;
    private final int dy;

    public Direction(final int dx, final int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }
}
