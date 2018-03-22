package game.model;

import game.gameTypes.GameOptions;
import game.model.players.Bot;
import game.model.players.Player;

import java.util.Iterator;

public class Model {

    private final GameSession gameSession;

    private final Board board = new Board();
    private final Iterator<Player> iterator;

    private Turn turn;
    private Player player;

    private boolean gameInProgress = true;

    Model(final GameSession gameSession, final GameOptions gameOptions) {
        this.gameSession = gameSession;

        iterator = gameOptions.getPlayers().cycleIterator();
        player = iterator.next();
        turn = gameOptions.getFirstTurn();

        // Init bots
        for (final Player player : gameOptions.getPlayers()) {
            if (player instanceof Bot) {
                ((Bot) player).initBot(this);
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public boolean turn(final int x, final int y) {
        if (!gameInProgress) {
            return false;
        }
        final Cell cell = turn == Turn.X ? Cell.X : Cell.O;
        if (board.set(x, y, cell)) {
            gameSession.set(x, y, cell);
            if (checkGameEnd()) {
                gameInProgress = false;
                return true;
            }
            nextPlayer();
            return true;
        }
        return false;
    }

    private boolean checkGameEnd() {
        final GameState state = board.getState();
        if (state != GameState.IN_PROGRESS) {
            gameSession.onGameEnd(state);
            return true;
        }
        return false;
    }

    private void nextPlayer() {
        player = iterator.next();
        if (turn == Turn.X) {
            turn = Turn.O;
        } else {
            turn = Turn.X;
        }
        player.getTurn();
    }

    public enum Turn {
        X, O
    }

}
