package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class StockPreferred {
    private final BigDecimal fixedDividend;
    private final BigDecimal parValue;

    public StockPreferred(BigDecimal fixedDividend, BigDecimal parValue) {
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }
}
