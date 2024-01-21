package com.bablshoff.bimstand.model.message;

import com.bablshoff.bimstand.model.config.CurtainsConfig;
import com.bablshoff.bimstand.model.config.LightingConfig;
import com.bablshoff.bimstand.model.config.StandButtonConfig;
import com.bablshoff.bimstand.model.config.StandStatusConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class SetupMessage implements IDeviceMessage {
    private final int setupVersion;
    private final List<CurtainsConfig> curtainsConfigSet;
    private final List<LightingConfig> lightingConfigSet;
    private final StandStatusConfig standStatusConfig;
    private final Map<String, List<StandButtonConfig>> standButtonsSet;
    private final String[] deviceType;
}
