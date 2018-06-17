package com.mit.spbau.kirakosian.ui;

import com.mit.spbau.kirakosian.ui.scenes.InitialScene;
import com.mit.spbau.kirakosian.ui.scenes.ResultsScene;
import com.mit.spbau.kirakosian.ui.scenes.SettingsScene;
import com.mit.spbau.kirakosian.ui.scenes.TestingScene;

public class SceneManager {

    private static Window window;
    public final static InitialScene INITIAL_SCENE = new InitialScene();
    public final static SettingsScene SETTINGS_SCENE = new SettingsScene();
    public final static TestingScene TESTING_SCENE = new TestingScene();
    public static final ResultsScene RESULTS_SCENE = new ResultsScene();

    public static void init(final Window w) {
        window = w;
    }

    public static void setScene(final Scene scene) {
        window.setScene(scene);
    }
}
