package com.bablshoff.bimstand.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OpcMessage {
    final MessageType messageType;
    final String body;
}


