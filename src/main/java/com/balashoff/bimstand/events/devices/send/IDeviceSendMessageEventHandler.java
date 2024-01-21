package com.balashoff.bimstand.events.devices.send;

import java.lang.reflect.Type;

public interface IDeviceSendMessageEventHandler {
    void addActionListener(IDeviceSendMessageEvent handler);

    void removeActionListener(IDeviceSendMessageEvent handler);

    <T>void send(Type messageType, T message);
}
