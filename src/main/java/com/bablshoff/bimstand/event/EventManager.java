package com.bablshoff.bimstand.event;

import com.bablshoff.bimstand.event.receive.WSReceiveMessageEventHandler;
import com.bablshoff.bimstand.event.send.WSSendMessageEventHandler;
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

    private EventManager(){}

}
