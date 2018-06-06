package com.mit.spbau.kirakosian.ui;

import com.mit.spbau.kirakosian.ServerTest;
import com.mit.spbau.kirakosian.options.ParameterOptionMeta;
import com.mit.spbau.kirakosian.options.GeneralOptions;
import com.mit.spbau.kirakosian.servers.Servers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsWindow extends Window {

    private final Map<ParameterOptionMeta, Integer> options = new HashMap<>();
    private Servers.ServerType serverType;

    public SettingsWindow() {
        super();
        final JButton startButton = new JButton("Start server test");
        startButton.addActionListener(e -> ServerTest.test(serverType, options));
        final JPanel settingsPanel = createSettingsPanel();

        mainPanel.add(settingsPanel);
        mainPanel.add(startButton, BorderLayout.SOUTH);
    }

    private JPanel createSettingsPanel() {
        final JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        final JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        optionsPanel.add(Box.createVerticalGlue());
        for (final ParameterOptionMeta option : GeneralOptions.options()) {
            optionsPanel.add(createOption(option));
            optionsPanel.add(Box.createVerticalGlue());
        }

        settingsPanel.add(createServerTypeOption(), BorderLayout.NORTH);
        settingsPanel.add(optionsPanel);
        return settingsPanel;
    }

    private Component createServerTypeOption() {
        final JComboBox<Servers.ServerType> comboBox = new JComboBox<>();
        for (final Servers.ServerType option : GeneralOptions.serverOptions()) {
            comboBox.addItem(option);
        }
        ((JLabel)comboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        comboBox.setToolTipText("Server type");
        comboBox.addActionListener(
                e -> serverType = (Servers.ServerType) comboBox.getSelectedItem());
        serverType = (Servers.ServerType) comboBox.getSelectedItem();
        return comboBox;
    }

    private Component createOption(final ParameterOptionMeta option) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        final int length = option.maxValue() - option.minValue();

        final JSlider slider = new JSlider(option.minValue(), option.maxValue());
        slider.setMajorTickSpacing(getTickSpace(length, 4));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMinorTickSpacing(getTickSpace(length, 50));
        slider.setSnapToTicks(true);
        slider.setToolTipText(option.description());

        options.put(option, slider.getValue());
        slider.addChangeListener(e -> options.put(option, slider.getValue()));

        final JLabel label = new JLabel(option.name());
        label.setOpaque(true);
        label.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(label);
        panel.add(slider);
        return panel;
    }

    private int getTickSpace(final int length, final int number) {
        return length < number ? 1 : length / number;
    }
}
