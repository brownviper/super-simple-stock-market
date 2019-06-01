package SuperSimpleStockMarket.components;

import java.math.BigDecimal;

public abstract class StockItem {
    public abstract BigDecimal dividendYield(BigDecimal price);
}
