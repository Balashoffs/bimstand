package com.bablshoff.bimstand.stand;

import com.bablshoff.bimstand.event.receive.IWSReceiveMessageEvent;
import com.bablshoff.bimstand.event.send.WSSendMessageEventHandler;

public class StandManager implements IWSReceiveMessageEvent {
    @Override
    public void receive(String message) {

    }

    private WSSendMessageEventHandler eventHandler;

    public void addEventHandler(WSSendMessageEventHandler handler){
        eventHandler = handler;
    }

    public void removeEventHandler(WSSendMessageEventHandler handler){
        eventHandler = handler;
    }

    public void start(){

    }
}
