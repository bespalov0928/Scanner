package org.scanner.begin.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class IndexDto {
    private String сoinPair;
    private ArrayList<PriceAll> listPrice;
}
