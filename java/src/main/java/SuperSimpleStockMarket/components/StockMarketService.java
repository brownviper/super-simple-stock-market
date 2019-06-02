package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.time.Instant;

public final class StockMarketService {

    private final TradeItemsRepository repository;

    StockMarketService(TradeItemsRepository repository) {
        this.repository = repository;
    }

    TradeItem processStockItem(StockItemBuilder.StockItem stockItem,
                               BuyOrSell buyOrSell,
                               BigDecimal price,
                               Integer quantity) {

        return repository.addTradeItem(stockItem, buyOrSell, price, quantity, Instant.now());
    }

    public enum BuyOrSell {
        BUY,
        SELL
    }

}
