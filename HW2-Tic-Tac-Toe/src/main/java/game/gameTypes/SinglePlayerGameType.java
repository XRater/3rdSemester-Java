package game.gameTypes;

import game.model.players.Bot;
import game.model.players.EasyBot;
import game.model.players.HardBot;
import game.model.players.HumanPlayer;
import utils.CircleList;

import java.util.Arrays;

public class SinglePlayerGameType extends AbstractGameType {

    private static Bot getBot(final BotLevel level) {
        switch (level) {
            case EASY:
                return new EasyBot();
            case HARD:
                return new HardBot();
        }
        return new EasyBot();
    }

    public SinglePlayerGameType(final BotLevel level) {
        super(new CircleList<>(Arrays.asList(new HumanPlayer(), getBot(level))));
    }

    public enum BotLevel {
        EASY, HARD
    }
}
