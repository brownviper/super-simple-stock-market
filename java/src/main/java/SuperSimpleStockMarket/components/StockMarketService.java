package SuperSimpleStockMarket.components;

public final class StockMarketService {

    private final TradeItemsRepository repository;

    StockMarketService(TradeItemsRepository repository) {
        this.repository = repository;
    }

    public enum BuyOrSell {
        BUY,
        SELL
    }

}
