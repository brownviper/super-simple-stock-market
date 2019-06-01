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
    def "Calculate P/E ratio for stockType=[#stockType], lastDividend=[#lastDivided], price=[#price]" (
            stockType, lastDividend, price, expectedDividendYield
    ) {
        setup:
        Class exception = null
        BigDecimal actual = null
        StockItemBuilder builder = StockItemBuilder.getBuilder()
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
        stockType | lastDividend | price | expectedDividendYield
        PREFERRED | 10.0         | 30.0  | 3.0
        PREFERRED | 0.0          | 30.0  | RuntimeException
    }
}
