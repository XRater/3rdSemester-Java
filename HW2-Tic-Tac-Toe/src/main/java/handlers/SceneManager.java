package handlers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * This class provides simple interface to change scenes in the application.
 *
 * As a small bonus of this class, every scene will be initialized not more then one time.
 */
public class SceneManager {

    public enum SceneEnum {
        GAME, MAIN_MENU, MODE_MENU, BOT_MENU, STATS

    }
    private static Stage stage;

    private static final LazyScene gameScene = new LazyScene("../game.fxml");

    private static final LazyScene mainMenuScene = new LazyScene("../mainMenu.fxml");
    private static final LazyScene modeMenuScene = new LazyScene("../modeMenu.fxml");
    private static final LazyScene botMenuScene = new LazyScene("../botMenu.fxml");
    private static final LazyScene statsScene = new LazyScene("../stats.fxml");
    /**
     * Initializes SceneManager with main stage.
     *
     * @param primaryStage stage to work with.
     */
    public static void initialize(final Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * The method changes a scene.
     *
     * @param sceneEnum describes the scene to call.
     */
    public static void changeScene(final SceneEnum sceneEnum) {
        Scene newScene = null;
        Handler newHandler = null;
        switch (sceneEnum) {
            case GAME:
                newScene = gameScene.getScene();
                newHandler = gameScene.getHandler();
                break;
            case MAIN_MENU:
                newScene = mainMenuScene.getScene();
                newHandler = mainMenuScene.getHandler();
                break;
            case MODE_MENU:
                newScene = modeMenuScene.getScene();
                newHandler = modeMenuScene.getHandler();
                break;
            case BOT_MENU:
                newScene = botMenuScene.getScene();
                newHandler = botMenuScene.getHandler();
                break;
            case STATS:
                newScene = statsScene.getScene();
                newHandler = statsScene.getHandler();
        }
        final double width = stage.getWidth();
        final double height = stage.getHeight();

        stage.setScene(newScene);
        newHandler.onShow();

        stage.setWidth(width);
        stage.setHeight(height);
    }

    @SuppressWarnings("WeakerAccess")
    public static Handler getScene(final SceneEnum sceneEnum) {
        switch (sceneEnum) {
            case GAME:
                return gameScene.getHandler();
            case MAIN_MENU:
                return mainMenuScene.getHandler();
            case MODE_MENU:
                return modeMenuScene.getHandler();
            case BOT_MENU:
                return botMenuScene.getHandler();
            case STATS:
                return statsScene.getHandler();
        }
        throw new NoSuchSceneException();
    }


    /**
     * This class provides lazy initialization of scenes.
     *
     * We do not have a lot of overhead without this class in that game,
     * but in general it might be a good pattern not to load every single scene
     * on the game start, and to load them when they are required instead.
     */
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
            } catch (@NotNull final IOException e) {
                throw new ErrorWhileLoadingScene(e);
            }
            handler = loader.getController();
            scene = new Scene(root, 300, 400);
        }
    }

    /**
     * This wrapper is required, because most of IOExceptions here are caused by
     * mistake in program, such as wrong files names and so on.
     *
     * Also, we might have no file cause user deleted it or something like that,
     * but right now I am not able to fix it (download files from server for example),
     * therefore it is more logical to throw a RuntimeException here.
     */
    private static class ErrorWhileLoadingScene extends RuntimeException {
        @SuppressWarnings("unused")
        ErrorWhileLoadingScene(final IOException e) {
        }
    }

    private static class NoSuchSceneException extends RuntimeException {
    }
}
