package org.scanner.begin.checkCoin;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArrayReplaceValueCoin {
    private List<ReplaceValueCoin> list = new ArrayList<ReplaceValueCoin>();

    public ArrayReplaceValueCoin() {
        this.list.add(new ReplaceValueCoin("priceMexcText", "ALTLAYERUSDT", "ALTUSDT"));
        this.list.add(new ReplaceValueCoin("priceBingxText", "TRUMPSOLUSDT", "TRUMPUSDT"));
    }
}
