package com.balashoff.bimstand.events.devices.receive;

import java.lang.reflect.Type;

public interface IDeviceReceiveMessageEventHandler {
    void addActionListener(IDeviceReceiveMessageEvent handler);
    void removeActionListener(IDeviceReceiveMessageEvent handler);
    <T> void receive(Type messageType, T message);

}
