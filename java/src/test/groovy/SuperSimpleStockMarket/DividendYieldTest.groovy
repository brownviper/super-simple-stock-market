package SuperSimpleStockMarket

import SuperSimpleStockMarket.components.StockItemBuilder
import spock.lang.Title
import spock.lang.Unroll

import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.COMMON
import static SuperSimpleStockMarket.components.StockItemBuilder.StockType.PREFERRED

@Title("Tests for calculating Divided Yield")
class DividendYieldTest extends CommonSpecification {

    @Unroll
    def "Calculate dividend yield for stockSymbol = [#stockSymbol], stockType = [#stockType], lastDividend =[#lastDividend], fixedDividend=[#fixedDividend], parValue=[#parValue] and price=[#price]"(
            desc, stockSymbol, stockType, lastDividend, fixedDividend, parValue, price, expectedDividendYield
    ) {
        setup:
        Class exception = null
        BigDecimal actual = null

        when:
        try{
            StockItemBuilder.StockItem stockItem = createStockItem(stockSymbol, stockType, lastDividend, fixedDividend, parValue)
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
        desc                                            | stockSymbol | stockType | lastDividend | fixedDividend | parValue | price | expectedDividendYield
        "calculate dividend yield for preferred stock"  |    "GIN"   | PREFERRED | null         | 10.0          | 5.0      | 2.0   | 25.0
        "throws if price is zero for preferred stock"   | "GIN"   | PREFERRED | null         | 10.0          | 5.0      | 0.0   | RuntimeException
        "can calculate dividend yield for common stock" | "TEA"   | COMMON    | 10.0         | null          | null     | 5.0   | 2.0
        "throws if price is zero for common stock"      | "POP"   | COMMON    | 10.0         | null          | null     | 0.0   | RuntimeException
        "throws if price is less that zero"             | "ALE"   | COMMON    | 10.0         | null          | null     | -5.0  | RuntimeException
        "throws if stock type is not defined"           | "JOE"   | null      | 10.0         | 10.0          | 5.0      | 2.0   | RuntimeException
    }
}
