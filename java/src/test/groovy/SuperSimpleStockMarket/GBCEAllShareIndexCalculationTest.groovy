package SuperSimpleStockMarket

import SuperSimpleStockMarket.components.StockMarketService
import SuperSimpleStockMarket.components.TradeItemsRepository
import spock.lang.Title

@Title("Test GBCE All Share Index calculation")
class GBCEAllShareIndexCalculationTest extends CommonSpecification {

    def "Calculate GBCE All Share Index for genereate stock markek"() {

        setup:
        TradeItemsRepository repository = new TradeItemsRepository()
        StockMarketService service = new StockMarketService(repository)
        populateStockMarketWithData(service, BigDecimal.valueOf(100), Integer.valueOf(50))

        when:
        BigDecimal result = service.calculateDBCEAllShareIndex()

        then:
        result == BigDecimal.valueOf(100)
    }

}
