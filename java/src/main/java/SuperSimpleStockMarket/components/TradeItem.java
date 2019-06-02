package SuperSimpleStockMarket.components;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public final class TradeItem {

    private final StockItemBuilder.StockItem stockItem;
    private final StockMarketService.BuyOrSell buyOrSell;
    private final BigDecimal price;
    private final Integer quantity;

    TradeItem (StockItemBuilder.StockItem stockItem, StockMarketService.BuyOrSell buyOrSell, BigDecimal price, Integer quantity) {
        this.stockItem = stockItem;
        this.buyOrSell = buyOrSell;
        this.price = price;
        this.quantity = quantity;
    }
}
