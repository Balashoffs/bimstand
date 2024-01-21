package com.bablshoff.bimstand.model.message;

import com.bablshoff.bimstand.model.device.Curtains;
import com.bablshoff.bimstand.model.device.Lighting;
import com.bablshoff.bimstand.model.device.StandButton;
import com.bablshoff.bimstand.model.device.StandStatusLed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class SetupMessage implements IDeviceMessage {
    private final List<Curtains> curtainsSet;
    private final List<Lighting> lightingSet;
    private final StandStatusLed standStatusLed;
    private final Map<String, List<StandButton>> standButtonsSet;
    private final String[] deviceType;
}
