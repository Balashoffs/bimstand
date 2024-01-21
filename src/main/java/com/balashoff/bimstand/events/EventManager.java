package com.balashoff.bimstand.events;

import com.balashoff.bimstand.events.devices.receive.DeviceReceiveMessageEventHandler;
import com.balashoff.bimstand.events.devices.send.DeviceSendMessageEventHandler;
import com.balashoff.bimstand.events.ws.receive.WSReceiveMessageEventHandler;
import com.balashoff.bimstand.events.ws.send.WSSendMessageEventHandler;
import lombok.Getter;

@Getter
public class EventManager {
    private static EventManager _instance;

    public static EventManager getInstance() {
        if (_instance == null) {
            _instance = new EventManager();
        }

        return _instance;
    }

    private final WSReceiveMessageEventHandler wsReceiveMessageEventHandler;
    private final WSSendMessageEventHandler wsSendMessageEventHandler;
    private final DeviceReceiveMessageEventHandler deviceReceiveMessageEventHandler;
    private final DeviceSendMessageEventHandler deviceSendMessageEventHandler;

    private EventManager() {
        wsReceiveMessageEventHandler = new WSReceiveMessageEventHandler();
        wsSendMessageEventHandler = new WSSendMessageEventHandler();
        deviceReceiveMessageEventHandler = new DeviceReceiveMessageEventHandler();
        deviceSendMessageEventHandler = new DeviceSendMessageEventHandler();
    }

}
