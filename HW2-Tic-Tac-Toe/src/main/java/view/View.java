package view;

import game.model.Cell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class View {

    private final static Image X = new Image("X.png");
    private final static Image O = new Image("O.png");

    private final StackPane[][] grid;

    public View(final StackPane[][] grid) {
        this.grid = grid;
    }

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
}
