package SuperSimpleStockMarket

import spock.lang.Specification

import spock.lang.Title
import spock.lang.Unroll

import SuperSimpleStockMarket.components.StockItemBuilder
import SuperSimpleStockMarket.components.TradeItemsRepository
import SuperSimpleStockMarket.components.StockMarketService
import SuperSimpleStockMarket.components.TradeItem
import static SuperSimpleStockMarket.components.StockMarketService.BuyOrSell.BUY
import static SuperSimpleStockMarket.components.StockMarketService.BuyOrSell.SELL
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.COMMON
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.PREFERRED


@Title("Stock market trade items Buying/Selling services")
class StockItemServiceTest extends Specification {

    def createStockItem(stockType, lastDividend, parValue, fixedDividend) {
        StockItemBuilder builder = StockItemBuilder.getBuilder()
        builder.addStockType(stockType)
        builder.addLastDividend(lastDividend)
        builder.addParValue(parValue)
        builder.addFixedDividend(fixedDividend)

        return builder.buildStockItem()
    }

    @Unroll
    def "StockMarketService #desc"(
         desc, stockType, buyOrSell, lastDividend, parValue, fixedDividend
    ) {
        given:
        StockItemBuilder.StockItem stockItem = createStockItem(stockType, lastDividend, parValue, fixedDividend)

        when:
        TradeItemsRepository repository = new TradeItemsRepository()
        StockMarketService service = new StockMarketService(repository)
        TradeItem tradeItem = service.processStockItem(buyOrSell, stockItem)

        then:
        assert(tradeItem).equals(null)

        where:
        desc                            | stockType | buyOrSell | lastDividend | parValue | fixedDividend
        'can buy common stock item'     | COMMON    | BUY       | 10.0         | 60.0     | null
    }
}
