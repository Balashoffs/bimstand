package com.bablshoff.bimstand.ws;

import com.bablshoff.bimstand.events.ws.receive.IWSReceiveMessageEventHandler;
import com.bablshoff.bimstand.events.ws.send.IWSSendMessageEvent;
import org.java_websocket.WebSocket;

public class StandWebSocketServerEvents extends StandWebSocketServer implements IWSSendMessageEvent {
    public StandWebSocketServerEvents(int port) {
        super(port);
    }

    private IWSReceiveMessageEventHandler eventHandler;

    public void addEventHandler(IWSReceiveMessageEventHandler handler){
        eventHandler = handler;
    }

    public void removeEventEventHandler(IWSReceiveMessageEventHandler handler){
        eventHandler = handler;
    }

    @Override
    public void send(String message) {
        getConnections().stream().findFirst().ifPresent(webSocket -> webSocket.send(message));
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        if(eventHandler != null){
            eventHandler.receive(s);
        }
    }
}
