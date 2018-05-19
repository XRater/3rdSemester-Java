import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class GameWindow extends JFrame {

    GameWindow(final int size) {
        super("Pairs game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        final JPanel mainPanel = new JPanel();
        final JPanel boardPanel = createField(size);

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(boardPanel);

        setContentPane(mainPanel);
        setSize(700, 700);
    }

    @NotNull
    private JPanel createField(final int size) {
        final JPanel boardPanel = new JPanel();
        final Board board = new Board(size);
        boardPanel.setLayout(new GridLayout(size, size, 10, 10));
        boardPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton button = new JButton();
                boardPanel.add(button);
                board.setButton(button, i, j);
                final int x = i;
                final int y = j;
                button.addActionListener(e -> board.buttonPressed(x, y));
            }
        }
        board.init();
        return boardPanel;
    }

}
