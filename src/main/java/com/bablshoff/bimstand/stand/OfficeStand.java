package com.bablshoff.bimstand.stand;

import com.bablshoff.bimstand.components.ButtonComponent;
import com.bablshoff.bimstand.model.message.IDeviceMessage;
import com.bablshoff.bimstand.model.message.SetupMessage;
import com.bablshoff.bimstand.model.message.curtains.CurtainsStatus;
import com.bablshoff.bimstand.model.message.curtains.СurtainsMessage;
import com.bablshoff.bimstand.model.message.lighting.LightingMessage;
import com.bablshoff.bimstand.model.message.lighting.LightingStatus;
import com.bablshoff.bimstand.stand.device.CurtainsDriver;
import com.bablshoff.bimstand.stand.device.LightingDriver;
import com.pi4j.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class OfficeStand {

    private final Map<String, CurtainsDriver> curtainsDrivers = new HashMap<>();
    private final Map<String, LightingDriver> lightingDrivers = new HashMap<>();


    public OfficeStand(Context context) {
        this.context = context;
    }

    public void setMessageConsumer(Consumer<IDeviceMessage> messageConsumer) {
        this.messageConsumer = messageConsumer;

    }

    private Consumer<IDeviceMessage> messageConsumer;
    private final Context context;


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
                messageConsumer.accept(answer);

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
                messageConsumer.accept(answer);
            });
        }
    }

    public void setupDevice(SetupMessage setupMessage) {
        String[] deviceType = setupMessage.getDeviceType();
        Map<String, List<ButtonComponent>> buttons = new HashMap<>();
        setupMessage.getStandButtonsSet().forEach((type, standButtons) -> {

        });
    }
}
