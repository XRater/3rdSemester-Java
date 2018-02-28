package tasks;

@SuppressWarnings("WeakerAccess")
public abstract class Task implements Runnable {

    protected static int counter;

    Task() {
        counter++;
    }

}
