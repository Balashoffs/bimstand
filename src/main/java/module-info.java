open module com.balashoff.bimstand {
    // Module Exports
    exports com.balashoff.bimstand.components;
    exports com.balashoff.bimstand.components.definitions;
    exports com.balashoff.bimstand.components.events;
    exports com.balashoff.bimstand.components.exceptions;
    exports com.balashoff.bimstand.components.helpers;
    exports com.balashoff.bimstand.components.internal;
    exports com.balashoff.bimstand.components.internal.rfid;
    exports com.balashoff.bimstand.helpers;




    exports  com.balashoff.bimstand.ws;
    exports  com.balashoff.bimstand.stand;
    exports  com.balashoff.bimstand.stand.module;
    exports  com.balashoff.bimstand.model.message;
    exports com.balashoff.bimstand.model.message.lighting;
    exports com.balashoff.bimstand.model.message.curtains;
    exports  com.balashoff.bimstand.model.config;
    exports  com.balashoff.bimstand.message;
    exports com.balashoff.bimstand.events;
    exports com.balashoff.bimstand.events.devices.receive;
    exports com.balashoff.bimstand.events.devices.send;
    exports com.balashoff.bimstand.events.ws.send;
    exports com.balashoff.bimstand.events.ws.receive;


    // Pi4J Modules
    requires com.pi4j;
    requires com.pi4j.library.pigpio;
    requires com.pi4j.plugin.pigpio;
    requires com.pi4j.plugin.raspberrypi;
    uses com.pi4j.extension.Extension;
    uses com.pi4j.provider.Provider;

    // Logging
    requires java.logging;
    requires org.apache.logging.log4j;


    // PicoCLI Modules
    requires info.picocli;

    // AWT
    requires java.desktop;

    requires org.java_websocket;

    requires static lombok;
    requires com.google.gson;
}
