package com.balashoff.bimstand.ws;

import lombok.extern.log4j.Log4j2;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;

@Log4j2
public class StandWebSocketServer extends WebSocketServer {

    private final ArrayList<String> clientHandshakeAR = new ArrayList<>(1);


    public StandWebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        log.debug("'{}' connected to server!", webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
        if (clientHandshakeAR.isEmpty()) {
            clientHandshakeAR.add(clientHandshake.getResourceDescriptor());
        } else {
            log.warn("somebody want to connect to stand with params: {}", webSocket.getRemoteSocketAddress());
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        log.debug("i: {}, s: {}, b: {}, socket: {}", i, s, b, webSocket.getRemoteSocketAddress());
        clientHandshakeAR.clear();
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        log.error(e.getMessage());
        log.error(e.fillInStackTrace());
        log.warn("Server catch error!");
    }

    @Override
    public void onStart() {
        log.debug("Server started on {} port", getPort());
        setConnectionLostTimeout(100);
    }


}
