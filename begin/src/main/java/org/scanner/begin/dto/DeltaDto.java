package org.scanner.begin.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DeltaDto {
    private String exchange;
    private ArrayList<Delta> listDelta;
}
