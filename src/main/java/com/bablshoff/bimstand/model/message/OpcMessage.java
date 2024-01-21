package com.bablshoff.bimstand.model.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class OpcMessage {
    public OpcMessage(MessageType messageType, String body) {
        this.messageType = messageType;
        this.body = body;
    }

    private final MessageType messageType;
   private final String body;
}


