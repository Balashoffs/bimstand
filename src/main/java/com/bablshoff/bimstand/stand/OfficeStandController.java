package com.bablshoff.bimstand.stand;

import com.bablshoff.bimstand.events.devices.receive.IDeviceReceiveMessageEvent;
import com.bablshoff.bimstand.events.devices.send.DeviceSendMessageEventHandler;
import com.bablshoff.bimstand.model.message.IDeviceMessage;
import com.bablshoff.bimstand.model.message.SetupMessage;
import com.bablshoff.bimstand.model.message.curtains.СurtainsMessage;
import com.bablshoff.bimstand.model.message.lighting.LightingMessage;
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
    private final OfficeStand officeStand;

    public void addEventHandler(DeviceSendMessageEventHandler handler) {
        deviceEventHandler = handler;
    }

    public void removeEventHandler(DeviceSendMessageEventHandler handler) {
        deviceEventHandler = handler;
    }

    public void consumeMessageFromStandDevice(IDeviceMessage deviceMessage){
        blockingQueue.add(deviceMessage);
    }

    public OfficeStandController(OfficeStand officeStand) {
        this.officeStand = officeStand;
        this.officeStand.setMessageConsumer(this::consumeMessageFromStandDevice);
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


    @Override
    public <T> void receive(Type messageType, T message) {
        if(messageType instanceof LightingMessage){
            LightingMessage lightingMessage = (LightingMessage) message;
            officeStand.controlLight(lightingMessage);
        }else if(messageType instanceof СurtainsMessage){
            СurtainsMessage curtainsMessage = (СurtainsMessage)message;
            officeStand.controlСurtains(curtainsMessage);
        }else if(messageType instanceof SetupMessage){
            SetupMessage setupMessage = (SetupMessage)message;
            officeStand.setupDevice(setupMessage);
        }else{
            log.warn("Incoming message is not valid: {}", messageType.getTypeName());
        }
    }

}
