package SuperSimpleStockMarket

import spock.lang.Specification

import spock.lang.Title

import SuperSimpleStockMarket.components.StockItemBuilder
import SuperSimpleStockMarket.components.TradeItemsRepository
import SuperSimpleStockMarket.components.StockMarketService
import SuperSimpleStockMarket.components.TradeItem


@Title("Stock market trade items Buying/Selling services")
class StockItemServiceTest extends Specification {

    def "StockItemService selling stockItem"() {
        given:
        StockItemBuilder builder = StockItemBuilder.getBuilder()
        TradeItemsRepository repository = new TradeItemsRepository()
        StockMarketService service = new StockMarketService(repository)
    }
}
