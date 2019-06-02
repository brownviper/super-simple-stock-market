package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.concurrent.LinkedBlockingQueue;

public final class TradeItemsRepository {

    private static LinkedBlockingQueue<TradeItem> collection = new LinkedBlockingQueue<>();

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

    public Boolean contains(TradeItem tradeItem) {

        return collection.contains(tradeItem);
    }
}
