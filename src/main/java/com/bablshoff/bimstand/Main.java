package com.bablshoff.bimstand;

import com.bablshoff.bimstand.stand.OfficeStandController;
import com.bablshoff.bimstand.events.EventManager;

import com.bablshoff.bimstand.message.MessageManager;
import com.bablshoff.bimstand.ws.StandWebSocketServerEvents;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
        eventManager.getDeviceSendMessageEventHandler().addActionListener(standManager);
        standManager.addEventHandler(eventManager.getWsSendMessageEventHandler());
        standManager.addEventHandler(eventManager.getDeviceSendMessageEventHandler());


        //TODO start PI4J service
        OfficeStandController officeStandController = new OfficeStandController();
        eventManager.getDeviceReceiveMessageEventHandler().addActionListener(officeStandController);
        officeStandController.addEventHandler(eventManager.getDeviceSendMessageEventHandler());
//        officeModel.onExit(()->{
//            try {
//                officeModel.removeEventHandler(eventManager.getDeviceReceiveMessageEventHandler());
//                standManager.removeEventHandler(eventManager.getWsSendMessageEventHandler());
//                standManager.removeEventHandler(eventManager.getDeviceSendMessageEventHandler());
//                server.removeEventEventHandler(eventManager.getWsReceiveMessageEventHandler());
//                server.stop();
//            } catch (InterruptedException e) {
//                log.warn("Stop server by error");
//                log.error(e.getMessage());
//                log.error(e.fillInStackTrace());
//            }
//        });

        officeStandController.start();


    }
}
