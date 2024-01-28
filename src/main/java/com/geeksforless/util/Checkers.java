package com.geeksforless.util;

import java.util.Objects;

public final class Checkers {

    private Checkers() {
        throw new AssertionError("You cannot create this class!");
    }

    private static boolean isCorrectInput(String userInput) {
        return Objects.nonNull(userInput) && !userInput.isBlank();
    }

    public static boolean isNumber(String userInput) {
        return isCorrectInput(userInput) && userInput.matches("-?\\d+(\\.\\d+)?");
    }

}
