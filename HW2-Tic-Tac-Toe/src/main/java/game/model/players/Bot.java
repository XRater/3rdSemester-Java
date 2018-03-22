package game.model.players;

import game.model.Model;

public interface Bot extends Player {

    @Override
    default boolean isBot() {
        return true;
    }

    void initBot(Model model, final Model.Turn turn);

    class NoTurnException extends RuntimeException {
    }
}
