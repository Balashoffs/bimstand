package com.bablshoff.bimstand.events.ws.send;

import java.util.concurrent.CopyOnWriteArrayList;

public class WSSendMessageEventHandler implements IWSSendMessageEventHandler {
    private final CopyOnWriteArrayList<IWSSendMessageEvent> handlers;

    public WSSendMessageEventHandler() {
        handlers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addActionListener(IWSSendMessageEvent handler) {
        handlers.add(handler);
    }

    @Override
    public void removeActionListener(IWSSendMessageEvent handler) {
        handlers.remove(handler);
    }

    @Override
    public void send(String message) {
        if(!handlers.isEmpty()){
            for (IWSSendMessageEvent mouseActionHandler : handlers) {
                mouseActionHandler.send(message);
            }
        }
    }
}
