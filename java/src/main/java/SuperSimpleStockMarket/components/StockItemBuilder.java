package SuperSimpleStockMarket.components;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import lombok.Getter;

public final class StockItemBuilder {

    private String stockSymbol = null;
    private StockType stockType = null;
    private BigDecimal lastDividend = null;
    private BigDecimal fixedDividend = null;
    private BigDecimal parValue = null;

    private StockItemBuilder() {}

    private static final StockItemBuilder builder = new StockItemBuilder();

    public static StockItemBuilder getBuilder() {
        return builder;
    }

    public void addStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
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
            stock = new PreferredStockItem(stockSymbol, lastDividend, fixedDividend, parValue);
        }

        if (this.stockType == StockType.COMMON) {
            stock = new CommonStockItem(stockSymbol, lastDividend);
        }

        reset();

        return stock;
    }

    @Getter
    public abstract class StockItem {
        private final String stockSymbol;
        private final BigDecimal lastDividend;

        StockItem(String stockSymbol, BigDecimal lastDividend) {
            this.stockSymbol = stockSymbol;
            this.lastDividend = lastDividend;
        }

        public abstract BigDecimal dividendYield(BigDecimal price);

        public BigDecimal peRatio(BigDecimal price) {
            if (getLastDividend().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("lastDividend should be greater than zero");
            }

            BigDecimal result = price.divide(this.getLastDividend(), ROUNDING).stripTrailingZeros();
            return result.setScale(5, BigDecimal.ROUND_HALF_EVEN);
        }

        final MathContext ROUNDING = new MathContext(30, RoundingMode.HALF_EVEN);
    }

    public class PreferredStockItem extends StockItem {

        private final BigDecimal fixedDividend;
        private final BigDecimal parValue;

        PreferredStockItem(String stockSymbol, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue) {
            super(stockSymbol, lastDividend);
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

        CommonStockItem(String stockSymbol, BigDecimal lastDividend) {
            super(stockSymbol, lastDividend);
        }

        @Override
        public BigDecimal dividendYield(BigDecimal price) throws RuntimeException {

            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("price should be greater than zero");
            }

            BigDecimal result = this.getLastDividend().divide(price, ROUNDING).stripTrailingZeros();

            return result.setScale(5, BigDecimal.ROUND_HALF_EVEN);
        }
    }

    public enum StockType {
        COMMON,
        PREFERRED
    }

}
