package com.bablshoff.bimstand.stand.module;

import com.bablshoff.bimstand.components.ButtonComponent;
import com.bablshoff.bimstand.model.config.StandButtonConfig;
import com.pi4j.context.Context;
import lombok.Getter;

@Getter
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
