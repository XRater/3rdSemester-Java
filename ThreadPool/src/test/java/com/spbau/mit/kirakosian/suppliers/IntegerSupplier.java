package com.spbau.mit.kirakosian.suppliers;

public class IntegerSupplier extends BaseSupplier<Integer> {

    private final Integer n;

    public IntegerSupplier(final int n) {
        this.n = n;
    }

    @Override
    public Integer get() {
        return n;
    }
}
