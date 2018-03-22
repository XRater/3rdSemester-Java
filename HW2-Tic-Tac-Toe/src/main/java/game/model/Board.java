package game.model;

import utils.Direction;

@SuppressWarnings("WeakerAccess")
public class Board {

    private final static int SIZE = 3;
    private final static int targetLength = 3;

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

    // this method is public for bot only.
    public boolean set(final int x, final int y, final Cell cell) {
        if (isEmpty(x, y)) {
            board[x][y] = cell;
            return true;
        }
        return false;
    }

    public void override(final int x, final int y, final Cell cell) {
        board[x][y] = cell;
    }

    // this method is public for ot only.
    public void clear() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
    }

    public GameState getState() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (final Direction direction : Direction.ANGLE_DIRECTIONS) {
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
            final int newX = x + i * direction.dx();
            final int newY = y + i * direction.dy();
            if (newX >= SIZE || newY >= SIZE || newX < 0 || newY < 0) {
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
}
