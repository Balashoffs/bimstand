package com.bablshoff.bimstand.model.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Lighting {
    private final String name;
    private final String id;
    public final String type;
    private final int pin;
}
