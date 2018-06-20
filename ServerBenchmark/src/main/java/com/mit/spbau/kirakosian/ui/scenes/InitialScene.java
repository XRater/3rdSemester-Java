package com.mit.spbau.kirakosian.ui.scenes;

import com.mit.spbau.kirakosian.ui.Scene;
import com.mit.spbau.kirakosian.ui.SceneManager;

import javax.swing.*;

public class InitialScene extends Scene {

    public InitialScene() {
        super();
        final JButton button = new JButton();
        button.addActionListener(e -> SceneManager.setScene(SceneManager.SETTINGS_SCENE));
        add(button);
    }
}
