import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Board class. All logic is stored here.
 */
class Board {

    private final int n;
    @NotNull
    private final Cell[][] field;

    @Nullable
    private Cell cell;

    Board(final int n) {
        if (n % 2 != 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        field = new Cell[n][n];
    }

    void setButton(final JButton button, final int i, final int j) {
        field[i][j] = new Cell(button);
    }

    void init() {
        final int mx = n * n / 2;
        final ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= mx; i++) {
            numbers.add(i);
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                field[i][j].setValue(numbers.get(i * n + j));
            }
        }
    }

    void buttonPressed(final int x, final int y) {
        final Cell newCell = field[x][y];
        if (newCell.showed) {
            return;
        }
        newCell.show();
        if (cell == null) {
            cell = newCell;
        } else {
            if (cell.value == newCell.value) {
                cell.open();
                newCell.open();
            } else {
                cell.hide();
                newCell.hide();
            }
            cell = null;
        }
    }

    @SuppressWarnings("WeakerAccess")
    private static class Cell {

        private final JButton button;

        private int value;
        @SuppressWarnings("unused")
        private boolean open;
        private boolean showed;

        private Cell(final JButton button) {
            this.button = button;
        }

        private void setValue(final int x) {
            value = x;
        }

        public void open() {
            open = true;
            show();
        }

        public void show() {
            showed = true;
            button.setText(String.valueOf(value));
        }

        public void hide() {
            showed = false;
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(200);
                } catch (@NotNull final InterruptedException e) {
                    // do nothing
                }
                button.setText(String.valueOf(""));
            });
        }

    }
}
