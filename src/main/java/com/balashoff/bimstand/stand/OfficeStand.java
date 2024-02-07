package com.balashoff.bimstand.stand;

import com.balashoff.bimstand.model.message.IDeviceMessage;
import com.balashoff.bimstand.model.message.SetupMessage;
import com.balashoff.bimstand.model.message.curtains.CurtainsAction;
import com.balashoff.bimstand.model.message.curtains.CurtainsMessage;
import com.balashoff.bimstand.model.message.lighting.LightingMessage;
import com.balashoff.bimstand.model.message.lighting.LightingStatus;
import com.balashoff.bimstand.stand.component.BoardLedStatusComponent;
import com.balashoff.bimstand.stand.module.CurtainsModule;
import com.balashoff.bimstand.stand.module.LightingModule;
import com.balashoff.bimstand.stand.module.StandButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pi4j.context.Context;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Log4j2
public class OfficeStand {

    private final Map<String, CurtainsModule> curtainsDrivers = new HashMap<>();
    private final Map<String, LightingModule> lightingDrivers = new HashMap<>();
    private BoardLedStatusComponent ledStatus;
    private StandButton resetStandButton;

    private final int setupVersion = 1;

    public OfficeStand(Context context) {
        this.context = context;
    }

    private final Context context;


    public void controlLight(LightingMessage lightingMessage) {
        if (lightingDrivers.isEmpty()) {
            log.warn("You forgot init lighting modules at app!!!");
            return;
        }
        final String id = lightingMessage.getDeviceId();
        if (lightingDrivers.containsKey(id)) {
            LightingModule lightingModule = lightingDrivers.get(id);
            lightingModule.control();
        }
    }

    public void controlCurtains(CurtainsMessage curtainsMessage) {
        if (curtainsDrivers.isEmpty()) {
            log.warn("You forgot init curtains modules at app!!!");
            return;
        }
        String id = curtainsMessage.getDeviceId();
        if (curtainsDrivers.containsKey(id)) {
            CurtainsModule curtainsModule = curtainsDrivers.get(id);
            curtainsModule.control(curtainsMessage.getDeviceAction());
        }
    }

    public void setupDevice(SetupMessage setupMessage, Consumer<IDeviceMessage> messageConsumer) {
        if (setupMessage.getSetupVersion() == setupVersion) {
            messageConsumer.accept(SetupMessage.builder().build());
        } else {
            curtainsDrivers.clear();
            lightingDrivers.clear();
            ledStatus.turnOff();
        }

    }

    public void setupDeviceFromJson(String json, Consumer<IDeviceMessage> messageConsumer) {
        String prt = json.substring(0, 100);
        log.warn(prt);
        Gson gson = new GsonBuilder().serializeNulls().create();
        SetupMessage setupMessage = gson.fromJson(json, SetupMessage.class);
        setupDevices(setupMessage, messageConsumer);
        log.debug(setupMessage);
    }

    private void setupDevices(SetupMessage setupMessage, Consumer<IDeviceMessage> messageConsumer) {
        ledStatus = new BoardLedStatusComponent(context, setupMessage.getStandStatusConfig());
        ledStatus.turnOn();


        Map<String, Boolean> deviceType = Arrays.stream(setupMessage.getDeviceType()).collect(Collectors.toMap(o -> o, o -> false));
        Map<String, List<StandButton>> buttons = new HashMap<>();
        setupMessage.getStandButtonsSet().forEach((type, standButtons) -> {
            standButtons.forEach(standButton -> {
                String dt = deviceType.keySet().stream().filter(s -> s.equals(standButton.getModuleType())).findFirst().orElse("");
                log.debug("Device type: {}", dt);
                StandButton buttonComponent = new StandButton(context, standButton);
                log.debug(buttonComponent);
                buttons.putIfAbsent(dt, new ArrayList<>());
                buttons.get(dt).add(buttonComponent);
            });
        });

        if (!buttons.isEmpty()) {
            curtainsDrivers.putAll(setupMessage.getCurtainsConfigSet().stream().map(curtains -> {
                List<StandButton> standButtons = buttons.get(curtains.type).stream().filter(standButton -> standButton.getModuleId().equals(curtains.id)).toList();
                CurtainsModule module = new CurtainsModule(context, curtains, standButtons);
                module.setCallback(messageConsumer);
                return module;
            }).collect(Collectors.toMap(CurtainsModule::getId, curtainsModule -> curtainsModule)));

            lightingDrivers.putAll(setupMessage.getLightingConfigSet().stream().map(lighting -> {
                List<StandButton> standButtons = buttons.get(lighting.getType()).stream().filter(standButton -> standButton.getModuleId().equals(lighting.getId())).toList();
                LightingModule module = new LightingModule(context, lighting, standButtons);
                module.setCallback(messageConsumer);
                return module;
            }).collect(Collectors.toMap(LightingModule::getId, lightingModule -> lightingModule)));

            if (buttons.containsKey("reset")) {
                resetStandButton = buttons.get("reset").get(0);
                resetStandButton.onDown(() -> {
                    curtainsDrivers.values().forEach(s -> {
                        CurtainsMessage message = CurtainsMessage.builder().deviceId(s.getId()).deviceName(s.getName()).deviceAction(CurtainsAction.close).build();
                        controlCurtains(message);
                    });
                    lightingDrivers.values().forEach(s -> {
                        LightingMessage message = LightingMessage.builder().deviceId(s.getId()).deviceName(s.getName()).deviceStatus(LightingStatus.off).build();
                        controlLight(message);
                    });
                });
            }
        }


    }
}
