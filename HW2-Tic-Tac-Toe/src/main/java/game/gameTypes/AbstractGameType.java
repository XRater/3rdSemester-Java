package game.gameTypes;

import game.GameOptions;
import game.model.players.Player;
import utils.CircleList;

public abstract class AbstractGameType implements GameOptions {

    protected final CircleList<Player> players;

    protected AbstractGameType(final CircleList<Player> players) {
        this.players = players;
    }

    @Override
    public final CircleList<Player> getPlayers() {
        return players;
    }

}
