package org.scanner.begin.dto;

import lombok.Data;

@Data
public class PriceAll {
    public String symbol;
    public Double priceBybit = 0.0;
    public Double priceOkx = 0.0;
    public Double priceBitget = 0.0;
    public Double priceBingx = 0.0;
    public Double priceKucoin = 0.0;
    public Double priceHuobi = 0.0;
    public Double priceMexc = 0.0;
    public Double priceCoinW = 0.0;
    public Double priceGate = 0.0;
}
