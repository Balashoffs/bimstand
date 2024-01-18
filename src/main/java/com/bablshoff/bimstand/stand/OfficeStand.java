package com.bablshoff.bimstand.stand;

import com.bablshoff.bimstand.components.ButtonComponent;
import com.bablshoff.bimstand.model.IDeviceMessage;
import com.bablshoff.bimstand.model.SetupMessage;
import com.bablshoff.bimstand.model.curtains.CurtainsStatus;
import com.bablshoff.bimstand.model.curtains.СurtainsMessage;
import com.bablshoff.bimstand.model.lighting.LightingMessage;
import com.bablshoff.bimstand.model.lighting.LightingStatus;
import com.bablshoff.bimstand.stand.device.CurtainsDriver;
import com.bablshoff.bimstand.stand.device.LightingDriver;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@AllArgsConstructor()
public class OfficeStand {
    private final LinkedBlockingQueue<IDeviceMessage> blockingQueue;
    private final Map<String, CurtainsDriver> curtainsDrivers = new HashMap<>();
    private final Map<String, LightingDriver> lightingDrivers = new HashMap<>();


    public void controlLight(LightingMessage lightingMessage) {
        final String id = lightingMessage.getDeviceId();
        if (lightingDrivers.containsKey(id)) {
            LightingDriver lightingDriver = lightingDrivers.get(id);
            lightingDriver.control(lightingMessage.isTurnOn(), (LightingStatus status) -> {
                boolean isTurnOn = status.equals(LightingStatus.on);
                LightingMessage answer = LightingMessage
                        .builder()
                        .deviceId(id)
                        .deviceName(lightingMessage.getDeviceName())
                        .isTurnOn(isTurnOn)
                        .deviceStatus(status)
                        .build();
                blockingQueue.add(answer);
            });
        }
    }

    public void controlСurtains(СurtainsMessage curtainsMessage) {
        String id = curtainsMessage.getDeviceId();
        if (curtainsDrivers.containsKey(id)) {
            CurtainsDriver curtainsDriver = curtainsDrivers.get(id);
            curtainsDriver.control(curtainsMessage.isOpen(), (CurtainsStatus status, Integer value) -> {
                boolean isOpen = status.equals(CurtainsStatus.opened);
                СurtainsMessage answer = СurtainsMessage
                        .builder()
                        .deviceId(id)
                        .deviceStatus(status)
                        .deviceName(curtainsMessage.getDeviceName())
                        .isOpen(isOpen)
                        .value(value)
                        .build();
                blockingQueue.add(answer);
            });
        }
    }

    public void setupDevice(SetupMessage setupMessage) {

    }
}
