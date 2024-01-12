package com.example.game00.controller;

public class UsernamePasswordChecker {

    public static boolean validateUsername(String username) {
        return username != null && username.length() >= 3 && username.length() <= 20;
    }

    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }
}
