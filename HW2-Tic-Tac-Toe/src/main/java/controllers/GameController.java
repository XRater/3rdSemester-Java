package controllers;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Model;

public class GameController {

    public GridPane table;
    private Model model;

    public void initialize() {
        setFieldListeners();

        model = new Model();
    }

    private void setFieldListeners() {
        for (final Node node : table.getChildren()) {
            if (node instanceof StackPane) {
                final StackPane stackPane = (StackPane) node;
                stackPane.setOnMouseReleased(event -> {
                    final int x = GridPane.getColumnIndex(stackPane);
                    final int y = GridPane.getRowIndex(stackPane);
                    model.turn(x, y);
                });
            }
        }
    }

    public void click(final MouseEvent mouseEvent) {
        System.out.println("Game");
        Controller.changeScene(Controller.SceneEnum.MENU);
    }
}
