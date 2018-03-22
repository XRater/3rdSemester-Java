package game.gameTypes;

import game.model.players.HumanPlayer;
import utils.CycleCollection;

import java.util.Arrays;

/**
 * Default class for multi-player game type. Overrides constructor with fabric method.
 */
public class MultiPlayerGameType extends AbstractGameType {

    private final static GameOptions MULTI_PLAYER_GAME_TYPE = new MultiPlayerGameType();

    /**
     * The method returns game options to describe simple Multi-player game.
     */
    public static GameOptions multiPlayerGame() {
        return MULTI_PLAYER_GAME_TYPE;
    }

    private MultiPlayerGameType() {
        super(new CycleCollection<>(Arrays.asList(new HumanPlayer(), new HumanPlayer())));
    }
}
