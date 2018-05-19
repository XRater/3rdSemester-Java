package game.model.players;

public interface Player {

    @SuppressWarnings("unused")
    default boolean isBot() {
        return false;
    }

    void getTurn();
}
