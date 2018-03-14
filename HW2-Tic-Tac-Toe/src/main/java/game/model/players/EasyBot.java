package game.model.players;

import game.model.Board;
import game.model.Model;

public class EasyBot implements Bot {

    private Model model;
    private Board board;

    @Override
    public void initBot(final Model model) {
        this.model = model;
        board = model.getBoard();
    }

    @Override
    public void getTurn() {
        final int size = board.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isEmpty(i, j)) {
                    model.turn(i, j);
                    return;
                }
            }
        }
    }
}
