package com.bablshoff.bimstand.stand;

import com.bablshoff.bimstand.events.devices.receive.IDeviceReceiveMessageEvent;
import com.bablshoff.bimstand.events.devices.send.DeviceSendMessageEventHandler;
import com.bablshoff.bimstand.model.IDeviceMessage;
import com.bablshoff.bimstand.model.SetupMessage;
import com.bablshoff.bimstand.model.curtains.СurtainsMessage;
import com.bablshoff.bimstand.model.lighting.LightingMessage;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Type;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


@Log4j2
public class OfficeStandController implements IDeviceReceiveMessageEvent, Runnable {
    private final LinkedBlockingQueue<IDeviceMessage> blockingQueue = new LinkedBlockingQueue<>(50);
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private DeviceSendMessageEventHandler deviceEventHandler;
    private final OfficeStand ofiiceModel = new OfficeStand(blockingQueue);

    public void addEventHandler(DeviceSendMessageEventHandler handler) {
        deviceEventHandler = handler;
    }

    public void removeEventHandler(DeviceSendMessageEventHandler handler) {
        deviceEventHandler = handler;
    }



    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (isRunning.get()){
            try {
                IDeviceMessage iDeviceMessage = blockingQueue.take();
                Type type = TypeToken.getParameterized(iDeviceMessage.getClass()).getType();
                deviceEventHandler.send(type, iDeviceMessage);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onExit(IExit exit) {
        exit.exit();
    }


    @Override
    public <T> void receive(Type messageType, T message) {
        if(messageType instanceof LightingMessage){
            LightingMessage lightingMessage = (LightingMessage) message;
            ofiiceModel.controlLight(lightingMessage);
        }else if(messageType instanceof СurtainsMessage){
            СurtainsMessage curtainsMessage = (СurtainsMessage)message;
            ofiiceModel.controlСurtains(curtainsMessage);
        }else if(messageType instanceof SetupMessage){
            SetupMessage setupMessage = (SetupMessage)message;
            ofiiceModel.setupDevice(setupMessage);
        }else{
            log.warn("Incoming message is not valid: {}", messageType.getTypeName());
        }
    }
}
