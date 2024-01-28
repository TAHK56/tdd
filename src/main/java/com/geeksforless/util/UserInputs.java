package com.geeksforless.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class UserInputs {

    private UserInputs() {
        throw new AssertionError("You cannot create this class!");
    }

    public static String getUserInput() {
        String userInput;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            userInput = reader.readLine();
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return userInput;
    }
}
