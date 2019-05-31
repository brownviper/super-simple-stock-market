package SuperSimpleStockMarket.components;

import java.math.BigDecimal;

public class Stock {
    private final BigDecimal lastDividend;
    private final BigDecimal parValue;

    public Stock(BigDecimal lastDividend, BigDecimal parValue) {
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }
}