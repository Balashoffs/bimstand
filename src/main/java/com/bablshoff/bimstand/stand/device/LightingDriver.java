package com.bablshoff.bimstand.stand.device;
import com.bablshoff.bimstand.model.lighting.LightingStatus;

import java.util.function.Consumer;

public class LightingDriver implements IDriver{
    public void control(boolean isTurn, Consumer<LightingStatus> statusConsumer) {
    }
}
