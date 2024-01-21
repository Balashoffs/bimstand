package com.bablshoff.bimstand.model.message;

import com.bablshoff.bimstand.model.device.Curtains;
import com.bablshoff.bimstand.model.device.Lighting;
import com.bablshoff.bimstand.model.device.StandButton;
import com.bablshoff.bimstand.model.device.StandStatusLed;
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

        List<Curtains> curtains = fillCurtains();
        List<Lighting> lightings = fillLightings();
        Map<String, List<StandButton>> buttons = fillButtons();
        StandStatusLed standStatusLed = fillStatusLed();
        SetupMessage setupMessage = SetupMessage
                .builder()
                .standStatusLed(standStatusLed)
                .curtainsSet(curtains)
                .lightingSet(lightings)
                .standButtonsSet(buttons)
                .deviceType(new String[]{"curtains", "lighting"})
                .build();

        String json = gson.toJson(setupMessage);
        Files.writeString(Path.of("setupMessage.json"), json);
    }

    private Map<String, List<StandButton>> fillButtons() {
        Map<String, List<StandButton>> buttons = new HashMap<>() {{
            put("curtains", new ArrayList<>());
            put("lighting", new ArrayList<>());
        }};

        StandButton standButtonC1D = StandButton
                .builder()
                .id("curtains_1_down")
                .name("curtains_1_down")
                .type("down")
                .pin(8)
                .debounce(10000)
                .inverted(true)
                .build();
        StandButton standButtonC1U = StandButton
                .builder()
                .id("curtains_1_up")
                .name("curtains_1_up")
                .type("up")
                .pin(7)
                .debounce(10000)
                .inverted(true)
                .build();

        StandButton standButtonC2D = StandButton
                .builder()
                .id("curtains_2_down")
                .name("curtains_2_down")
                .type("down")
                .pin(5)
                .debounce(10000)
                .inverted(true)
                .build();
        StandButton standButtonC2U = StandButton
                .builder()
                .id("curtains_2_up")
                .name("curtains_2_up")
                .type("up")
                .pin(6)
                .debounce(10000)
                .inverted(true)
                .build();

        buttons.get("curtains").addAll(List.of(standButtonC1D, standButtonC1U, standButtonC2D, standButtonC2U));

        StandButton standButtonL1 = StandButton
                .builder()
                .id("led_1")
                .name("led_1")
                .type("switch")
                .pin(26)
                .debounce(10000)
                .inverted(true)
                .build();
        StandButton standButtonL2 = StandButton
                .builder()
                .id("led_2")
                .name("led_2")
                .type("switch")
                .pin(16)
                .debounce(10000)
                .inverted(true)
                .build();


        buttons.get("lighting").addAll(List.of(standButtonL1, standButtonL2));


        return buttons;
    }

    private StandStatusLed fillStatusLed() {
        return StandStatusLed
                .builder()
                .pin(19)
                .id("status")
                .name("status")
                .build();
    }

    private List<Curtains> fillCurtains() {
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
        Curtains curtains1 = Curtains
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

        Curtains curtains2 = Curtains
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
        return List.of(curtains1, curtains2);
    }

    public List<Lighting> fillLightings() {
        Lighting lighting1 = Lighting.builder().id("led1").name("led1").type("lighting").pin(12).build();
        Lighting lighting2 = Lighting.builder().id("led2").name("led2").type("lighting").pin(12).build();

        return List.of(lighting1, lighting2);
    }
}