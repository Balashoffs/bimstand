package com.bablshoff.bimstand.stand.module;
import com.bablshoff.bimstand.components.RelayComponent;
import com.bablshoff.bimstand.model.config.LightingConfig;
import com.bablshoff.bimstand.model.message.IDeviceMessage;

import com.bablshoff.bimstand.model.message.lighting.LightingMessage;
import com.bablshoff.bimstand.model.message.lighting.LightingStatus;
import com.pi4j.context.Context;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class LightingModule implements IDriver{
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
        this.id = config.getId();
        this.name = config.getName();
        this.type = config.getType();
        this.relayComponent = new RelayComponent(context, config.getPin());
        Optional<StandButton> standButtonOptional = buttons.stream().filter(standButton -> standButton.getModuleId().equals(config.getId())).findFirst();
        standButtonOptional.ifPresent(standButton -> button = standButton);
        if(button != null){
            button.onDown(this::switchLight);
        }
    }

    private void switchLight(){
        relayComponent.setState(relayComponent.toggleState());
        IDeviceMessage message = buildMessage(relayComponent.toggleState());
        messageConsumer.accept(message);
    }

    private LightingMessage buildMessage(boolean state) {
        LightingStatus status = state ? LightingStatus.on : LightingStatus.off;
        return LightingMessage.builder().deviceId(id).deviceName(name).deviceStatus(status).build();
    }

    public void control() {
        switchLight();
    }

    @Override
    public void setCallback(Consumer<IDeviceMessage> messageConsumer) {
     this.messageConsumer = messageConsumer;
    }
}
