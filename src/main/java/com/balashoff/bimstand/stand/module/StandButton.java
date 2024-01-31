package com.balashoff.bimstand.stand.module;

import com.balashoff.bimstand.components.ButtonComponent;
import com.balashoff.bimstand.model.config.StandButtonConfig;
import com.pi4j.context.Context;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StandButton extends ButtonComponent {
    private final String id;
    private final String name;
    private final String type;
    private final String moduleType;
    private final String moduleId;

    public StandButton(Context pi4j, StandButtonConfig config) {
        super(pi4j, config.getPin(), config.isInverted(), config.getDebounce());
        id = config.getId();
        name = config.getName();
        type = config.getType();
        moduleType = config.getModuleType();
        moduleId = config.getModuleId();
    }
}
