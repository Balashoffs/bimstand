package com.bablshoff.bimstand.message;

import com.bablshoff.bimstand.events.devices.send.DeviceSendMessageEventHandler;
import com.bablshoff.bimstand.events.devices.send.IDeviceSendMessageEvent;
import com.bablshoff.bimstand.events.ws.receive.IWSReceiveMessageEvent;
import com.bablshoff.bimstand.events.ws.send.WSSendMessageEventHandler;
import com.bablshoff.bimstand.model.message.LightingMessage;
import com.bablshoff.bimstand.model.message.OpcMessage;
import com.bablshoff.bimstand.model.message.SetupMessage;
import com.bablshoff.bimstand.model.message.curtains.CurtainsMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Type;

@Log4j2
public class MessageManager implements IWSReceiveMessageEvent, IDeviceSendMessageEvent {

    final JsonParser jsonParser = new JsonParser();
    //TODO MAybe need Concurrent Queue

    @Override
    public void receive(String message) {
        OpcMessage opcMessage = jsonParser.fromJsonC(message, OpcMessage.class);
        switch (opcMessage.getMessageType()) {
            case curtains_cs -> {
                CurtainsMessage curtainsMessage = jsonParser.fromJsonC(opcMessage.getBody(), CurtainsMessage.class);
                Type type = TypeToken.getParameterized(CurtainsMessage.class).getType();
                deviceHandler.send(type, curtainsMessage);

            }
            case lighting_cs -> {
                LightingMessage lightingMessage = jsonParser.fromJsonC(opcMessage.getBody(), LightingMessage.class);
                Type type = TypeToken.getParameterized(LightingMessage.class).getType();
                deviceHandler.send(type, lightingMessage);
            } case setup_device_cs -> {
                SetupMessage setupMessage = jsonParser.fromJsonC(opcMessage.getBody(), SetupMessage.class);
                Type type = TypeToken.getParameterized(SetupMessage.class).getType();
                deviceHandler.send(type, setupMessage);
            }

        }
    }

    private WSSendMessageEventHandler wsEventHandler;

    public void addEventHandler(WSSendMessageEventHandler handler) {
        wsEventHandler = handler;
    }

    public void removeEventHandler(WSSendMessageEventHandler handler) {
        wsEventHandler = handler;
    }

    private DeviceSendMessageEventHandler deviceHandler;

    public void addEventHandler(DeviceSendMessageEventHandler handler) {
        deviceHandler = handler;
    }

    public void removeEventHandler(DeviceSendMessageEventHandler handler) {
        deviceHandler = handler;
    }

    @Override
    public <T> void send(Type messageType, T message) {
        String jsonMessage= jsonParser.toJsonT(message, messageType);
        wsEventHandler.send(jsonMessage);
    }


    static class JsonParser {
        private final Gson gson = new Gson();

        <T> T fromJsonC(String json, Class<?> mainClass, Class<?>... subClass) {
            Type type = TypeToken.getParameterized(mainClass, subClass).getType();
            return gson.fromJson(json, type);
        }

        <T> String toJsonC(T object, Class<?> mainClass, Class<?>... subClass) {
            Type type = TypeToken.getParameterized(mainClass, subClass).getType();
            return gson.toJson(object, type);
        }

        <T> T fromJsonT(String json, Type type ) {
            return gson.fromJson(json, type);
        }

        <T> String toJsonT(T object, Type type) {
            return gson.toJson(object, type);
        }
    }
}


