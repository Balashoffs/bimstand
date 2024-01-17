package com.bablshoff.bimstand.events.devices.receive;

import java.lang.reflect.Type;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeviceReceiveMessageEventHandler implements IDeviceReceiveMessageEventHandler {
    private final CopyOnWriteArrayList<IDeviceReceiveMessageEvent> handlers;

    public DeviceReceiveMessageEventHandler() {
        handlers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addActionListener(IDeviceReceiveMessageEvent handler) {
        handlers.add(handler);
    }

    @Override
    public void removeActionListener(IDeviceReceiveMessageEvent handler) {
        handlers.remove(handler);
    }

    @Override
    public <T> void receive(Type messageType, T message) {
        for (IDeviceReceiveMessageEvent messageEvent : handlers) {
            messageEvent.receive(messageType, message);
        }
    }




}
