package tasks;

public class SleepTask extends Task {

    private int number = counter;

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Task " + number + " was started");
                Thread.sleep(10000);
                System.out.println("Task " + number + " was finished");
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }
}
