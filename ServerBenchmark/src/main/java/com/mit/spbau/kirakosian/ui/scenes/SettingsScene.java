package com.mit.spbau.kirakosian.ui.scenes;

import com.mit.spbau.kirakosian.testing.ServerTestInitializer;
import com.mit.spbau.kirakosian.options.metrics.MetricMeta;
import com.mit.spbau.kirakosian.options.parameters.ParameterOptionMeta;
import com.mit.spbau.kirakosian.options.UIOptions;
import com.mit.spbau.kirakosian.options.TestOptions;
import com.mit.spbau.kirakosian.servers.Servers;
import com.mit.spbau.kirakosian.ui.Scene;
import com.mit.spbau.kirakosian.ui.SceneManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class SettingsScene extends Scene {

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JComboBox<Servers.ServerType> comboBox;
    private final java.util.List<OptionPanel> optionPanels = new ArrayList<>();

    public SettingsScene() {
        super();
        final JButton startButton = new JButton("Start server startTest");
        startButton.addActionListener(e ->
        {
            final TestOptions testOptions;
            try {
                testOptions = collectOptions();
            } catch (final IllegalOptionsException e1) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid options were set. Only integer values are allowed", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
//            String error = testOptions.validate();
            String error = null;
            if (error != null) {
                JOptionPane.showMessageDialog(null,
                        "Error: " + error, "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            SceneManager.setScene(SceneManager.TESTING_SCENE);
            new Thread(() -> ServerTestInitializer.startTest(testOptions)).start();
        });
        final JPanel settingsPanel = createSettingsPanel();

        add(settingsPanel);
        add(startButton, BorderLayout.SOUTH);
    }

    private TestOptions collectOptions() throws IllegalOptionsException {
        final TestOptions options = new TestOptions();
        for (final Class<? extends MetricMeta> meta : UIOptions.metrics()) {
            options.addMetric(meta);
        }
        options.setServerType((Servers.ServerType) comboBox.getSelectedItem());
        for (final OptionPanel panel : optionPanels) {
            panel.collectOptions(options);
        }
        return options;
    }

    private JPanel createSettingsPanel() {
        final JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        final JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        optionsPanel.add(Box.createVerticalGlue());
        for (final ParameterOptionMeta option : UIOptions.options()) {
            final OptionPanel panel = new OptionPanel(option);
            optionPanels.add(panel);
            optionsPanel.add(panel);
            optionsPanel.add(Box.createVerticalGlue());
        }

        settingsPanel.add(createServerTypeOption(), BorderLayout.NORTH);
        settingsPanel.add(optionsPanel);
        return settingsPanel;
    }

    private Component createServerTypeOption() {
        final JComboBox<Servers.ServerType> comboBox = new JComboBox<>();
        for (final Servers.ServerType option : UIOptions.serverOptions()) {
            comboBox.addItem(option);
        }
        ((JLabel)comboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        comboBox.setToolTipText("Server type");
        this.comboBox = comboBox;
        return comboBox;
    }

    private class OptionPanel extends JPanel {

        private final ParameterOptionMeta option; // option to set up
        private final JSlider slider; // slider to choose option value
        private final JPanel alterPanel; // panel with settings for altering option

        private boolean altering; // shows whether slider or panel is showed
        private final JTextField lowerBound = new JTextField("0");
        private final JTextField upperBound = new JTextField("0");
        private final JTextField delta = new JTextField("0");

        private OptionPanel(final ParameterOptionMeta option) {
            this.option = option;
            setLayout(new GridLayout(2, 1));

            slider = new JSlider(option.minValue(), option.maxValue());
            setUpSlider();

            final JLabel label = new JLabel(option.name());
            label.setOpaque(true);
            label.setHorizontalTextPosition(JLabel.CENTER);
            add(label);

            alterPanel = createAlterPanel();

            add(createInnerPanel());
        }

        private JPanel createAlterPanel() {
            final JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(Box.createHorizontalGlue());
            panel.add(createAlterOption("Min", lowerBound));
            panel.add(Box.createHorizontalGlue());
            panel.add(createAlterOption("Max", upperBound));
            panel.add(Box.createHorizontalGlue());
            panel.add(createAlterOption("Delta", delta));
            panel.add(Box.createHorizontalGlue());
            return panel;
        }

        private Component createAlterOption(final String name, final  JTextField textField) {
            final JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            final JLabel label = new JLabel(name);
            panel.add(label);
            panel.add(Box.createHorizontalStrut(10));
            panel.add(textField);
            return panel;
        }

        /**
         * Creates new inner panel
         *
         * @return created inner panel
         */
        private Component createInnerPanel() {
            final JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(slider);

            if (option.mayAlter()) {
                final JRadioButton button = new JRadioButton();
                panel.add(button, BorderLayout.EAST);
                buttonGroup.add(button);
                button.addItemListener(e -> {
                    switch (e.getStateChange()) {
                        case ItemEvent.SELECTED: {
                            panel.remove(slider);
                            panel.add(alterPanel);
                            panel.revalidate();
                            altering = true;
                            break;
                        }
                        case ItemEvent.DESELECTED: {
                            panel.remove(alterPanel);
                            panel.add(slider);
                            panel.revalidate();
                            altering = false;
                            break;
                        }
                    }
                });
            }
            return panel;
        }

        private void setUpSlider() {
            final int length = option.maxValue() - option.minValue();
            slider.setMajorTickSpacing(getTickSpace(length, 4));
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setMinorTickSpacing(getTickSpace(length, 50));
            slider.setSnapToTicks(true);
            slider.setToolTipText(option.description());
        }

        /**
         * The method evaluates optimal tick space for target number of slices.
         *
         * @param length length of the gap
         * @param number number of slices
         * @return optimal tick space
         */
        private int getTickSpace(final int length, final int number) {
            return length < number ? 1 : length / number;
        }

        @SuppressWarnings("WeakerAccess")
        public void collectOptions(final TestOptions options) throws IllegalOptionsException {
            if (altering) {
                options.setAlteringOptionMeta(option.getClass());
                try {
                    options.setLowerBound(Integer.parseInt(lowerBound.getText()));
                    options.setUpperBound(Integer.parseInt(upperBound.getText()));
                    options.setDelta(Integer.parseInt(delta.getText()));
                } catch (final NumberFormatException e) {
                    throw new IllegalOptionsException(e);
                }
                options.setOption(option.getClass(), null);
            } else {
                options.setOption(option.getClass(), slider.getValue());
            }
        }
    }


    private class IllegalOptionsException extends Throwable {
        IllegalOptionsException(final NumberFormatException e) {
            super(e);
        }
    }
}
