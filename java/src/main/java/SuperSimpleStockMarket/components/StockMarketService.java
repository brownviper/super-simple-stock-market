package SuperSimpleStockMarket.components;

import java.math.BigDecimal;

public final class StockMarketService {

    private final TradeItemsRepository repository;

    StockMarketService(TradeItemsRepository repository) {
        this.repository = repository;
    }

    TradeItem processStockItem(StockItemBuilder.StockItem stockItem, BuyOrSell buyOrSell, BigDecimal price, Integer quantity) {
        TradeItem tradeItem = new TradeItem(stockItem, buyOrSell, price, quantity);
        repository.addTradeItem(tradeItem);

        return tradeItem;
    }

    public enum BuyOrSell {
        BUY,
        SELL
    }

}
