package SuperSimpleStockMarket

import spock.lang.Specification
import spock.lang.Shared
import spock.lang.Title
import spock.lang.Unroll

import java.time.Duration
import java.time.Instant

import SuperSimpleStockMarket.components.StockItemBuilder
import SuperSimpleStockMarket.components.StockMarketService
import SuperSimpleStockMarket.components.TradeItemsRepository
import static SuperSimpleStockMarket.components.StockMarketService.BuyOrSell.BUY
import static SuperSimpleStockMarket.components.StockMarketService.BuyOrSell.SELL
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.COMMON
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.PREFERRED

@Title("Tests for volume weighted stock price calculation")
class VolumeWeightedStockPriceTest extends Specification {

    @Shared
    private StockItemBuilder.StockItem referenceStockItem

    def createStockItem(
            stockSymbol,
            stockType,
            lastDividend,
            fixedDividend,
            parValue
    ) {
        StockItemBuilder builder = StockItemBuilder.getBuilder()
        builder.addStockSymbol(stockSymbol)
        builder.addStockType(stockType)
        builder.addLastDividend(lastDividend)
        builder.addFixedDividend(fixedDividend)
        builder.addParValue(parValue)

        return builder.buildStockItem()
    }

    def populateStockMarketWithData(
            StockMarketService service,
            BigDecimal price,
            Integer quantity
    ) {
        // add POP
        service.processStockItem(
                createStockItem("TEA", COMMON, 0, null, 100),
                BUY, price, quantity, Instant.now())        // add TEA
        service.processStockItem(
                createStockItem("TEA", COMMON, 0, null, 100),
                SELL, price, quantity, Instant.now().minus(Duration.ofMinutes(10)))
        service.processStockItem(
                createStockItem("TEA", COMMON, 0, null, 100),
                SELL, price, quantity, Instant.now().minus(Duration.ofMinutes(17)))

        // add POP
        service.processStockItem(
                createStockItem("POP", COMMON, 8, null, 100),
                BUY, price, quantity, Instant.now().minus(Duration.ofMinutes(20)))        // add TEA

        // add ALE
        service.processStockItem(
                createStockItem("ALE", COMMON, 23, null, 60),
                BUY, price, quantity, Instant.now().minus(Duration.ofMinutes(10)))        // add TEA

        // add GIN
        service.processStockItem(
                createStockItem("ALE", PREFERRED, 8, 2, 100),
                BUY, price, quantity, Instant.now().minus(Duration.ofMinutes(12)))

        // add JOE
        service.processStockItem(
                createStockItem("ALE", COMMON, 13, null, 250),
                BUY, price, quantity, Instant.now().minus(Duration.ofMinutes(12)))
    }

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
