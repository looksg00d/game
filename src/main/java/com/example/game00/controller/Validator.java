package com.example.game00.controller;

public class Validator {

    public static boolean validateUsername(String username) {
        // Пример: имя пользователя не должно быть пустым и должно быть длиной от 3 до 20 символов
        // Можно добавить дополнительные проверки, если нужно
        return username != null && username.length() >= 3 && username.length() <= 20;
    }

    public static boolean validatePassword(String password) {
        // Пример: пароль должен быть не короче 6 символов
        return password != null && password.length() >= 6;
    }
}
