import org.junit.jupiter.api.Test;
import suppliers.CounterSupplier;
import suppliers.NotifySupplier;
import suppliers.SleepSupplier;
import suppliers.WaitSupplier;
import threadPool.LightFuture;
import threadPool.ThreadPool;
import threadPool.ThreadPoolFactory;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("FieldCanBeLocal")
class ThreadPoolFactoryTest {

    private final int testsNumber = 100;
    private final int maxThreadsNumber = 10;

    /**
     * This test checks if every task was executed exactly one time.
     */
    @Test
    void testSimple() {
        for (int t = 0; t < testsNumber; t++) {
            final ThreadPool pool = ThreadPoolFactory.initThreadPool(3);
            final List<CounterSupplier> list = new LinkedList<>();

            for (int i = 0; i < 10; i++) {
                final CounterSupplier dummy = new CounterSupplier();
                list.add(dummy);
                pool.addTask(dummy);
            }

            pool.waitWithShutDown();

            assertEquals((t + 1) * 10, list.get(0).getCounter());
            for (final CounterSupplier counterSupplier : list) {
                assertEquals(1, counterSupplier.getMyCounter());
            }
        }
    }

    /**
     * This test checks if there at least n threads int the pool.
     *
     * Initialises n - 1 thread with waiting tasks and notifying them with the last one.
     * If there are not enough threads in the pool, test will stuck, therefore there is
     * a timeout for test execution.
     */
    @Test
    void testALotOfThreads() {
        for (int i = 0; i < maxThreadsNumber; i++) {
            testNThreads(i * i / 2);
        }
    }

    private void testNThreads(final int threadsNumber) {

        final Thread timer = new Thread(() -> {
            Utils.sleep(5000);
            synchronized (this) {
                notify();
            }
        });
        timer.start();

        final ThreadPool pool = ThreadPoolFactory.initThreadPool(threadsNumber);
        final List<LightFuture<Void>> list = new LinkedList<>();

        for (int i = 0; i < threadsNumber - 1; i++) {
            list.add(pool.addTask(new WaitSupplier()));
        }
        pool.addTask(new SleepSupplier());
        pool.addTask(new NotifySupplier());

        new Thread(() -> {
            pool.waitWithShutDown();
            synchronized (this) {
                notify();
            }
        }).start();

        synchronized (this) {
            try {
                // there must be loop, but i cannot change variable
                // for it in lambda... What should I do with it?
                // Here I will hope that at least one test will fail.
                wait();
            } catch (final InterruptedException e) {
                // do nothing
            }
            // Am i right that timer cannot still work?
            // In case thread was set on this one immediately?
            if (!timer.isAlive()) {
                fail("Out of time.");
            }
        }

        for (final LightFuture<Void> lightFuture : list) {
            assertTrue(lightFuture.isReady());
        }
    }

}