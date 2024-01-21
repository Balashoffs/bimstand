package com.bablshoff.bimstand.stand.device;

import com.bablshoff.bimstand.components.ButtonComponent;
import com.bablshoff.bimstand.components.StepMotorComponent;
import com.bablshoff.bimstand.components.definitions.Button;
import com.bablshoff.bimstand.model.message.curtains.CurtainsStatus;
import com.pi4j.context.Context;


import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class CurtainsDriver implements IDriver {
    private final StepMotorComponent stepMotorComponent;
    private final Map<String, ButtonComponent> buttons;
    private final String id;
    private final int maxStep;
    private final String name;

    private int pos = 0;

    public CurtainsDriver(String id, String name, int maxStep, Context pi4j) {
        this.id = id;
        this.name = name;
        this.maxStep = maxStep;
        stepMotorComponent = new StepMotorComponent(pi4j);
        buttons = new HashMap<>();
        setupButtons(buttons, pi4j);
    }

    private void setupButtons(Map<String, ButtonComponent> buttons, Context pi4j) {
//        ButtonComponent up = new ButtonComponent(pi4j, Button.UP);
//        buttons.put(Button.UP, up);
//        up.onDown(() -> {});
//        ButtonComponent down = new ButtonComponent(pi4j, Button.UP);
//        down.onDown(() -> {
//
//        });
//        buttons.put(Button.DOWN, down);

    }

    public void control(boolean isOpen, BiConsumer<CurtainsStatus, Integer> statusConsumer) {

    }
}


