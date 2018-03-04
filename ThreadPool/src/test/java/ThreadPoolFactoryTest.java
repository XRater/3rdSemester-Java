import org.junit.jupiter.api.Test;
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

    private final int testsNumber = 10;
    private final int maxThreadsNumber = 10;

    @Test
    void testALotOfThreads() {
        for (int i = 0; i < maxThreadsNumber; i++) {
            testNThreads(i * i / 2);
        }
    }

    private void testNThreads(final int threadsNumber) {
        final ThreadPool pool = ThreadPoolFactory.initThreadPool(threadsNumber);

        final List<LightFuture<Void>> list = new LinkedList<>();

        for (int i = 0; i < threadsNumber - 1; i++) {
            list.add(pool.addTask(new WaitSupplier()));
        }
        pool.addTask(new SleepSupplier());
        pool.addTask(new NotifySupplier());
        pool.shutdown();

        Utils.sleep(1000);

        for (final LightFuture<Void> lightFuture : list) {
            assertTrue(lightFuture.isReady());
        }
    }

}