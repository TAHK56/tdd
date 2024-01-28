package com.geeksforless.math;

public enum Coefficient {
    FIRST(2.7), SECOND(-0.3), FREE(4);

    private final double value;

    Coefficient(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
