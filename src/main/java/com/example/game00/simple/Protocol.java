package com.example.game00.simple;

public class Protocol {
    public static final int PORT = 12345;

    // Формат сообщения может включать идентификатор отправителя, тип сообщения и само сообщение
    public static String createMessage(String senderId, String messageType, String messageContent) {
        return senderId + ";" + messageType + ";" + messageContent;
    }

    // Метод для разбора сообщения
    public static String[] parseMessage(String message) {
        return message.split(";");
    }

    // Типы сообщений, например, CHAT для чат-сообщений, GUESS для догадок в игре
    public static final String MESSAGE_TYPE_CHAT = "CHAT";
    public static final String MESSAGE_TYPE_GUESS = "GUESS";
}
