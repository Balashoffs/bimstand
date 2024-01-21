package com.balashoff.bimstand.model.message;

import com.balashoff.bimstand.model.config.CurtainsConfig;
import com.balashoff.bimstand.model.config.LightingConfig;
import com.balashoff.bimstand.model.config.StandButtonConfig;
import com.balashoff.bimstand.model.config.StandStatusConfig;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SetupMessageTest {

    Gson gson = new Gson();

    @Test
    void generateJsonTest() throws IOException {

        List<CurtainsConfig> curtains = fillCurtains();
        List<LightingConfig> lightingConfigs = fillLightings();
        Map<String, List<StandButtonConfig>> buttons = fillButtons();
        StandStatusConfig standStatusConfig = fillStatusLed();
        SetupMessage setupMessage = SetupMessage
                .builder()
                .standStatusConfig(standStatusConfig)
                .curtainsConfigSet(curtains)
                .lightingConfigSet(lightingConfigs)
                .standButtonsSet(buttons)
                .deviceType(new String[]{"curtains", "lighting"})
                .build();

        String json = gson.toJson(setupMessage);
        Files.writeString(Path.of("setupMessage.json"), json);
    }

    @Test
    void readJsonTest() throws IOException {
       String json =  Files.readString(Path.of("setupMessage.json"));
        SetupMessage setupMessage =  gson.fromJson(json, SetupMessage.class);
        System.out.println(setupMessage.getSetupVersion());
    }

    private Map<String, List<StandButtonConfig>> fillButtons() {
        Map<String, List<StandButtonConfig>> buttons = new HashMap<>() {{
            put("curtains", new ArrayList<>());
            put("lighting", new ArrayList<>());
        }};

        StandButtonConfig standButtonConfigC1D = StandButtonConfig
                .builder()
                .id("curtains_1_down")
                .name("curtains_1_down")
                .type("down")
                .moduleType("curtains")
                .moduleId("curtain1")
                .pin(8)
                .debounce(10000)
                .inverted(true)
                .build();
        StandButtonConfig standButtonConfigC1U = StandButtonConfig
                .builder()
                .id("curtains_1_up")
                .name("curtains_1_up")
                .type("up")
                .moduleType("curtains")
                .moduleId("curtain1")
                .pin(7)
                .debounce(10000)
                .inverted(true)
                .build();

        StandButtonConfig standButtonConfigC2D = StandButtonConfig
                .builder()
                .id("curtains_2_down")
                .name("curtains_2_down")
                .type("down")
                .moduleType("curtains")
                .moduleId("curtain2")
                .pin(5)
                .debounce(10000)
                .inverted(true)
                .build();
        StandButtonConfig standButtonConfigC2U = StandButtonConfig
                .builder()
                .id("curtains_2_up")
                .name("curtains_2_up")
                .moduleType("curtains")
                .moduleId("curtain2")
                .type("up")
                .pin(6)
                .debounce(10000)
                .inverted(true)
                .build();

        buttons.get("curtains").addAll(List.of(standButtonConfigC1D, standButtonConfigC1U, standButtonConfigC2D, standButtonConfigC2U));

        StandButtonConfig standButtonConfigL1 = StandButtonConfig
                .builder()
                .id("led_1")
                .name("led_1")
                .type("switch")
                .moduleType("lighting")
                .moduleId("led1")
                .pin(26)
                .debounce(10000)
                .inverted(true)
                .build();
        StandButtonConfig standButtonConfigL2 = StandButtonConfig
                .builder()
                .id("led_2")
                .name("led_2")
                .type("switch")
                .moduleType("lighting")
                .moduleId("led2")
                .pin(16)
                .debounce(10000)
                .inverted(true)
                .build();


        buttons.get("lighting").addAll(List.of(standButtonConfigL1, standButtonConfigL2));


        return buttons;
    }

    private StandStatusConfig fillStatusLed() {
        return StandStatusConfig
                .builder()
                .pin(19)
                .id("status")
                .name("status")
                .build();
    }

    private List<CurtainsConfig> fillCurtains() {
        int[][] steps = new int[][]{
                {3, 0},
                {0},
                {0, 1},
                {1},
                {1, 2},
                {2},
                {3, 2},
                {3},
        };
        CurtainsConfig curtainsConfig1 = CurtainsConfig
                .builder()
                .id("curtain1")
                .name("curtain1")
                .type("curtains")
                .steps(steps).pins(new int[]{4, 17, 27, 22})
                .maxTurnCount(10)
                .pulseTime(1)
                .turnQnt(50)
                .maxTurnCount(10)
                .build();

        CurtainsConfig curtainsConfig2 = CurtainsConfig
                .builder()
                .id("curtain2")
                .name("curtain2")
                .type("curtains")
                .steps(steps).pins(new int[]{18, 23, 24, 25})
                .maxTurnCount(10)
                .pulseTime(1)
                .turnQnt(50)
                .maxTurnCount(10)
                .build();
        return List.of(curtainsConfig1, curtainsConfig2);
    }

    public List<LightingConfig> fillLightings() {
        LightingConfig lightingConfig1 = LightingConfig.builder().id("led1").name("led1").type("lighting").pin(12).build();
        LightingConfig lightingConfig2 = LightingConfig.builder().id("led2").name("led2").type("lighting").pin(12).build();

        return List.of(lightingConfig1, lightingConfig2);
    }
}