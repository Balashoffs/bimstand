package com.balashoff.bimstand.model.message.curtains;

import com.balashoff.bimstand.model.message.IDeviceMessage;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class CurtainsMessage implements IDeviceMessage {
   private String deviceId;
   private int value;
   private String deviceName;
   private CurtainsStatus deviceStatus;
   private CurtainsAction deviceAction;
}
