package game.gameTypes;

import game.model.players.*;
import utils.CircleList;

import java.util.Arrays;

/**
 * Default class for single-player game type construction.
 *
 * Overrides constructor with number of fabric methods.
 */
public class SinglePlayerGameType extends AbstractGameType {

    private final static GameOptions SINGLE_PLAYER_EASY_FIRST =
            new SinglePlayerGameType(BotLevel.EASY, true);

    private final static GameOptions SINGLE_PLAYER_EASY_SECOND =
            new SinglePlayerGameType(BotLevel.EASY, false);

    private final static GameOptions SINGLE_PLAYER_HARD_FIRST =
            new SinglePlayerGameType(BotLevel.HARD, true);

    private final static GameOptions SINGLE_PLAYER_HARD_SECOND =
            new SinglePlayerGameType(BotLevel.HARD, false);

    /**
     * Fabric method to create single-player game.
     *
     * @param isEasy sets bot to easy if true, and to hard otherwise.
     * @param first describes, whether human player has first turn or second one.
     * @return new single-player game options described by the given parameters.
     */
    public static GameOptions singlePlayerGame(final boolean isEasy, final boolean first) {
        if (isEasy) {
            return first ? SINGLE_PLAYER_EASY_FIRST : SINGLE_PLAYER_EASY_SECOND;
        }
        return first ? SINGLE_PLAYER_HARD_FIRST : SINGLE_PLAYER_HARD_SECOND;
    }

    private SinglePlayerGameType(final BotLevel level, final boolean firstTurn) {
        super(getList(level, firstTurn));
    }

    /**
     * The method returns bot from the target level.
     *
     * @param level target bot level.
     */
    private static Bot getBot(final BotLevel level) {
        switch (level) {
            case HARD:
                return new HardBot();
            default:
                return new EasyBot();
        }
    }

    /**
     * The method to create circle list of players with given options.
     *
     * @param level target bot level.
     * @param firstTurn set true, if player has first turn and false otherwise.
     * @return new list
     */
    private static CircleList<Player> getList(final BotLevel level, final boolean firstTurn) {
        final Bot bot = getBot(level);
        return firstTurn ?
                new CircleList<>(Arrays.asList(new HumanPlayer(), bot)) :
                new CircleList<>(Arrays.asList(bot, new HumanPlayer()));
    }

    private enum BotLevel {
        EASY, HARD
    }
}
