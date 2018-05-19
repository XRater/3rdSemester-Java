package game.model.players;

public class HumanPlayer implements Player {

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public void getTurn() {
        // do nothing
    }
}
