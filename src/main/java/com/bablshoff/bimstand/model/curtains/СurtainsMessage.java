package com.bablshoff.bimstand.model.curtains;

import com.bablshoff.bimstand.model.IDeviceMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class СurtainsMessage implements IDeviceMessage {
   private final String id;
   private final boolean isOpen;
   private final String name;
   private final CurtainsStatus status;
}
