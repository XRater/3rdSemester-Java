package game;

import game.gameTypes.GameOptions;
import game.model.Cell;
import game.model.GameState;
import game.model.Model;
import org.jetbrains.annotations.NotNull;
import view.View;

public class GameController {

    private final View view;

    private Statistics statistics;
    private Model model;

    private boolean isActiveSession;
    private boolean isGameInProgress;

    public GameController(final View view) {
        this.view = view;
    }

    public void startNewSession(@NotNull final GameOptions options) {
        statistics = new Statistics();
        isActiveSession = true;
        startNewGame(options);
    }

    public void startNewGame(@NotNull final GameOptions options) {
        model = new Model(this, options);
        view.clear();
        isGameInProgress = true;
    }

    public void turn(final int x, final int y) {
        model.turn(x, y);
    }

    public void set(final int x, final int y, final Cell cell) {
        view.set(x, y, cell);
    }

    public void onGameEnd(@NotNull final GameState state) {
        switch (state) {
            case IN_PROGRESS:
                throw new RuntimeException(); // should never happen
            case DRAW:
                statistics.onDraw();
                break;
            case X_WIN:
                statistics.onXWin();
                break;
            case O_WIN:
                statistics.onOWin();
                break;
        }
        isGameInProgress = false;
    }

    public boolean isGameInProgress() {
        return isGameInProgress;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public boolean isActiveSession() {
        return isActiveSession;
    }

    public void closeSession() {
        isActiveSession = false;
        isGameInProgress = false;
    }
}
