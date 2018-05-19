package game;

import game.gameTypes.GameOptions;
import game.gameTypes.MultiPlayerGameType;
import game.gameTypes.SinglePlayerGameType;

/**
 * In this class options for the new game a stored. Also, this class helps to
 * create different game types such as multi-player/single-player game.
 */
@SuppressWarnings("WeakerAccess")
public class GameConfig {

    private static GameOptions options = MultiPlayerGameType.multiPlayerGame();

    /**
     * The method returns current game options.
     */
    public static GameOptions getOptions() {
        return options;
    }

    public static void setOptions(final GameOptions options) {
        GameConfig.options = options;
    }

    /**
     * Options for default multi-player game.
     */
    public static void setMultiPlayerGame() {
        setOptions(MultiPlayerGameType.multiPlayerGame());
    }

    /**
     * Options for default single-player game with easy bot. Player will have the first turn.
     */
    public static void setSinglePlayerEasyGame() {
        setOptions(SinglePlayerGameType.singlePlayerGame(true, true));
    }

    /**
     * Options for default single-player game with hard bot. Player will have the first turn.
     */
    public static void setSinglePlayerHardGame() {
        setOptions(SinglePlayerGameType.singlePlayerGame(false, true));
    }

}
