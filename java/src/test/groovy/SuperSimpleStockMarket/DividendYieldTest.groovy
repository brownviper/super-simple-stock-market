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
        Class exception = null
        Stock stock = new Stock(lastDividend)
        BigDecimal actual = null

        when:
        try{
            actual =  stock.dividendYield(price)
        } catch (RuntimeException e) {
            exception = e.class
        }

        then:
        if (expectedDividendYield instanceof Class) {
            assert expectedDividendYield.equals(exception)
        } else {
            assert(actual.doubleValue() == BigDecimal.valueOf(expectedDividendYield).doubleValue())
        }

        where:
        lastDividend | price | expectedDividendYield
        10.0         | 5.0   | 2.0
        10.0         | 0.0   | RuntimeException
        10.0         | -5.0   | RuntimeException
    }
}
