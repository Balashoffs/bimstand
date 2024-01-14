package com.bablshoff.bimstand;

import com.bablshoff.bimstand.event.EventManager;

import com.bablshoff.bimstand.stand.StandManager;
import com.bablshoff.bimstand.ws.StandWebSocketServer;

public class Main {
    public static void main(String[] args) {
        //TODO Start web socket server
        EventManager eventManager = EventManager.getInstance();

        StandWebSocketServer server = new StandWebSocketServer(12345);
        eventManager.getWsSendMessageEventHandler().addActionListener(server);

        server.addEventHandler(eventManager.getWsReceiveMessageEventHandler());
        server.start();
        //TODO Bim Stand service

        StandManager standManager = new StandManager();
        eventManager.getWsReceiveMessageEventHandler().addActionListener(standManager);
        standManager.addEventHandler(eventManager.getWsSendMessageEventHandler());

        standManager.start();

        //TODO start PI4J service
    }
}
