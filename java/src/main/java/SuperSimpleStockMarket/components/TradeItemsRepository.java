package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.util.concurrent.LinkedBlockingQueue;

public final class TradeItemsRepository {

    private static LinkedBlockingQueue<TradeItem> collection = new LinkedBlockingQueue<>();

    TradeItem addTradeItem(
            StockItemBuilder.StockItem stockItem,
            StockMarketService.BuyOrSell buyOrSell,
            BigDecimal price,
            Integer quantity) {

        TradeItem tradeItem = new TradeItem(stockItem, buyOrSell, price, quantity);
        collection.add(tradeItem);

        return tradeItem;
    }
}
