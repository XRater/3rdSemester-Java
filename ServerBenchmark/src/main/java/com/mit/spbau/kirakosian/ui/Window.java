package com.mit.spbau.kirakosian.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class Window extends JFrame {

    @SuppressWarnings("WeakerAccess")
    protected JPanel mainPanel = new JPanel();

    protected Window() {
        super("Server Benchmark");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new Dimension(500, 600));

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);
    }
}
