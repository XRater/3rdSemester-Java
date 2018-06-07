package com.mit.spbau.kirakosian.controller;

import com.mit.spbau.kirakosian.ui.SceneManager;
import com.mit.spbau.kirakosian.ui.Window;

import javax.swing.*;

public class Controller {

    public static void calculationsCompleted() {
        SwingUtilities.invokeLater(() -> SceneManager.setScene(SceneManager.RESULTS_SCENE));
    }

}
