package model;

class Board {

    private static final boolean MAY_OVERRIDE = false;
    private final static int SIZE = 3;

    private final Cell[][] board = new Cell[SIZE][SIZE];

    Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
    }

    public boolean isEmpty(final int x, final int y) {
        return board[x][y] == Cell.EMPTY;
    }

    public boolean set(final int x, final int y, final Cell cell) {
        if (MAY_OVERRIDE || isEmpty(x, y)) {
            board[x][y] = cell;
            return true;
        }
        return false;
    }
}
