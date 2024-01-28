package com.geeksforless;

import com.geeksforless.math.Coefficient;
import com.geeksforless.math.Point;
import com.geeksforless.util.Checkers;
import com.geeksforless.util.UserInputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Map;

@SuppressWarnings("all")
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getSimpleName());
    public static final double BOUND_X = 1.4;
    public static final double BEGIN_SEGMENT_X = 0;
    public static final double END_SEGMENT_X = 2.0;
    public static final double STEP = 0.002;

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    public double calculateFunction(double x) {
        if (x > BOUND_X) {
            return (Coefficient.FIRST.getValue() + Coefficient.SECOND.getValue() * x) / Math.hypot(x, 1);
        } else if (x < BOUND_X) {
            return Coefficient.FIRST.getValue() * Math.pow(x, 2) + Coefficient.SECOND.getValue() * x
                   + Coefficient.FREE.getValue();
        } else {
            return Coefficient.FIRST.getValue() / x + Math.hypot(x, 1);
        }
    }

    public int calculatePointsInSegmentByStep(double begin, double end, double step) {
        return (int) ((end - begin) / step + 1);
    }

    public Point[] createPointsInGivenSegmentWithGivenStep(double begin, double end, double step) {
        Point[] points = new Point[calculatePointsInSegmentByStep(begin, end, step)];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(begin, calculateFunction(begin));
            begin += step;
        }
        return points;
    }

    public double getMaxValue() {
        return findAllNeededInformation(createPointsInGivenSegmentWithGivenStep(BEGIN_SEGMENT_X, END_SEGMENT_X, STEP))
                .getMax();
    }

    public double getMinValue() {
        return findAllNeededInformation(createPointsInGivenSegmentWithGivenStep(BEGIN_SEGMENT_X, END_SEGMENT_X, STEP))
                .getMin();
    }

    public double getTotalValue() {
        return findAllNeededInformation(createPointsInGivenSegmentWithGivenStep(BEGIN_SEGMENT_X, END_SEGMENT_X, STEP))
                .getSum();
    }

    public double getAverageValue() {
        return findAllNeededInformation(createPointsInGivenSegmentWithGivenStep(BEGIN_SEGMENT_X, END_SEGMENT_X, STEP))
                .getAverage();
    }

    public Map<Integer, Point> findMaxPointWithIndex() {
        var points = createPointsInGivenSegmentWithGivenStep(BEGIN_SEGMENT_X, END_SEGMENT_X, STEP);
        for (int i = 0; i < points.length; i++) {
            if (getMaxValue() == points[i].y()) {
                return Map.of(i, points[i]);
            }
        }
        return Map.of();
    }

    public Map<Integer, Point> findMinPointWithIndex() {
        var points = createPointsInGivenSegmentWithGivenStep(BEGIN_SEGMENT_X, END_SEGMENT_X, STEP);
        for (int i = 0; i < points.length; i++) {
            if (getMinValue() == points[i].y()) {
                return Map.of(i, points[i]);
            }
        }
        return Map.of();
    }

    private DoubleSummaryStatistics findAllNeededInformation(Point[] points) {
        return Arrays.stream(points)
                .mapToDouble(Point::y)
                .summaryStatistics();
    }

    private void preview() {
        String task = """
                y = (a * x * x) + (b * x) + c     if   x < 1.4
                y = (a / x) + sqrt(x * x + 1)     if   x = 1.4
                y = (a + b * x) / sqrt(x * x + 1) if x > 1.4
                Coefficients of the equations: a = 2.7; b = -0.3; c = 4;
                This is segment: [0.0; 2.0] delta x = 0.002
                And numbers for test points in the given segment: 0, 700, 1000
                """;
        LOGGER.info("Hello! This is the TDD homework and this is my task:\n{}", task);
        LOGGER.info("Enter independent variable x and the program show you dependent variable y:");
    }

    private void start() {
        preview();
        String independentVariableX = UserInputs.getUserInput();
        while (!Checkers.isNumber(independentVariableX)) {
            LOGGER.info("Enter the correct number!");
            independentVariableX = UserInputs.getUserInput();
        }
        double x = Double.parseDouble(independentVariableX);
        LOGGER.info("y = {}", calculateFunction(x));
        LOGGER.info("Points in segment [0.0; 2.0] with step 0.002 is {}",
                calculatePointsInSegmentByStep(BEGIN_SEGMENT_X, END_SEGMENT_X, STEP));
        LOGGER.info("Array points in this segment are: ");
        var points = createPointsInGivenSegmentWithGivenStep(BEGIN_SEGMENT_X, END_SEGMENT_X, STEP);
        LOGGER.info(Arrays.toString(points));
        LOGGER.info("MaxValue y is: {}", getMaxValue());
        LOGGER.info("MinValue y is: {}", getMinValue());
        LOGGER.info("TotalValue y is: {}", getTotalValue());
        LOGGER.info("AverageValue y is: {}", getAverageValue());
        LOGGER.info("MaxIndex and Point is : {}", findMaxPointWithIndex());
        LOGGER.info("MinIndex and Point is : {}", findMinPointWithIndex());
        LOGGER.info("Points in the given segment 0 is {}; 700 is {}; 1000 is {}", points[0], points[700], points[1000]);

    }
}