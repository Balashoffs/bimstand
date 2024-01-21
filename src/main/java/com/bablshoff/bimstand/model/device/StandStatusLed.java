package com.bablshoff.bimstand.model.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class StandStatusLed {
    private final String id;
    private final String name;
    private final int pin;
}
