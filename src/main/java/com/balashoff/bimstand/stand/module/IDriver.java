package com.balashoff.bimstand.stand.module;

import com.balashoff.bimstand.model.message.IDeviceMessage;

import java.util.function.Consumer;

public interface IDriver {
    void setCallback(Consumer<IDeviceMessage> messageConsumer);
    Consumer<IDeviceMessage> messageConsumer = null;
}
