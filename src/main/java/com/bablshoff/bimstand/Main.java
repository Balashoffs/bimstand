package com.bablshoff.bimstand;

import com.bablshoff.bimstand.events.EventManager;

import com.bablshoff.bimstand.stand.MessageManager;
import com.bablshoff.bimstand.ws.StandWebSocketServerEvents;

public class Main {
    public static void main(String[] args) {
        //TODO Start web socket server
        EventManager eventManager = EventManager.getInstance();

        StandWebSocketServerEvents server = new StandWebSocketServerEvents(12345);
        eventManager.getWsSendMessageEventHandler().addActionListener(server);

        server.addEventHandler(eventManager.getWsReceiveMessageEventHandler());
        server.start();
        //TODO Bim Stand service

        MessageManager standManager = new MessageManager();
        eventManager.getWsReceiveMessageEventHandler().addActionListener(standManager);
        eventManager.getDeviceReceiveMessageEventHandler().addActionListener(standManager);
        standManager.addEventHandler(eventManager.getWsSendMessageEventHandler());
        standManager.addEventHandler(eventManager.getDeviceSendMessageEventHandler());


        //TODO start PI4J service
    }
}
