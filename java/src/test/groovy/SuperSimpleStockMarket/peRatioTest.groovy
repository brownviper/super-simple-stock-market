package SuperSimpleStockMarket

import SuperSimpleStockMarket.components.StockItemBuilder
import spock.lang.Title
import spock.lang.Unroll

import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.COMMON
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.PREFERRED

@Title("Tests for calculating P/E Ratio")
class peRatioTest extends CommonSpecification {

    @Unroll
    def "Calculate P/E ratio for stockSymbol = [#stockSymbol], stockType=[#stockType], lastDividend=[#lastDivided], price=[#price]" (
            desc, stockSymbol, stockType, lastDividend, price, expectedDividendYield
    ) {
        setup:
        Class exception = null
        BigDecimal actual = null

        when:
        try{
            StockItemBuilder.StockItem stockItem = createStockItem(stockSymbol, stockType, lastDividend, null, null)
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
        desc                                          | stockSymbol | stockType | lastDividend | price | expectedDividendYield
        "calculate P/E ratio for preferred stock"     |    "GIN"   | PREFERRED | 10.0         | 30.0  | 3.0
        "throws if price is zero for preferred stock" |     "GIN"   | PREFERRED | 0.0          | 30.0  | RuntimeException
        "calculate P/E ratio for common stock"        |    "JOE"   | COMMON    | 10.0         | 5     | 0.5
        "throws if price is zero for common stock"    |    "POP"   | COMMON    | 0.0          | 5     | RuntimeException
    }
}
