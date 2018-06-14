package com.mit.spbau.kirakosian.controller;

import com.mit.spbau.kirakosian.testing.TestResults;
import com.mit.spbau.kirakosian.ui.SceneManager;

import javax.swing.*;

public class Controller {

    public static void calculationsCompleted(TestResults results) {
        SceneManager.RESULTS_SCENE.acceptResults(results);
        SwingUtilities.invokeLater(() -> SceneManager.setScene(SceneManager.RESULTS_SCENE));
    }

    public static void cancel() {

    }
}
