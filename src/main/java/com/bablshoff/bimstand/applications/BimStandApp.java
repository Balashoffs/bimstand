package com.bablshoff.bimstand.applications;

import com.bablshoff.bimstand.events.EventManager;
import com.bablshoff.bimstand.message.MessageManager;
import com.bablshoff.bimstand.stand.OfficeStand;
import com.bablshoff.bimstand.stand.OfficeStandController;
import com.bablshoff.bimstand.ws.StandWebSocketServerEvents;
import com.pi4j.context.Context;
import com.bablshoff.bimstand.Application;

public class BimStandApp implements Application {
    @Override
    public void execute(Context pi4j) {
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
        OfficeStand officeStand = new OfficeStand(pi4j);
        OfficeStandController officeStandController = new OfficeStandController(officeStand);
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
