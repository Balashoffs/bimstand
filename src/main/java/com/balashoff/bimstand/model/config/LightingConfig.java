package com.balashoff.bimstand.model.config;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@Setter
@NoArgsConstructor
@ToString
public class LightingConfig {
    private String name;
    private String id;
    private String type;
    private int pin;
}
