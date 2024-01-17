package com.bablshoff.bimstand.events;

import com.bablshoff.bimstand.events.devices.receive.DeviceReceiveMessageEventHandler;
import com.bablshoff.bimstand.events.devices.send.DeviceSendMessageEventHandler;
import com.bablshoff.bimstand.events.ws.receive.WSReceiveMessageEventHandler;
import com.bablshoff.bimstand.events.ws.send.WSSendMessageEventHandler;
import lombok.Getter;

@Getter
public class EventManager {
    private static EventManager _instance;
    public static EventManager getInstance(){
        if(_instance == null){
            _instance = new EventManager();
        }

        return _instance;
    }

    final WSReceiveMessageEventHandler wsReceiveMessageEventHandler = new WSReceiveMessageEventHandler();
    final WSSendMessageEventHandler wsSendMessageEventHandler = new WSSendMessageEventHandler();
    final DeviceReceiveMessageEventHandler deviceReceiveMessageEventHandler = new  DeviceReceiveMessageEventHandler();
    final DeviceSendMessageEventHandler deviceSendMessageEventHandler = new  DeviceSendMessageEventHandler();

    private EventManager(){}

}
