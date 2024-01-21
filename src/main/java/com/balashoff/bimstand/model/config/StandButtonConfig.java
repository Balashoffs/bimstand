package com.balashoff.bimstand.model.config;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@Setter
@NoArgsConstructor
@ToString
public class StandButtonConfig {
    private String id;
    private String name;
    private String moduleType;
    private String moduleId;
    private String type;
    private int pin;
    private boolean inverted;
    private long debounce;
}
