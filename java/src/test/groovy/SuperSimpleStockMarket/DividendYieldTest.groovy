package SuperSimpleStockMarket

import spock.lang.Specification

import spock.lang.Title
import spock.lang.Unroll

import SuperSimpleStockMarket.components.StockItemBuilder
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.COMMON
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.PREFERRED


@Title("Tests for calculating Divided Yield")
class DividendYieldTest extends Specification {

    @Unroll
    def "Calculate dividend yield for stockSymbol = [#stockSymbol], stockType = [#stockType], lastDividend =[#lastDividend], fixedDividend=[#fixedDividend], parValue=[#parValue] and price=[#price]"(
            stockSymbol, stockType, lastDividend, fixedDividend, parValue, price, expectedDividendYield
    ) {
        setup:
        Class exception = null
        BigDecimal actual = null
        StockItemBuilder builder = StockItemBuilder.getBuilder()
        builder.addStockSymbol(stockSymbol)
        builder.addStockType(stockType)
        builder.addLastDividend(lastDividend)
        builder.addFixedDividend(fixedDividend)
        builder.addParValue(parValue)

        when:
        try{
            StockItemBuilder.StockItem stockItem = builder.buildStockItem()
            actual = stockItem.dividendYield(price)
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
        stockSymbol | stockType | lastDividend | fixedDividend | parValue | price | expectedDividendYield
            "GIN"   | PREFERRED | null         | 10.0          | 5.0      | 2.0   | 25.0
            "GIN"   | PREFERRED | null         | 10.0          | 5.0      | 0.0   | RuntimeException
            "TEA"   | COMMON    | 10.0         | null          | null     | 5.0   | 2.0
            "POP"   | COMMON    | 10.0         | null          | null     | 0.0   | RuntimeException
            "ALE"   | COMMON    | 10.0         | null          | null     | -5.0  | RuntimeException
            "JOE"   | null      | 10.0         | 10.0          | 5.0      | 2.0   | RuntimeException
    }
}
