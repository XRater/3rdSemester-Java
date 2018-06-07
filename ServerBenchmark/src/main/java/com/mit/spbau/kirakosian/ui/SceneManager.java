package com.mit.spbau.kirakosian.ui;

import com.mit.spbau.kirakosian.ui.scenes.InitialScene;
import com.mit.spbau.kirakosian.ui.scenes.ResultsScene;
import com.mit.spbau.kirakosian.ui.scenes.SettingsScene;
import com.mit.spbau.kirakosian.ui.scenes.TestingScene;

public class SceneManager {

    private static Window window;
    public final static Scene INITIAL_SCENE = new InitialScene();
    public final static Scene SETTINGS_SCENE = new SettingsScene();
    public final static Scene TESTING_SCENE = new TestingScene();
    public static final Scene RESULTS_SCENE = new ResultsScene();

    public static void init(final Window w) {
        window = w;
    }

    public static void setScene(final Scene scene) {
        window.setScene(scene);
    }
}
