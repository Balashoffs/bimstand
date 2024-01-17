package com.bablshoff.bimstand.model.lighting;

import com.bablshoff.bimstand.model.IDeviceMessage;
import com.bablshoff.bimstand.model.curtains.CurtainsStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LightingMessage implements IDeviceMessage {
   private final String id;
   private final boolean isTurnOn;
   private final String name;
   private final LightingStatus status;
}
