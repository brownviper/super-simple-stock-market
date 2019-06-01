package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class PreferredStockItem extends StockItem {

    private final BigDecimal fixedDividend;
    private final BigDecimal parValue;

    public PreferredStockItem(BigDecimal fixedDividend, BigDecimal parValue) {
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    @Override
    public BigDecimal dividendYield(BigDecimal price) {

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("price should be greater than zero");
        }

        MathContext ROUNDING = new MathContext(30, RoundingMode.HALF_EVEN);

        BigDecimal result = this.fixedDividend
                .multiply(this.parValue, ROUNDING)
                .divide(price, ROUNDING)
                .stripTrailingZeros();

        return result.setScale(5, BigDecimal.ROUND_HALF_EVEN);

    }
}
