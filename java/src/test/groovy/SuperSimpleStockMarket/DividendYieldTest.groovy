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
    def "Calculate dividend yield for stockType = [#stockType], lastDividend =[#lastDividend], fixedDividend=[#fixedDividend], parValue=[#parValue] and price=[#price]"(
            stockType, lastDividend, fixedDividend, parValue, price, expectedDividendYield
    ) {
        setup:
        Class exception = null
        BigDecimal actual = null
        StockItemBuilder builder = StockItemBuilder.getBuilder()
        builder.addStockType(stockType)
        builder.addLastDividend(lastDividend)
        builder.addFixedDividend(fixedDividend)
        builder.addParValue(parValue)
        StockItemBuilder.StockItem stockItem = builder.buildStockItem()

        when:
        try{
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
        stockType | lastDividend | fixedDividend | parValue | price | expectedDividendYield
        PREFERRED | null         | 10.0          | 5.0      | 2.0   | 25.0
        PREFERRED | null         | 10.0          | 5.0      | 0.0   | RuntimeException
        COMMON    | 10.0         | null          | null     | 5.0   | 2.0
        COMMON    | 10.0         | null          | null     | 0.0   | RuntimeException
        COMMON    | 10.0         | null          | null     | -5.0  | RuntimeException
    }
}
