package game.model.players;

import game.model.Board;
import game.model.Model;

public class HardBot implements Bot {

    private Model model;
    private Board board;

    @Override
    public void initBot(final Model model) {
        this.model = model;
        board = model.getBoard();
    }

    @Override
    public void getTurn() {
        model.turn(0, 0);
    }
}
