package game.gameTypes;

import game.model.players.Player;
import utils.CycleCollection;

/**
 * This class provides simple constructor for game type.
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractGameType implements GameOptions {

    protected final CycleCollection<Player> players;

    protected AbstractGameType(final CycleCollection<Player> players) {
        this.players = players;
    }

    @Override
    public final CycleCollection<Player> getPlayers() {
        return players;
    }

}
