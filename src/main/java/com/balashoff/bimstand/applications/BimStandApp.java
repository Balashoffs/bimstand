package com.balashoff.bimstand.applications;

import com.balashoff.bimstand.events.EventManager;
import com.balashoff.bimstand.helpers.ReadFromResource;
import com.balashoff.bimstand.message.MessageManager;
import com.balashoff.bimstand.stand.OfficeStand;
import com.balashoff.bimstand.stand.OfficeStandController;
import com.balashoff.bimstand.ws.StandWebSocketServerEvents;
import com.pi4j.context.Context;
import com.balashoff.bimstand.Application;
import lombok.SneakyThrows;

public class BimStandApp implements Application {
    @SneakyThrows
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
        standManager.addEventHandler(eventManager.getDeviceReceiveMessageEventHandler());


        //TODO start PI4J service
        OfficeStand officeStand = new OfficeStand(pi4j);
        OfficeStandController officeStandController = new OfficeStandController(officeStand);
        eventManager.getDeviceReceiveMessageEventHandler().addActionListener(officeStandController);
        officeStandController.addEventHandler(eventManager.getDeviceSendMessageEventHandler());
        String json = ReadFromResource.readConfigFile(BimStandApp.class);
        officeStandController.setupControllerFromFile(json);
        while (true){
            Thread.sleep(10);
        }
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

//        officeStandController.start();
    }
}
