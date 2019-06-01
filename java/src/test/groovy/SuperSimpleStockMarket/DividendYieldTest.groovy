package SuperSimpleStockMarket

import spock.lang.Specification

import spock.lang.Title
import spock.lang.Unroll

import SuperSimpleStockMarket.components.StockItem
import SuperSimpleStockMarket.components.CommonStockItem
import SuperSimpleStockMarket.components.PreferredStockItem

@Title("Tests for calculating Divided Yield")
class DividendYieldTest extends Specification {

    @Unroll
    def "Calculate dividend yield for preferred stock, fixedDividend=[#fixedDividend], parValue=[#parValue] and price=[#price]"(
            fixedDividend, parValue, price, expectedDividendYield
    ) {
        setup:
        Class exception = null
        PreferredStockItem stock = new PreferredStockItem(fixedDividend, parValue)
        BigDecimal actual = null

        when:
        try{
            actual = stock.dividendYield(price)
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
        fixedDividend | parValue | price | expectedDividendYield
        10.0          | 5.0      | 2.0   | 25.0
        10.0          | 5.0      | 0.0   | RuntimeException
    }

    @Unroll
    def "Calculate dividend yield for common stock, lastDividend=[#lastDividend], and price=[#price]"(
            lastDividend, price, expectedDividendYield
    ) {
        setup:
        Class exception = null
        CommonStockItem stock = new CommonStockItem(lastDividend)
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
