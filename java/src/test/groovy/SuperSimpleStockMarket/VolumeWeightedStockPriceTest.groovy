package SuperSimpleStockMarket

import SuperSimpleStockMarket.components.StockItemBuilder
import SuperSimpleStockMarket.components.StockMarketService
import SuperSimpleStockMarket.components.TradeItemsRepository
import spock.lang.Shared
import spock.lang.Title
import spock.lang.Unroll

import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.COMMON
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.PREFERRED

@Title("Tests for volume weighted stock price calculation")
class VolumeWeightedStockPriceTest extends CommonSpecification {

    @Shared
    private StockItemBuilder.StockItem referenceStockItem

    @Unroll
    def "Calculate volume weighted stock price for stockSymbol = [#stockSymbol], and timeInterval=[#timeInterval]"(
            stockSymbol, stockType, lastDividend, fixedDividend, parValue, timeInterval, price, quantity, expected) {

        setup:
        TradeItemsRepository repository = new TradeItemsRepository()
        StockMarketService service = new StockMarketService(repository)
        referenceStockItem = createStockItem(stockSymbol, stockType, lastDividend, fixedDividend, parValue)
        populateStockMarketWithData(service, BigDecimal.valueOf(price), Integer.valueOf(quantity))

        when:
        BigDecimal actual = service.calculateVolumeWeightedStockPriceFor(referenceStockItem, timeInterval)

        then:
        expected == actual

        where:
        stockSymbol | stockType | lastDividend | fixedDividend | parValue | timeInterval | price | quantity | expected
            "TEA"   | COMMON    | 10.0         | null          | null     | 15           | 50    | 4        | 50
            "TEA"   | COMMON    | 10.0         | null          | null     | 5            | 100   | 50       | 100
            "GIN"   | PREFERRED | null         | 10.0          | 5.0      | 10           | 60    | 200      | 0
    }

}
