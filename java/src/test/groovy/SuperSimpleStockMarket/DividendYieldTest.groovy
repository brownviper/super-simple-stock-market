package SuperSimpleStockMarket

import spock.lang.Specification

import spock.lang.Title
import spock.lang.Unroll

import SuperSimpleStockMarket.components.Stock
import SuperSimpleStockMarket.components.StockPreferred

@Title("Tests for calculating Divided Yield")
class DividendYieldTest extends Specification {

    def "Calculate dividend yield for preferred stock"() {
        setup:
        StockPreferred stock = new StockPreferred(6.0, 60.0)
        BigDecimal actual = null

        when:
            actual = stock.dividendYield(30)

        then:
            assert(actual.doubleValue() == BigDecimal.valueOf(12.0).doubleValue())

    }

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
