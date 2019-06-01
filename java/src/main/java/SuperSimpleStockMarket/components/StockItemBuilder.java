package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class StockItemBuilder {

    private StockType stockType = null;
    private BigDecimal lastDividend = null;
    private BigDecimal fixedDividend = null;
    private BigDecimal parValue = null;

    private StockItemBuilder() {}

    private static final StockItemBuilder builder = new StockItemBuilder();

    public static StockItemBuilder getBuilder() {
        return builder;
    }

    public void addStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public void addLastDividend(BigDecimal lastDividend) {
        this.lastDividend = lastDividend;
    }

    public void addFixedDividend(BigDecimal fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public void addParValue(BigDecimal parValue) {
        this.parValue = parValue;
    }

    private void reset() {
        stockType = null;
        lastDividend = null;
        fixedDividend = null;
        parValue = null;
    }

    public StockItem buildStockItem() {
        StockItem stock = null;

        if (stockType == null) {
            throw new RuntimeException("error - stockType must be specified");
        }

        if (this.stockType == StockType.PREFERRED) {
            stock = new PreferredStockItem(fixedDividend, parValue);
        }

        if (this.stockType == StockType.COMMON) {
            stock = new CommonStockItem(lastDividend);
        }

        reset();

        return stock;
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

    public enum StockType {
        COMMON,
        PREFERRED
    }

}
