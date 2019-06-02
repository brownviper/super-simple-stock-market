package SuperSimpleStockMarket

import SuperSimpleStockMarket.components.StockItemBuilder
import SuperSimpleStockMarket.components.StockMarketService
import SuperSimpleStockMarket.components.TradeItem
import SuperSimpleStockMarket.components.TradeItemsRepository
import spock.lang.Title
import spock.lang.Unroll

import java.time.Instant

import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.COMMON
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.PREFERRED
import static SuperSimpleStockMarket.components.StockMarketService.BuyOrSell.BUY
import static SuperSimpleStockMarket.components.StockMarketService.BuyOrSell.SELL

@Title("Stock market trade items Buying/Selling services")
class StockItemServiceTest extends CommonSpecification {

    @Unroll
    def "StockMarketService #desc"(
         desc, stockSymbol, stockType, buyOrSell, price, quantity, lastDividend, parValue, fixedDividend
    ) {
        setup:
        StockItemBuilder.StockItem stockItem = createStockItem(stockSymbol, stockType, lastDividend, parValue, fixedDividend)
        TradeItemsRepository repository = new TradeItemsRepository()
        StockMarketService service = new StockMarketService(repository)

        when:
        TradeItem tradeItem = service.processStockItem(stockItem, buyOrSell, price, quantity, Instant.now())

        then:
        tradeItem
        assert(repository.contains(tradeItem) == Boolean.TRUE)

        where:
        desc                            | stockSymbol | stockType | buyOrSell | price  | quantity | lastDividend | parValue | fixedDividend
        'can buy common stock item'     | "JOE"       | COMMON    | BUY       | 100.0  | 4        | 10.0         | 60.0     | null
        'can buy preferred stock item'  | "GIN"       | PREFERRED | BUY       | 100.0  | 4        | 10.0         | 60.0     | 7.0
        'can sell common stock item'    | "JOE"       | COMMON    | SELL      | 100.0  | 4        | 10.0         | 60.0     | null
        'can sell preferred stock item' | "GIN"       | PREFERRED | SELL      | 100.0  | 4        | 10.0         | 60.0     | 7
    }
}
