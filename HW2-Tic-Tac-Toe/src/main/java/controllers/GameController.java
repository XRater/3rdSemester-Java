package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Model;
import view.View;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML private GridPane table;

    private Model model;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setFieldListeners();
        model = new Model(initView());
    }

    private View initView() {
        final StackPane[][] grid = new StackPane[3][3];
        for (final Node node : table.getChildren()) {
            if (node instanceof StackPane) {
                final StackPane stackPane = (StackPane) node;
                final int x = GridPane.getColumnIndex(stackPane);
                final int y = GridPane.getRowIndex(stackPane);
                grid[x][y] = stackPane;
            }
        }
        return new View(grid);
    }

    private void setFieldListeners() {
        for (final Node node : table.getChildren()) {
            if (node instanceof StackPane) {
                final StackPane stackPane = (StackPane) node;
                final int x = GridPane.getColumnIndex(stackPane);
                final int y = GridPane.getRowIndex(stackPane);
                stackPane.setOnMouseReleased(event -> model.turn(x, y));
            }
        }
    }

    @FXML public void click(MouseEvent mouseEvent) {
        System.out.println("Game");
        Controller.changeScene(Controller.SceneEnum.MENU);
    }

}
