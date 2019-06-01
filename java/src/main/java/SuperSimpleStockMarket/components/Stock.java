package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Stock {
    private final BigDecimal lastDividend;

    public Stock(BigDecimal lastDividend) {
        this.lastDividend = lastDividend;
    }

    public BigDecimal dividendYield(BigDecimal price) throws RuntimeException {

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("price should be greater than zero");
        }

        MathContext ROUNDING = new MathContext(30, RoundingMode.HALF_EVEN);

        BigDecimal result = this.lastDividend.divide(price, ROUNDING).stripTrailingZeros();

        return result.setScale(5, BigDecimal.ROUND_HALF_EVEN);
    }
}