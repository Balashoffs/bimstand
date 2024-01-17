open module com.balashoff.bimstand {
    // Module Exports
    exports com.bablshoff.bimstand.components;
    exports com.bablshoff.bimstand.components.definitions;
    exports com.bablshoff.bimstand.components.events;
    exports com.bablshoff.bimstand.components.exceptions;
    exports com.bablshoff.bimstand.components.helpers;
    exports com.bablshoff.bimstand.components.internal;
    exports com.bablshoff.bimstand.components.internal.rfid;
    exports com.bablshoff.bimstand.helpers;
    exports com.bablshoff.bimstand.events.ws.send;
    exports com.bablshoff.bimstand.model;
    exports com.bablshoff.bimstand.model.curtains;


    // Pi4J Modules
    requires com.pi4j;
    requires com.pi4j.library.pigpio;
    requires com.pi4j.plugin.pigpio;
    requires com.pi4j.plugin.raspberrypi;
    uses com.pi4j.extension.Extension;
    uses com.pi4j.provider.Provider;

    // Logging
    requires java.logging;


    // PicoCLI Modules
    requires info.picocli;

    // AWT
    requires java.desktop;

    requires org.java_websocket;
    requires javax.websocket.api;
    requires lombok;
    requires com.google.gson;
}
