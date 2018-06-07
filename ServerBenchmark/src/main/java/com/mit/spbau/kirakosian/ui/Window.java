package com.mit.spbau.kirakosian.ui;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    @SuppressWarnings("WeakerAccess")
    @NotNull private Scene currentScene;

    public Window() {
        super("Server Benchmark");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new Dimension(500, 600));

        currentScene = SceneManager.SETTINGS_SCENE;
        currentScene.setVisible(true);
        setContentPane(currentScene);
    }


    public void setScene(final Scene scene) {
        currentScene.setVisible(false);
        scene.setVisible(true);
        scene.onShow();
        setContentPane(scene);
        revalidate();
        currentScene = scene;
    }
}
