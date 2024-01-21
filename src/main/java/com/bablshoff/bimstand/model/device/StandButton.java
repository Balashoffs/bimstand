package com.bablshoff.bimstand.model.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class StandButton {
    private final String id;
    private final String name;
    private final String type;
    private final int pin;
    private final boolean inverted;
    private final long debounce;
}
