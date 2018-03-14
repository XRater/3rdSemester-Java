package game;

import game.model.Model;
import game.model.players.Player;
import utils.CircleList;

public interface GameOptions {

    CircleList<Player> getPlayers();

    default Model.Turn getFirstTurn() {
        return Model.Turn.X;
    }
}
