package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    public enum SceneEnum {
        GAME, MENU
    }

    private static Stage stage;

    private static final LazyScene gameScene = new LazyScene("../game.fxml");
    private static final LazyScene menuScene = new LazyScene("../menu.fxml");

    public static void initialize(final Stage primaryStage) {
        stage = primaryStage;
    }

    public static void changeScene(final SceneEnum sceneEnum) {
        Scene newScene = null;
        Handler newHandler = null;
        switch (sceneEnum) {
            case GAME:
                newScene = gameScene.getScene();
                newHandler = gameScene.getHandler();
                break;
            case MENU:
                newScene = menuScene.getScene();
                newHandler = menuScene.getHandler();
                break;
        }
        final double width = stage.getWidth();
        final double height = stage.getHeight();

        stage.setScene(newScene);
        newHandler.onShow();

        stage.setWidth(width);
        stage.setHeight(height);
    }

    @SuppressWarnings("WeakerAccess")
    private static class LazyScene {

        private Scene scene;
        private Handler handler;
        private final String name;

        public LazyScene(final String name) {
            this.name = name;
        }

        public Scene getScene() {
            if (scene == null) {
                init();
            }
            return scene;
        }

        public Handler getHandler() {
            if (handler == null) {
                init();
            }
            return handler;
        }

        private void init() {
            final FXMLLoader loader = new FXMLLoader();
            final Parent root;
            try {
                root = loader.load(SceneManager.class.getResourceAsStream(name));
            } catch (final IOException e) {
                throw new ErrorWhileLoadingScene(e);
            }
            handler = loader.getController();
            scene = new Scene(root, 300, 400);
        }
    }

    private static class ErrorWhileLoadingScene extends RuntimeException {
        @SuppressWarnings("unused")
        ErrorWhileLoadingScene(final IOException e) {
        }
    }
}
