package org.scanner.begin.checkCoin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplaceValueCoin {
    private String exchange;
    private String coinFrom;
    private String coinTo;
}
