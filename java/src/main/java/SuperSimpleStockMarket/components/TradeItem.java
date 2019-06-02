package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;

@Getter
public final class TradeItem {

    private final StockItemBuilder.StockItem stockItem;
    private final StockMarketService.BuyOrSell buyOrSell;
    private final BigDecimal price;
    private final Integer quantity;
    private final Instant timeStamp;

    TradeItem (StockItemBuilder.StockItem stockItem, StockMarketService.BuyOrSell buyOrSell, BigDecimal price, Integer quantity, Instant timeStamp) {
        this.stockItem = stockItem;
        this.buyOrSell = buyOrSell;
        this.price = price;
        this.quantity = quantity;
        this.timeStamp = timeStamp;
    }
}
