package com.balashoff.bimstand.events.ws.send;

public interface IWSSendMessageEventHandler {
    void addActionListener(IWSSendMessageEvent handler);

    void removeActionListener(IWSSendMessageEvent handler);

    void send(String message);
}
