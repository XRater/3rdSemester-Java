package game.model.players;

import game.model.Board;
import game.model.Cell;
import org.jetbrains.annotations.NotNull;
import utils.Direction;

/**
 * Hard bot for tic-tac-toe 3x3 game.
 * <p>
 * This bot compares different turns by optimality function. Therefore, it is easy to
 * modify this bot by changing {@link HardBot#getScore(Board)} function.
 */
public class HardBot extends OptimizationBot {

    @Override
    protected int getScore(@NotNull final Board board) {
        int score = 10;
        final int size = board.size();
        final int target = 3;

        if (isGameWon()) {
            return Integer.MAX_VALUE;
        }

        // check if opponent can end the game
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (final Direction direction : Direction.ANGLE_DIRECTIONS) {
                    final int mx = i + direction.dx() * (target - 1);
                    final int my = j + direction.dy() * (target - 1);
                    if (mx >= size || my >= size || mx < 0 || my < 0) {
                        continue;
                    }
                    int theirCnt = 0;
                    int ourCnt = 0;
                    for (int k = 0; k < target; k++) {
                        final int nx = i + direction.dx() * k;
                        final int ny = j + direction.dy() * k;
                        if (nx < size && ny < size) {
                            final Cell cell = board.get(nx, ny);
                            if (cell == opponentCell) {
                                theirCnt++;
                            }
                            if (cell == ourCell) {
                                ourCnt++;
                            }
                        }
                    }
                    if (ourCnt == 0 && theirCnt >= target - 1) {
                        score -= 10000;
                    }
                    if (theirCnt == 0 && ourCnt >= target - 1) {
                        score += 1000;
                    }
                    if (theirCnt == 0 && ourCnt >= 1) {
                        score += 100;
                    }
                }
            }
        }

        if (board.get(size / 2, size / 2) == ourCell) {
            score += 20;
        }

        final int[] xs = {0, board.size() - 1};
        for (final int i : xs) {
            for (final int j : xs) {
                if (board.get(i, j) == ourCell) {
                    score += 10;
                }
            }
        }

        return Math.max(score, 0);
    }
}
