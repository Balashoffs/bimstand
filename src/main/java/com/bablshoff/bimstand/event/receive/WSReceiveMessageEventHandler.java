package com.bablshoff.bimstand.event.receive;

import java.util.concurrent.CopyOnWriteArrayList;

public class WSReceiveMessageEventHandler implements IWSReceiveMessageEventHandler {
    private final CopyOnWriteArrayList<IWSReceiveMessageEvent> handlers;

    public WSReceiveMessageEventHandler() {
        handlers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addActionListener(IWSReceiveMessageEvent handler) {
        handlers.add(handler);
    }

    @Override
    public void removeActionListener(IWSReceiveMessageEvent handler) {
        handlers.remove(handler);
    }

    @Override
    public void receive(String message) {
        for (IWSReceiveMessageEvent messageEvent : handlers) {
            messageEvent.receive(message);
        }
    }



}
