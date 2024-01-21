package com.balashoff.bimstand.events.devices.receive;

import java.lang.reflect.Type;

public interface IDeviceReceiveMessageEvent {
    <T> void receive(Type messageType, T message);
}
