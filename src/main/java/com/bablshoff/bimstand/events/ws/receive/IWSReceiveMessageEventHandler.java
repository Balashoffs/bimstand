package com.bablshoff.bimstand.events.ws.receive;

public interface IWSReceiveMessageEventHandler {
    void addActionListener(IWSReceiveMessageEvent handler);
    void removeActionListener(IWSReceiveMessageEvent handler);
    void receive(String message);

}
