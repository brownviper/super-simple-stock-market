package SuperSimpleStockMarket.components;

public final class StockMarketService {

    private final TradeItemsRepository repository;

    StockMarketService(TradeItemsRepository repository) {
        this.repository = repository;
    }

    TradeItem processStockItem(BuyOrSell buyOrSell, StockItemBuilder.StockItem stockItem) {
        return null;
    }

    public enum BuyOrSell {
        BUY,
        SELL
    }

}
