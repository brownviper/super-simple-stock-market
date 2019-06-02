package SuperSimpleStockMarket.components;

import java.math.BigDecimal;

public final class StockMarketService {

    private final TradeItemsRepository repository;

    StockMarketService(TradeItemsRepository repository) {
        this.repository = repository;
    }

    TradeItem processStockItem(StockItemBuilder.StockItem stockItem,
                               BuyOrSell buyOrSell,
                               BigDecimal price,
                               Integer quantity) {

        return repository.addTradeItem(stockItem, buyOrSell, price, quantity);
    }

    public enum BuyOrSell {
        BUY,
        SELL
    }

}
