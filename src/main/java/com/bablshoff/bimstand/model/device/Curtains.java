package com.bablshoff.bimstand.model.device;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class Curtains {
    public final int[] pins;
    public final int[][] steps;
    public final long pulseTime;
    public final  String name;
    public final String id;
    public final int maxTurnCount;
    public final int turnQnt;
    public final String type;
}
