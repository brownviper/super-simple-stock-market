package SuperSimpleStockMarket.components;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class StockMarketService {

    private final TradeItemsRepository repository;

    StockMarketService(TradeItemsRepository repository) {
        this.repository = repository;
    }

    public TradeItem processStockItem(StockItemBuilder.StockItem stockItem,
                               BuyOrSell buyOrSell,
                               BigDecimal price,
                               Integer quantity,
                               Instant timeStamp) {

        return repository.addTradeItem(stockItem, buyOrSell, price, quantity, timeStamp);
    }

    public BigDecimal calculateVolumeWeightedStockPriceFor(StockItemBuilder.StockItem stockItem, Integer timeInterval) {

        if (stockItem == null || timeInterval.compareTo(0) <= 0) {
            throw new RuntimeException("stockItem must be valid timeInterval must be > 0");
        }

        final Instant scanningWindow = Instant.now().minus(Duration.ofMinutes(timeInterval));
        final Stream<TradeItem> stream = repository.getRepositoryAsStream();
        AtomicInteger totalQuantity = new AtomicInteger(0);

        BigDecimal priceAndQuantitySum = stream.parallel()
                // get items outside intervals
                .filter(trade -> trade.getTimeStamp().isAfter(scanningWindow))
                // get items with same symbol
                .filter(trade -> trade.getStockItem().getStockSymbol().equals(stockItem.getStockSymbol()))
                // iterate over and multiply price * quantity
                .map(trade -> {
                    totalQuantity.addAndGet(trade.getQuantity());
                    return trade.getPrice().multiply(BigDecimal.valueOf(trade.getQuantity()));
                })
                // sum all the results, or zero if no items found
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        System.out.println("--> For " + stockItem.getStockSymbol() + " totalQuantity="+ totalQuantity.get());
        System.out.println("--> For " + stockItem.getStockSymbol() + " priceAndQuantitySum="+ priceAndQuantitySum.toPlainString());

        if (priceAndQuantitySum.compareTo(BigDecimal.ZERO) == 0) {
            // return here so we dont divide over zero
            return BigDecimal.ZERO;
        }

        BigDecimal volumeWeightedStockPrice = priceAndQuantitySum.divide(BigDecimal.valueOf(totalQuantity.get()), stockItem.getROUNDING());

        System.out.println("--> For " + stockItem.getStockSymbol() + " volumeWeightedStocPrice="+ volumeWeightedStockPrice.toPlainString());

        // return the result to 2 decimal places
        return volumeWeightedStockPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public enum BuyOrSell {
        BUY,
        SELL
    }

}
