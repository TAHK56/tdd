package com.geeksforless.math;

public record Point(double x, double y) {

    @Override
    public String toString() {
        return String.format("Point(%.6f; %.6f)", x, y);
    }
}
