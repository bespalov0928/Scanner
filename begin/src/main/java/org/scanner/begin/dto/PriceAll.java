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

    public String priceBybitText = "";
    public String priceOkxText = "";
    public String priceBitgetText = "";
    public String priceBingxText = "";
    public String priceKucoinText = "";
    public String priceHuobiText = "";
    public String priceMexcText = "";
    public String priceCoinWText = "";
    public String priceGateText = "";

    public void refillPriceDouble(){
        this.priceBybit = priceBybitText.isEmpty()?0.0:Double.valueOf(priceBybitText.replaceAll(",","."));
        this.priceOkx = priceOkxText.isEmpty()?0.0:Double.valueOf(priceOkxText.replaceAll(",","."));
        this.priceBitget = priceBitgetText.isEmpty()?0.0:Double.valueOf(priceBitgetText.replaceAll(",","."));
        this.priceBingx = priceBingxText.isEmpty()?0.0:Double.valueOf(priceBingxText.replaceAll(",","."));
        this.priceKucoin = priceKucoinText.isEmpty()?0.0:Double.valueOf(priceKucoinText.replaceAll(",","."));
        this.priceHuobi = priceHuobiText.isEmpty()?0.0:Double.valueOf(priceHuobiText.replaceAll(",","."));
        this.priceMexc = priceMexcText.isEmpty()?0.0:Double.valueOf(priceMexcText.replaceAll(",","."));
        this.priceCoinW = priceCoinWText.isEmpty()?0.0:Double.valueOf(priceCoinWText.replaceAll(",","."));
        this.priceGate = priceGateText.isEmpty()?0.0:Double.valueOf(priceGateText.replaceAll(",","."));
    }
}
