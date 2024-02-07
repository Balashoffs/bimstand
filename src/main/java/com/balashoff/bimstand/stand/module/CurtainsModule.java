package com.balashoff.bimstand.stand.module;

import com.balashoff.bimstand.components.StepMotorComponent;
import com.balashoff.bimstand.model.config.CurtainsConfig;
import com.balashoff.bimstand.model.message.IDeviceMessage;
import com.balashoff.bimstand.model.message.curtains.CurtainsAction;
import com.balashoff.bimstand.model.message.curtains.CurtainsStatus;
import com.balashoff.bimstand.model.message.curtains.CurtainsMessage;
import com.balashoff.bimstand.stand.component.StepMotorController;
import com.pi4j.context.Context;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Log4j2
public class CurtainsModule implements IDriver {

    @Getter
    private final String id;
    @Getter
    private final String name;
    @Getter
    private final String type;

    private final Map<String, StandButton> buttons = new HashMap<>();

    private Consumer<IDeviceMessage> messageConsumer;
    private final StepMotorController stepMotorController;


    public CurtainsModule(Context pi4j, CurtainsConfig curtainsConfig, List<StandButton> buttons) {
        this.id = curtainsConfig.id;
        this.name = curtainsConfig.name;
        this.type = curtainsConfig.type;
        StepMotorComponent stepMotorComponent = new StepMotorComponent(
                pi4j,
                curtainsConfig.pins,
                curtainsConfig.steps,
                curtainsConfig.pulseTime,
                curtainsConfig.maxTurnCount,
                curtainsConfig.turnQnt
        );
        stepMotorController = new StepMotorController(stepMotorComponent);
        stepMotorController.addControllerHandler(this::getUpdatedCurtainsStatus);
        setupButtons(buttons);
        new Thread(stepMotorController).start();
    }

    private void getUpdatedCurtainsStatus(CurtainsStatus status) {
        CurtainsMessage curtainsMessage = CurtainsMessage
                .builder()
                .deviceId(id)
                .deviceStatus(status)
                .deviceName(name)
                .build();
        messageConsumer.accept(curtainsMessage);
    }

    private void setupButtons(List<StandButton> buttons) {
        for (StandButton button : buttons) {
            log.debug(button.getName());
            if (button.getType().equals("up")) {
                button.onDown(() -> {
                    log.debug(button.getType());
                    stepMotorController.addAction(CurtainsAction.forward);
                });
            } else if (button.getType().equals("down")) {
                button.onDown(() -> {
                    log.debug(button.getType());
                    stepMotorController.addAction(CurtainsAction.backward);
                });
            }

            this.buttons.put(button.getType(), button);
        }

    }

    public void control(CurtainsAction action) {
        stepMotorController.addAction(action);
    }


    @Override
    public void setCallback(Consumer<IDeviceMessage> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }
}


