package com.bablshoff.bimstand.stand.module;

import com.bablshoff.bimstand.model.message.IDeviceMessage;

import java.util.function.Consumer;

public interface IDriver {
    void setCallback(Consumer<IDeviceMessage> messageConsumer);
    Consumer<IDeviceMessage> messageConsumer = null;
}
