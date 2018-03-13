package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Supplier;

public class Controller {

    public enum SceneEnum {
        GAME, MENU;
    }

    private static Stage stage;

    private static final LazyScene gameScene = new LazyScene("../game.fxml");
    private static final LazyScene menuScene = new LazyScene("../menu.fxml");

    public static void initialize(final Stage primaryStage) {
        stage = primaryStage;
    }

    public static void changeScene(final SceneEnum sceneEnum) {
        Scene newScene = null;
        switch (sceneEnum) {
            case GAME:
                newScene = gameScene.get();
                break;
            case MENU:
                newScene = menuScene.get();
                break;
        }
        final double width = stage.getWidth();
        final double height = stage.getHeight();
        stage.setScene(newScene);
        stage.setWidth(width);
        stage.setHeight(height);
    }

    private static class LazyScene implements Supplier<Scene> {

        private Scene scene;
        private final String name;

        LazyScene(final String name) {
            this.name = name;
        }

        @Override
        public Scene get() {
            if (scene == null) {
                final Parent root;
                try {
                    root = FXMLLoader.load(Controller.class.getResource(name));
                } catch (final IOException e) {
                    throw new ErrorWhileLoadingScene(e);
                }
                scene = new Scene(root, 300, 400);
            }
            return scene;
        }
    }

    private static class ErrorWhileLoadingScene extends RuntimeException {
        ErrorWhileLoadingScene(final IOException e) {
        }
    }
}
