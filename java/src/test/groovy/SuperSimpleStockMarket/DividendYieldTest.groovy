package SuperSimpleStockMarket

import spock.lang.Specification

import spock.lang.Title
import spock.lang.Unroll

import SuperSimpleStockMarket.components.Stock

class DividendYieldTest extends Specification {

    def "Calculate dividend yield for common stock"() {
        setup:
        Stock stock = new Stock(10, 60)

        when:
        BigDecimal expectedDividendYield =  stock.dividendYield(5)

        then:
        assert(expectedDividendYield.doubleValue() == BigDecimal.valueOf(2.0).doubleValue())
    }
}
