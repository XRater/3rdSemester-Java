package game.model.players;

import game.model.Board;
import game.model.Cell;
import game.model.GameState;
import game.model.Model;

@SuppressWarnings("WeakerAccess")
abstract class OptimizationBot implements Bot {

    private Model model;
    protected Board board;

    protected Cell ourCell;
    protected Cell opponentCell;

    @Override
    public final void initBot(final Model model, final Model.Turn turn) {
        this.model = model;
        board = model.getBoard();
        ourCell = turn == Model.Turn.X ? Cell.X : Cell.O;
        opponentCell = turn == Model.Turn.X ? Cell.O : Cell.X;
    }

    @Override
    public void getTurn() {
        int x = 0;
        int y = 0;
        int score = -1;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (board.get(i, j) != Cell.EMPTY) {
                    continue;
                }
                board.override(i, j, ourCell);
                final int newScore = getScore(board);
                board.override(i, j, Cell.EMPTY);
                if (newScore > score) {
                    x = i;
                    y = j;
                    score = newScore;
                }
            }
        }

        if (score == -1) {
            throw new NoTurnException();
        }

        model.turn(x, y);
    }

    protected abstract int getScore(final Board board);

    protected boolean isGameWon() {
        final GameState state = board.getState();
        return state == GameState.X_WIN && ourCell == Cell.X ||
                state == GameState.O_WIN && ourCell == Cell.O;
    }
}
