package com.bablshoff.bimstand.model.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OpcMessage {
    final MessageType messageType;
    final String body;
}


