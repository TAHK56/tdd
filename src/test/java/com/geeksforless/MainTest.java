package com.geeksforless;

import com.geeksforless.math.Point;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

@DisplayName("Test the math function given in README.md file")
class MainTest {

    private static final double STANDARD_DEVIATION = 1E-9;
    private Main main;

    @BeforeEach
    void setUp() {
        main = new Main();
    }


    @ParameterizedTest(name = "value x = {0} and result y = {1}")
    @CsvSource(value = {"1.6, 1.1765976468070596", "2.6, 0.6892398322730285", "1.7, 1.1103740771738326"})
    void checkCalculateDependentVariableGreaterGivenConstant(double x, double expectedY) {
        double result = main.calculateFunction(x);

        Assertions.assertThat(result).describedAs(() -> "Result function y = (a + b * x) / sqrt(x * x + 1) when x > 1.4")
                .isEqualTo(expectedY, Offset.offset(STANDARD_DEVIATION));
    }

    @ParameterizedTest(name = "value x = {0} and result y = {1}")
    @CsvSource(value = {"1, 6.4", "0, 4", "-1, 7"})
    void checkCalculateDependentVariableLessGivenConstant(double x, double expectedY) {
        double result = main.calculateFunction(x);

        Assertions.assertThat(result).describedAs(() -> "Result function y = (a * x * x) + (b * x) + c when x < 1.4")
                .isEqualTo(expectedY, Offset.offset(STANDARD_DEVIATION));
    }

    @Test
    void checkCalculateDependentVariableEqualToConstant() {
        double expectedY = 3.6490364819799543;

        double result = main.calculateFunction(Main.BOUND_X);

        Assertions.assertThat(result).describedAs(() -> "Result function y = (a / x) + sqrt(x * x + 1) when x = 1.4")
                .isEqualTo(expectedY, Offset.offset(STANDARD_DEVIATION));
    }

    @ParameterizedTest(name = "Segment is [{0};{1}] with step = {2} and points = {1}")
    @MethodSource("createSegmentWithGivenStepAndExpectedSize")
    void checkNumberPointsInGivenSegmentByStep(double begin, double end, double step, int expectedPointsSize) {
        int result = main.calculatePointsInSegmentByStep(begin, end, step);

        Assertions.assertThat(result).describedAs(() -> "Number points in segment [0;2]").isEqualTo(expectedPointsSize);
    }

    @ParameterizedTest(name = "Segment is [{0};{1}] with step = {2} and Array = {1}")
    @MethodSource("createSegmentWithGivenStepAndExpectedArray")
    void checkArrayInGivenSegmentByStep(double begin, double end, double step, Point[] expectedPoints) {
        var points  = main.createPointsInGivenSegmentWithGivenStep(begin, end, step);

        Assertions.assertThat(points).describedAs(() -> "points should be equals expectedArray")
                .isEqualTo(expectedPoints);
    }

    @Test
    void checkMaxValueInGivenSegment() {
        double expectedMaxValue = 8.857490800000008;

        Assertions.assertThat(main.getMaxValue()).as(() -> "Max dependent value")
                .isEqualTo(expectedMaxValue, Offset.offset(STANDARD_DEVIATION));
    }

    @Test
    void checkMinValueInGivenSegment() {
        double expectedMinValue = 0.9391485505499111;

        Assertions.assertThat(main.getMinValue()).as(() -> "Min dependent value")
                .isEqualTo(expectedMinValue, Offset.offset(STANDARD_DEVIATION));
    }

    @Test
    void checkTotalValuesInGivenSegment() {
        double expectedTotalValue = 4221.789184754883;

        Assertions.assertThat(main.getTotalValue()).as(() -> "Total dependent values")
                .isEqualTo(expectedTotalValue, Offset.offset(STANDARD_DEVIATION));
    }

    @Test
    void checkAverageValuesInGivenSegment() {
        double expectedAverageValue = 4.2175716131417404;

        Assertions.assertThat(main.getAverageValue()).as(() -> "Total dependent values")
                .isEqualTo(expectedAverageValue, Offset.offset(STANDARD_DEVIATION));
    }


    @Test
    void checkPositionMaxPointInGivenSegment() {
        int indexExpected = 699;
        Point maxExpected = new Point(1.398000000000001, 8.857490800000008);

        Map<Integer, Point> maxPointWithIndex = main.findMaxPointWithIndex();

        Assertions.assertThat(maxPointWithIndex).isEqualTo(Map.of(indexExpected, maxExpected));
    }

    @Test
    void checkPositionMinPointInGivenSegment() {
        int indexExpected = 1000;
        Point minExpected = new Point(2.0000000000000013, 0.9391485505499111);

        Map<Integer, Point> minPointWithIndex = main.findMinPointWithIndex();

        Assertions.assertThat(minPointWithIndex).isEqualTo(Map.of(indexExpected, minExpected));
    }

    private static Stream<Arguments> createSegmentWithGivenStepAndExpectedSize() {
        return Stream.of(Arguments.of(0, 2, 0.002, 1001), Arguments.of(0, 2, 0.001, 2001),
                Arguments.of(0, 2, 1, 3), Arguments.of(0, 3, 0.004, 751));
    }

    private static Stream<Arguments> createSegmentWithGivenStepAndExpectedArray() {
       var firstPoints = new Point[] {new Point(0, 4), new Point(1, 6.4), new Point(2, 0.9391485505499116)};
        return Stream.of(Arguments.of(0, 2, 1, firstPoints));
    }

}