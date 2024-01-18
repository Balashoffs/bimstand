package com.bablshoff.bimstand.model.lighting;

import com.bablshoff.bimstand.model.IDeviceMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LightingMessage implements IDeviceMessage {
   private final String deviceId;
   private final boolean isTurnOn;
   private final String deviceName;
   private final LightingStatus deviceStatus;
}
