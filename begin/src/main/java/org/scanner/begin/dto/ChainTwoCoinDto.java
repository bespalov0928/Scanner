package org.scanner.begin.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ChainTwoCoinDto {
    private String сoinPair;
    private Double spred;
    private ArrayList<Delta> listChain_v2;
}
