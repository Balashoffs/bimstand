package com.balashoff.bimstand.model.message.lighting;

import com.balashoff.bimstand.model.message.IDeviceMessage;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class LightingMessage implements IDeviceMessage {
   private String deviceId;
   private String deviceName;
   private LightingStatus deviceStatus;
}
