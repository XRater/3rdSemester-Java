package com.mit.spbau.kirakosian.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class Scene extends JPanel {

    protected Scene() {
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout());

        setVisible(false);
    }

    protected void onShow() {
    }
}
