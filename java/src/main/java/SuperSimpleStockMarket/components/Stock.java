package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Stock {
    private final BigDecimal lastDividend;
    private final BigDecimal parValue;

    public Stock(BigDecimal lastDividend, BigDecimal parValue) {
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }

    public BigDecimal dividendYield(BigDecimal price) {
        MathContext ROUNDING = new MathContext(30, RoundingMode.HALF_EVEN);

        BigDecimal result = this.lastDividend.divide(price, ROUNDING).stripTrailingZeros();

        return result.setScale(5, BigDecimal.ROUND_HALF_EVEN);
    }
}