package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class StockItemFactory {

    private StockItemFactory() {}

    private static final StockItemFactory factory = new StockItemFactory();

    public static StockItemFactory getFactory() {
        return factory;
    }

    public CommonStockItem createCommonStockItem(BigDecimal lastDividend) {
        return new CommonStockItem(lastDividend);
    }

    public StockItem createPreferredStockItem(BigDecimal fixedDividend, BigDecimal parValue) {
        return new PreferredStockItem(fixedDividend, parValue);
    }

    public abstract class StockItem {
        public final MathContext ROUNDING = new MathContext(30, RoundingMode.HALF_EVEN);
        public abstract BigDecimal dividendYield(BigDecimal price);
    }

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

            BigDecimal result = this.fixedDividend
                    .multiply(this.parValue, ROUNDING)
                    .divide(price, ROUNDING)
                    .stripTrailingZeros();

            return result.setScale(5, BigDecimal.ROUND_HALF_EVEN);

        }
    }

    public class CommonStockItem extends StockItem {
        private final BigDecimal lastDividend;

        public CommonStockItem(BigDecimal lastDividend) {
            this.lastDividend = lastDividend;
        }

        @Override
        public BigDecimal dividendYield(BigDecimal price) throws RuntimeException {

            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("price should be greater than zero");
            }

            BigDecimal result = this.lastDividend.divide(price, ROUNDING).stripTrailingZeros();

            return result.setScale(5, BigDecimal.ROUND_HALF_EVEN);
        }
    }

}
