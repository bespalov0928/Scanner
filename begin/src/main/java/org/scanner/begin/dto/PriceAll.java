package org.scanner.begin.dto;

import lombok.Data;

@Data
public class PriceAll {
    String symbol;
    Double priceBybit = 0.0;
    Double priceOkx = 0.0;
    Double priceBitget = 0.0;
    Double priceBingx = 0.0;
    Double priceKucoin = 0.0;
    Double priceHuobi = 0.0;
    Double priceMexc = 0.0;
    Double priceCoinW = 0.0;
    Double priceGate = 0.0;
}
