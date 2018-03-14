package game.model.players;

public interface Player {

    default boolean isBot() {
        return false;
    }

    void getTurn();
}
