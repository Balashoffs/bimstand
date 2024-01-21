package com.balashoff.bimstand.model.config;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
@ToString
public class StandStatusConfig {
    private String id;
    private String name;
    private int pin;
}
