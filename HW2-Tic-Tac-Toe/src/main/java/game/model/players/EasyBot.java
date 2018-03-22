package game.model.players;

import game.model.Board;

public class EasyBot extends OptimizationBot {

    @Override
    protected int getScore(final Board board) {
        int score = 0;

        if (isGameWon()) {
            return Integer.MAX_VALUE;
        }

        if (board.get(board.size() / 2, board.size() / 2) == ourCell) {
            score += 50;
        }

        final int[] xs = {0, board.size() - 1};
        for (final int i : xs) {
            for (final int j : xs) {
                if (board.get(i, j) == ourCell) {
                    score += 10;
                }
            }
        }
        return score;
    }

}
