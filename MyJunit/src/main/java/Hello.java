import annotations.Test;

import java.io.IOException;

public class Hello {

    @Test
    public void hi() throws InterruptedException {
        Thread.sleep(400);
    }

    @Test
    public void hi2() {
    }

    @Test(expected = IOException.class)
    public void fail() {
        throw new RuntimeException();
    }
}
