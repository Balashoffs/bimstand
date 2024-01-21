package com.bablshoff.bimstand.model.message.curtains;

import com.bablshoff.bimstand.model.message.IDeviceMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CurtainsMessage implements IDeviceMessage {
   private final String deviceId;
   private final int value;
   private final String deviceName;
   private final CurtainsStatus deviceStatus;
   private final CurtainsAction deviceAction;
}
