package com.balashoff.bimstand.model.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class CurtainsConfig {
    public  int[] pins;
    public  int[][] steps;
    public  long pulseTime;
    public   String name;
    public  String id;
    public  int maxTurnCount;
    public  int turnQnt;
    public  String type;
}
