package com.balashoff.bimstand.stand.component;

import com.balashoff.bimstand.model.config.StandStatusConfig;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;



public class BoardLedStatusComponent {
    private final DigitalOutput output;

    public BoardLedStatusComponent(Context context, StandStatusConfig config) {
        output = context.create(buildDigitalOutputConfig(context, config.getPin()));
    }

    public void turnOn(){
        output.high();
    }

    public void turnOff(){
        output.high();
    }

    protected DigitalOutputConfig buildDigitalOutputConfig(Context pi4j, int address) {
        return DigitalOutput.newConfigBuilder(pi4j)
                .id("BCM" + address)
                .name("BimStandStatus_" + address)
                .address(address)
                .initial(DigitalState.LOW)
                .shutdown(DigitalState.LOW)
                .build();
    }
}
