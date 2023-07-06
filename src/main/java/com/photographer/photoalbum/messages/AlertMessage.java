package com.photographer.photoalbum.messages;


public class AlertMessage {
    private final AlertMessageType type;
    private final String message;

    public AlertMessage(AlertMessageType type, String message) {
        this.type = type;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public AlertMessageType getType() {
        return type;
    }
}