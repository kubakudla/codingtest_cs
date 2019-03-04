Application is run with Maven with a command which also executes tests :

mvn package

To run application:

java -jar target/codingtest.jar


Swagger UI (for testing directly rest service) can be run from:

http://localhost:8081/codingtest/swagger-ui.html

Rest service is here:

http://localhost:8081/codingtest/api/trade/data


Main assumptions:
- from extra activities point 3) is done (Added support for swagger and some useful annotations)
- there are junit tests and integration tests (TradeDataIntegrationTest). Only some tests are present as an example.
- added requirements for mandatadaory fields (@NotNull annotations). If any of those fields are missing, other validation isn't done.
- https://www.theice.com/publicdocs/clear_us/ICEU_Currency_Bank_Holidays_for_2016.pdf was used for holidays validation