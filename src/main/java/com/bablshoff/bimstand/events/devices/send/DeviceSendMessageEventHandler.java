package com.bablshoff.bimstand.events.devices.send;

import java.lang.reflect.Type;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeviceSendMessageEventHandler implements IDeviceSendMessageEventHandler {
    private final CopyOnWriteArrayList<IDeviceSendMessageEvent> handlers;

    public DeviceSendMessageEventHandler() {
        handlers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addActionListener(IDeviceSendMessageEvent handler) {
        handlers.add(handler);
    }

    @Override
    public void removeActionListener(IDeviceSendMessageEvent handler) {
        handlers.remove(handler);
    }

    @Override
    public <T> void send(Type messageType, T message) {
        if(!handlers.isEmpty()){
            for (IDeviceSendMessageEvent mouseActionHandler : handlers) {
                mouseActionHandler.send(messageType, message);
            }
        }
    }


}
