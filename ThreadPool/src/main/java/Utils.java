public class Utils {

    public static void sleep(final int time) {
        try {
            Thread.sleep(time);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

}
