package SuperSimpleStockMarket

import spock.lang.Specification

import spock.lang.Title
import spock.lang.Unroll

import SuperSimpleStockMarket.components.StockItemBuilder
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.COMMON
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.PREFERRED


@Title("Tests for calculating P/E Ratio")
class peRatioTest extends Specification {

    @Unroll
    def "Calculate P/E ratio for stockSymbol = [#stockSymbol], stockType=[#stockType], lastDividend=[#lastDivided], price=[#price]" (
            stockSymbol, stockType, lastDividend, price, expectedDividendYield
    ) {
        setup:
        Class exception = null
        BigDecimal actual = null
        StockItemBuilder builder = StockItemBuilder.getBuilder()
        builder.addStockSymbol(stockSymbol)
        builder.addStockType(stockType)
        builder.addLastDividend(lastDividend)

        when:
        try{
            StockItemBuilder.StockItem stockItem = builder.buildStockItem()
            actual = stockItem.peRatio(price)
        } catch(RuntimeException e) {
            exception = e.class
        }

        then:
        if(expectedDividendYield instanceof Class) {
            assert(expectedDividendYield).equals(exception)
        } else {
            assert(actual.doubleValue() == BigDecimal.valueOf(expectedDividendYield).doubleValue())
        }

        where:
        stockSymbol | stockType | lastDividend | price | expectedDividendYield
            "GIN"   | PREFERRED | 10.0         | 30.0  | 3.0
            "GIN"   | PREFERRED | 0.0          | 30.0  | RuntimeException
            "JOE"   | COMMON    | 10.0         | 5     | 0.5
            "POP"   | COMMON    | 0.0          | 5     | RuntimeException
    }
}
