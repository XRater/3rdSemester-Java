/**
 *
 */
public class GameManager {

   private void initNewGame(final int size) {
        final GameWindow window = new GameWindow(size);
        window.setVisible(true);
    }

    public static void main (final String [] args) {
        final GameManager gm = new GameManager();
        if (args.length != 1) {
            System.out.println("Wrong arguments");
            return;
        }
        final int size = Integer.parseInt(args[0]);
        if (size % 2 != 0 || size > 10) {
            System.out.println("Invalid board size: " + size);
        }
        gm.initNewGame(size);
    }

}