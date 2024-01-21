package com.bablshoff.bimstand.model.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LightingMessage {
    final private String id;
    final private boolean isTurnOn;
    final private String name;
}
