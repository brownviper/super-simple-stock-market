package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public final class TradeItemsRepository {


    private LinkedBlockingQueue<TradeItem> collection = new LinkedBlockingQueue<>();

    TradeItem addTradeItem(
            StockItemBuilder.StockItem stockItem,
            StockMarketService.BuyOrSell buyOrSell,
            BigDecimal price,
            Integer quantity,
            Instant timeStamp) {

        TradeItem tradeItem = new TradeItem(stockItem, buyOrSell, price, quantity, timeStamp);
        collection.add(tradeItem);

        return tradeItem;
    }

    public Stream<TradeItem> getRepositoryAsStream() {
        final HashSet<TradeItem> data = new HashSet<>(collection);
        return data.stream();
    }

    public Boolean contains(TradeItem tradeItem) {

        return collection.contains(tradeItem);
    }
}
