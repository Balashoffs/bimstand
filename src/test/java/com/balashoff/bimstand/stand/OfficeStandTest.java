package com.balashoff.bimstand.stand;

import com.balashoff.bimstand.helpers.CrowPiPlatform;
import com.pi4j.context.Context;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OfficeStandTest {
    OfficeStand officeStand;
    Context context;
    @BeforeEach
    void setUp() {
        context = CrowPiPlatform.buildNewContext();
        officeStand = new OfficeStand(context);
    }

    @AfterEach
    void tearDown() {
        context.shutdown();
    }

    @Test
    void setupDeviceFromJson() throws IOException {
        String json = Files.readString(Path.of("setupMessage.json"));
        officeStand.setupDeviceFromJson(json, iDeviceMessage -> {});
    }
}