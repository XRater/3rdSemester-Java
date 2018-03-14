package controllers;

import game.GameConfig;
import game.model.Model;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import view.View;

import java.net.URL;
import java.util.ResourceBundle;

public class GameHandler implements Handler {

    @FXML public StackPane gameScreen;
    @FXML private GridPane table;

    private Model model;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setFieldListeners();
        model = new Model(initView());

        gameScreen.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onExit();
            }
        });
    }

    @Override
    public void onShow() {
        System.out.println("Start");
        model.initialize(GameConfig.getOptions());
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

    @FXML public void onExit() {
        model.clear();
        SceneManager.changeScene(SceneManager.SceneEnum.MENU);
    }
}
