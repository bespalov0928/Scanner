package org.scanner.begin.dto;

import lombok.Data;

@Data
public class Delta {
    public String exchange;
    public String symbol;
    public Double delta_value = 0.0;
    public Double bybit = 0.0;
    public Double okx = 0.0;
    public Double bitget = 0.0;
    public Double bingx = 0.0;
    public Double kucoin = 0.0;
    public Double huobi = 0.0;
    public Double mexc = 0.0;
    public Double coinw = 0.0;
    public Double gate = 0.0;
}
