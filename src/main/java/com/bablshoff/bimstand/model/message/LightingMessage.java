package com.bablshoff.bimstand.model.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LightingMessage {
    final private String id;
    final private boolean isTurnOn;
    final private String name;
}
