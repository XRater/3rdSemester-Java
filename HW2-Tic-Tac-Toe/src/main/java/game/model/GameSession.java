package game.model;

import game.gameTypes.GameOptions;
import view.View;

public class GameSession {

    private final View view;

    private Model model;
    private Statistics statistics;

    public GameSession(final View view) {
        this.view = view;
    }

    public void initGame(final GameOptions options) {
        model = new Model(this, options);
        statistics = new Statistics();
        view.clear();
    }

    public void turn(final int x, final int y) {
        model.turn(x, y);
    }

    void set(final int x, final int y, final Cell cell) {
        view.set(x, y, cell);
    }

    void onGameEnd(final GameState state) {
        switch (state) {
            case IN_PROGRESS:
                throw new RuntimeException(); // should never happen
            case DRAW:
                statistics.onDraw();
            case X_WIN:
                statistics.onXWin();
            case O_WIN:
                statistics.onYWin();
        }
    }

    private Statistics getStatistics() {
        return statistics;
    }
}
