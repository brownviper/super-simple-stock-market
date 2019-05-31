package SuperSimpleStockMarket

import spock.lang.Specification

import spock.lang.Title
import spock.lang.Unroll

import SuperSimpleStockMarket.components.Stock

class DividendYieldTest extends Specification {

    def "Calculate last dividend for common stock"() {

        Stock stock = new Stock(lastDividend, parValue)

    }

    def "application has a greeting"() {
        setup:
        def app = new App()

        when:
        def result = app.greeting

        then:
        result != null
    }
}
