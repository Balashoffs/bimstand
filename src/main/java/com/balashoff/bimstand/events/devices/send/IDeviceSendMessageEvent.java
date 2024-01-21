package com.balashoff.bimstand.events.devices.send;

import java.lang.reflect.Type;

public interface IDeviceSendMessageEvent {
    <T>void send(Type messageType, T message);

}
