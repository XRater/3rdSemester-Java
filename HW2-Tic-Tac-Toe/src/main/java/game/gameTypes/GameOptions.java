package game.gameTypes;

import game.model.Model;
import game.model.players.Player;
import utils.CircleList;

/**
 * Interface for game options. In case of tic-tac-toe returns list of players and sets
 * first turn to X by default.
 */
public interface GameOptions {

    /**
     * The methods returns cycle list of all players in the game.
     */
    CircleList<Player> getPlayers();

    /**
     * Returns first-turn symbol.
     */
    default Model.Turn getFirstTurn() {
        return Model.Turn.X;
    }
}
