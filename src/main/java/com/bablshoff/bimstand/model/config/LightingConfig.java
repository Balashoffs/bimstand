package com.bablshoff.bimstand.model.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class LightingConfig {
    private final String name;
    private final String id;
    public final String type;
    private final int pin;
}
