package view;

import game.model.Cell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * View class to control game board.
 *
 * The main idea of separating this class from GameHandler class is to separate
 * class, which listens to actions (Handler) from class that actually draws (View).
 *
 * Unfortunately, in that case I have to pass all mutable objects to this class.
 */
public class View {

    private final static Image X = new Image("X.png");
    private final static Image O = new Image("O.png");

    private final StackPane[][] grid;

    /**
     * Creates view from the grid. Created view class will be able t controll inner images
     * of grid.
     */
    public View(final StackPane[][] grid) {
        this.grid = grid;
    }

    /**
     * The method sets image of the cell to the target one.
     *
     * @param x x-coordinate.
     * @param y y-coordinate.
     * @param cell cell state to put into target cell.
     */
    public void set(final int x, final int y, final Cell cell) {
        final ImageView imageView = (ImageView) grid[x][y].getChildren().iterator().next();

        switch (cell) {
            case X:
                imageView.setImage(X);
                break;
            case O:
                imageView.setImage(O);
                break;
            default:
                imageView.setImage(null);
        }
    }

    /**
     * Clears the table.
     */
    public void clear() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                set(i, j, Cell.EMPTY);
            }
        }
    }
}
