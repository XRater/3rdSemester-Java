package model;

import view.View;

public class Model {

    private static final Turn FIRST_TURN = Turn.X;

    private final View view;

    private final Board board = new Board();
    private Turn turn = FIRST_TURN;

    public Model(final View view) {
        this.view = view;
    }

    public void turn(final int x, final int y) {
        final Cell cell = turn == Turn.X ? Cell.X : Cell.O;
        if (board.set(x, y, cell)) {
            view.set(x, y, cell);
            if (turn == Turn.X) {
                turn = Turn.O;
            } else {
                turn = Turn.X;
            }
        }
    }

    public enum Turn {
        X, O;
    }

}
