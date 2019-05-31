package SuperSimpleStockMarket

import spock.lang.Specification

import spock.lang.Title
import spock.lang.Unroll

import SuperSimpleStockMarket.components.Stock

class DividendYieldTest extends Specification {

    @Unroll
    def "Calculate dividend yield for common stock, lastDividend=[#lastDividend], and price=[#price]"(
            lastDividend, price, expectedDividendYield
    ) {
        setup:
        Stock stock = new Stock(lastDividend)

        when:
        BigDecimal actual =  stock.dividendYield(price)

        then:
        assert(actual.doubleValue() == BigDecimal.valueOf(expectedDividendYield).doubleValue())

        where:
        lastDividend | price | expectedDividendYield
        10.0           | 5.0     | 2.0
        10.0           | 0.0     | Exception
    }
}
