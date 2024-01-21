package com.balashoff.bimstand.model.message;

import com.balashoff.bimstand.model.config.CurtainsConfig;
import com.balashoff.bimstand.model.config.LightingConfig;
import com.balashoff.bimstand.model.config.StandButtonConfig;
import com.balashoff.bimstand.model.config.StandStatusConfig;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
@ToString
public class SetupMessage implements IDeviceMessage {
    private int setupVersion;
    private List<CurtainsConfig> curtainsConfigSet;
    private List<LightingConfig> lightingConfigSet;
    private StandStatusConfig standStatusConfig;
    private Map<String, List<StandButtonConfig>> standButtonsSet;
    private String[] deviceType;
}
