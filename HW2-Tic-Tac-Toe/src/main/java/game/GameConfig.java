package game;

import game.gameTypes.MultiPlayerGameType;
import game.gameTypes.SinglePlayerGameType;

@SuppressWarnings("WeakerAccess")
public class GameConfig {

    private static final GameOptions MULTI_PLAYER_GAME_TYPE = new MultiPlayerGameType();
    private static final GameOptions SINGLE_PLAYER_EASY_GAME
            = new SinglePlayerGameType(SinglePlayerGameType.BotLevel.EASY);
    private static final GameOptions SINGLE_PLAYER_HARD_GAME
            = new SinglePlayerGameType(SinglePlayerGameType.BotLevel.HARD);

    private static GameOptions options = MULTI_PLAYER_GAME_TYPE;

    public static GameOptions getOptions() {
        return options;
    }

    public static void setOptions(final GameOptions options) {
        GameConfig.options = options;
    }

    public static void setMultiPlayerGame() {
        setOptions(MULTI_PLAYER_GAME_TYPE);
    }

    public static void setSinglePlayerEasyGame() {
        setOptions(SINGLE_PLAYER_EASY_GAME);
    }

    public static void setSinglePlayerHardGame() {
        setOptions(SINGLE_PLAYER_HARD_GAME);
    }

}
