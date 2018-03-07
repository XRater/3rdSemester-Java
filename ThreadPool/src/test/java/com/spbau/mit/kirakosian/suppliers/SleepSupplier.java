package com.spbau.mit.kirakosian.suppliers;

import org.jetbrains.annotations.Nullable;
import com.spbau.mit.kirakosian.threadPool.Utils;

public class SleepSupplier extends BaseSupplier<Void> {

    private int time = 100;

    public SleepSupplier() {

    }

    public SleepSupplier(final int time) {
        this.time = time;
    }

    @Nullable
    @Override
    public Void get() {
        Utils.sleep(time);
        return null;
    }
}
