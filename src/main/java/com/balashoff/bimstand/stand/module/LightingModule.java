package com.balashoff.bimstand.stand.module;

import com.balashoff.bimstand.components.RelayComponent;
import com.balashoff.bimstand.model.config.LightingConfig;
import com.balashoff.bimstand.model.message.IDeviceMessage;

import com.balashoff.bimstand.model.message.lighting.LightingMessage;
import com.balashoff.bimstand.model.message.lighting.LightingStatus;
import com.pi4j.context.Context;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Log4j2
public class LightingModule implements IDriver {
    private final RelayComponent relayComponent;
    private StandButton button;
    @Getter
    private final String id;
    @Getter
    private final String name;
    @Getter
    private final String type;

    private Consumer<IDeviceMessage> messageConsumer;

    public LightingModule(Context context, LightingConfig config, List<StandButton> buttons) {
        log.debug(config);
        this.id = config.getId();
        this.name = config.getName();
        this.type = config.getType();
        this.relayComponent = new RelayComponent(context, config.getPin());
        this.relayComponent.toggleState();
        Optional<StandButton> standButtonOptional = buttons.stream().filter(standButton -> standButton.getModuleId().equals(config.getId())).findFirst();
        standButtonOptional.ifPresent(standButton -> button = standButton);
        if (button != null) {
            button.onDown(this::switchLight);
        }
    }

    private void switchLight() {
        boolean isState = relayComponent.toggleState();
        switchState = isState;
        IDeviceMessage message = buildMessage(isState);
        log.debug("switchLight: {}", switchState);
        messageConsumer.accept(message);
    }

    private LightingMessage buildMessage(boolean state) {
        LightingStatus status = state ? LightingStatus.on : LightingStatus.off;
        return LightingMessage.builder().deviceId(id).deviceName(name).deviceStatus(status).build();
    }

    private boolean switchState = false;

    public void control(LightingStatus deviceStatus) {
        if (deviceStatus == LightingStatus.reset) {
            if(switchState){
                switchLight();
            }
        } else {
            switchLight();
        }
    }

    @Override
    public void setCallback(Consumer<IDeviceMessage> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }
}
