package com.balashoff.bimstand.stand;

import com.balashoff.bimstand.events.devices.receive.IDeviceReceiveMessageEvent;
import com.balashoff.bimstand.events.devices.send.DeviceSendMessageEventHandler;
import com.balashoff.bimstand.model.message.IDeviceMessage;
import com.balashoff.bimstand.model.message.SetupMessage;
import com.balashoff.bimstand.model.message.curtains.CurtainsMessage;
import com.balashoff.bimstand.model.message.lighting.LightingMessage;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
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

    public void consumeMessageFromStandDevice(IDeviceMessage deviceMessage) {
        blockingQueue.add(deviceMessage);
    }

    public OfficeStandController(OfficeStand officeStand) {
        this.officeStand = officeStand;
    }

    @SneakyThrows
    public void setupControllerFromFile(String json) {
        officeStand.setupDeviceFromJson(json, this::consumeMessageFromStandDevice);
    }


    @Override
    public <T> void receive(Type messageType, T message) {
        String[] chunks = messageType.getTypeName().split("\\.");
        String className = chunks[chunks.length -1];
        log.debug("class name incoming message: {}", className);
//        if (className.equals("LightingMessage")) {
        if (message instanceof LightingMessage lightingMessage) {
            officeStand.controlLight(lightingMessage);
        } else if (message instanceof CurtainsMessage curtainsMessage) {
            officeStand.controlCurtains(curtainsMessage);
        } else if (message instanceof SetupMessage setupMessage) {
            officeStand.setupDevice(setupMessage, this::consumeMessageFromStandDevice);
        } else {
            log.warn("Incoming message is not valid: {}", messageType.getTypeName());
        }
    }

    @Override
    public void run() {
        isRunning.set(true);
        while (isRunning.get()) {
            try {
                IDeviceMessage deviceMessage = blockingQueue.take();
                Type type = TypeToken.getParameterized(deviceMessage.getClass()).getType();
                deviceEventHandler.send(type, deviceMessage);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start() {
        new Thread(this).start();
    }
}
