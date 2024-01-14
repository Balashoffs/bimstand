package com.bablshoff.bimstand.applications;

import com.pi4j.context.Context;
import com.bablshoff.bimstand.Application;

public class ExampleApp implements Application {
    @Override
    public void execute(Context pi4j) {
        System.out.println("CrowPi with Pi4J rocks!");
    }
}
