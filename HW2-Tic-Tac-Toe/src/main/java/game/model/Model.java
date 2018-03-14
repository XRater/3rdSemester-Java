package game.model;

import game.GameOptions;
import game.model.players.Bot;
import game.model.players.Player;
import view.View;

import java.util.Iterator;

public class Model {

    private final View view;
    private boolean isInitialized;

    private final Board board = new Board();
    private Turn turn;

    private Iterator<Player> iterator;
    private Player player;

    public Model(final View view) {
        this.view = view;
    }

    public void initialize(final GameOptions gameOptions) {
        board.clear();

        iterator = gameOptions.getPlayers().circleIterator();
        player = iterator.next();
        turn = gameOptions.getFirstTurn();

        // Init bots
        for (final Player player : gameOptions.getPlayers()) {
            if (player instanceof Bot) {
                ((Bot) player).initBot(this);
            }
        }

        isInitialized = true;
    }

    public Board getBoard() {
        return board;
    }

    public void clear() {
        isInitialized = false;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                view.set(i, j, Cell.EMPTY);
            }
        }
    }

    public boolean turn(final int x, final int y) {
        if (!isInitialized) {
            throw new NotInitializedYetException();
        }
        final Cell cell = turn == Turn.X ? Cell.X : Cell.O;
        if (board.set(x, y, cell)) {
            view.set(x, y, cell);
            nextPlayer();
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

    private class NotInitializedYetException extends RuntimeException {
    }
}
