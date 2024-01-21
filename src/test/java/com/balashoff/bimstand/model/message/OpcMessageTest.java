package com.balashoff.bimstand.model.message;

import com.balashoff.bimstand.model.message.curtains.CurtainsAction;
import com.balashoff.bimstand.model.message.curtains.CurtainsMessage;
import com.balashoff.bimstand.model.message.curtains.CurtainsStatus;
import com.balashoff.bimstand.model.message.lighting.LightingMessage;
import com.balashoff.bimstand.model.message.lighting.LightingStatus;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OpcMessageTest {
    Gson gson = new Gson();
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @SneakyThrows
    @Test
    void generateLightingMessageTest(){
        LightingMessage lightingMessage = LightingMessage.builder().deviceId("led1").deviceName("led1").deviceStatus(LightingStatus.on).build();
        String body = gson.toJson(lightingMessage);
        OpcMessage opcMessage = new OpcMessage(MessageType.lighting_cs, body);
        String message =  gson.toJson(opcMessage);
        Files.writeString(Path.of("lightingMessage.json"), message);
    }

    @SneakyThrows
    @Test
    void generateCurtainsMessageTest(){
        CurtainsMessage lightingMessage = CurtainsMessage.builder().deviceId("led1").deviceName("led1").deviceAction(CurtainsAction.open).deviceStatus(CurtainsStatus.closed).build();
        String body = gson.toJson(lightingMessage);
        OpcMessage opcMessage = new OpcMessage(MessageType.curtains_cs, body);
        String message =  gson.toJson(opcMessage);
        Files.writeString(Path.of("curtainsMessage.json"), message);
    }
}