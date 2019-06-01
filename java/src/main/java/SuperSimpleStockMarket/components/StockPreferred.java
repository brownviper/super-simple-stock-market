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

    public BigDecimal dividendYield(BigDecimal price) {

        MathContext ROUNDING = new MathContext(30, RoundingMode.HALF_EVEN);

        BigDecimal result = this.fixedDividend
                .multiply(this.parValue, ROUNDING)
                .divide(price, ROUNDING)
                .stripTrailingZeros();

        return result.setScale(5, BigDecimal.ROUND_HALF_EVEN);

    }
}
