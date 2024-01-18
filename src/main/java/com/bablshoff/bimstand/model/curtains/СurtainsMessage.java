package com.bablshoff.bimstand.model.curtains;

import com.bablshoff.bimstand.model.IDeviceMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class СurtainsMessage implements IDeviceMessage {
   private final String deviceId;
   private final boolean isOpen;
   private final int value;
   private final String deviceName;
   private final CurtainsStatus deviceStatus;
}
