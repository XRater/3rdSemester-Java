package game.gameTypes;

import game.model.players.HumanPlayer;
import utils.CircleList;

import java.util.Arrays;

public class MultiPlayerGameType extends AbstractGameType {

    public MultiPlayerGameType() {
        super(new CircleList<>(Arrays.asList(new HumanPlayer(), new HumanPlayer())));
    }
}
