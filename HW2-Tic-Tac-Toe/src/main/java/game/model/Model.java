package game.model;

import game.GameController;
import game.gameTypes.GameOptions;
import game.model.players.Bot;
import game.model.players.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class Model {

    private final GameController gameController;

    private final Board board = new Board();
    private final Iterator<Player> iterator;

    private Turn turn;
    private Player player;

    private boolean gameInProgress = true;

    public Model(final GameController gameController, final GameOptions gameOptions) {
        this.gameController = gameController;

        iterator = gameOptions.getPlayers().cycleIterator();
        player = iterator.next();
        turn = gameOptions.getFirstTurn();

        // Init bots
        Turn tmpTurn = gameOptions.getFirstTurn();
        for (final Player player : gameOptions.getPlayers()) {
            if (player instanceof Bot) {
                ((Bot) player).initBot(this, tmpTurn);
            }
            tmpTurn = tmpTurn == Turn.X ? Turn.O : Turn.X;
        }
    }

    @NotNull
    public Board getBoard() {
        return board;
    }

    public void turn(final int x, final int y) {
        if (!gameInProgress) {
            return;
        }
        final Cell cell = turn == Turn.X ? Cell.X : Cell.O;
        if (board.set(x, y, cell)) {
            gameController.set(x, y, cell);
            if (checkGameEnd()) {
                gameInProgress = false;
                return;
            }
            nextPlayer();
        }
    }

    private boolean checkGameEnd() {
        final GameState state = board.getState();
        if (state != GameState.IN_PROGRESS) {
            gameController.onGameEnd(state);
            return true;
        }
        return false;
    }

    private void nextPlayer() {
        player = iterator.next();
        turn = turn == Turn.X ? Turn.O : Turn.X;
        player.getTurn();
    }

    public enum Turn {
        X, O
    }

}
