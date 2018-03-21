package game.model;

@SuppressWarnings("WeakerAccess")
public class Board {

    private final static int SIZE = 3;
    private final static int targetLength = 3;

    private final static Direction[] directions = {
            new Direction(0, 1), new Direction(1, 0), new Direction(1, 1)
    };

    private final Cell[][] board = new Cell[SIZE][SIZE];

    Board() {
        clear();
    }

    public boolean isEmpty(final int x, final int y) {
        return board[x][y] == Cell.EMPTY;
    }

    public int size() {
        return SIZE;
    }

    public Cell get(final int x, final int y) {
        return board[x][y];
    }

    boolean set(final int x, final int y, final Cell cell) {
        if (isEmpty(x, y)) {
            board[x][y] = cell;
            return true;
        }
        return false;
    }

    void clear() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
    }

    public GameState getState() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (final Direction direction : directions) {
                    final LineState state = getLineState(i, j, direction);
                    switch (state) {
                        case X:
                            return GameState.X_WIN;
                        case O:
                            return GameState.O_WIN;
                    }
                }
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == Cell.EMPTY) {
                    return GameState.IN_PROGRESS;
                }
            }
        }
        return GameState.DRAW;
    }

    private LineState getLineState(final int x, final int y, final Direction direction) {
        final Cell init = board[x][y];
        for (int i = 1; i < targetLength; i++) {
            final int newX = x + i * direction.dx;
            final int newY = y + i * direction.dy;
            if (newX >= SIZE || newY >= SIZE) {
                return LineState.NONE;
            }
            final Cell cell = board[newX][newY];
            if (cell != init) {
                return LineState.NONE;
            }
        }
        switch (init) {
            case X:
                return LineState.X;
            case O:
                return LineState.O;
        }
        return LineState.NONE;
    }

    private enum LineState {
        X, O, NONE
    }

    private static class Direction {
        private final int dx;
        private final int dy;

        public Direction(final int dx, final int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}
