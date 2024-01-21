package com.bablshoff.bimstand.model.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class StandButtonConfig {
    private final String id;
    private final String name;
    private final String moduleType;
    private final String moduleId;
    private final String type;
    private final int pin;
    private final boolean inverted;
    private final long debounce;
}
