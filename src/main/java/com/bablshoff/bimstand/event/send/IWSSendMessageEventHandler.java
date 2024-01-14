package com.bablshoff.bimstand.event.send;

public interface IWSSendMessageEventHandler {
    void addActionListener(IWSSendMessageEvent handler);

    void removeActionListener(IWSSendMessageEvent handler);

    void send(String message);
}
