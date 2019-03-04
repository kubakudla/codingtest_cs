package com.jkudla.codingtest.integration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jkudla.codingtest.Application;
import com.jkudla.codingtest.result.TradeDataResult;
import com.jkudla.codingtest.util.ResponseStatusEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.jayway.restassured.RestAssured.given;
import static com.jkudla.codingtest.dto.TradeDataDto.AMOUNT1_CANNOT_BE_NULL;
import static com.jkudla.codingtest.dto.TradeDataDto.AMOUNT2_CANNOT_BE_NULL;
import static com.jkudla.codingtest.dto.TradeDataDto.CCY_PAIR_CANNOT_BE_NULL;
import static com.jkudla.codingtest.dto.TradeDataDto.CUSTOMER_CANNOT_BE_NULL;
import static com.jkudla.codingtest.dto.TradeDataDto.DIRECTION_CANNOT_BE_NULL;
import static com.jkudla.codingtest.dto.TradeDataDto.LEGAL_ENTITY_CANNOT_BE_NULL;
import static com.jkudla.codingtest.dto.TradeDataDto.RATE_CANNOT_BE_NULL;
import static com.jkudla.codingtest.dto.TradeDataDto.TRADER_CANNOT_BE_NULL;
import static com.jkudla.codingtest.dto.TradeDataDto.TYPE_CANNOT_BE_NULL;
import static com.jkudla.codingtest.util.TradeDataResultMapper.readTradeDataResult;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TradeDataIntegrationTest {

    public static final String API_TRADE_DATA = "/codingtest/api/trade/data";
    public static final String TEST_PATH = "src/test/resources/";

    @LocalServerPort
    private int port;

    @Before
    public void setUpRestAssured() {
        RestAssured.port = port;
    }

    @Test
    public void shouldFailProvidedSampleTestFile() throws IOException {
        //given
        String body = new String(Files.readAllBytes(Paths.get(TEST_PATH + "sample_test_trade_data.json")));

        //when
        Response response = sendTradeData(body, 400);

        //then
        String responseString = response.getBody().prettyPrint();
        TradeDataResult tradeDataResult = readTradeDataResult(responseString);

        assertThat(tradeDataResult.getStatus()).isEqualTo(ResponseStatusEnum.ERROR);
        assertThat(tradeDataResult.getErrors().size()).isEqualTo(11);
        assertThat(tradeDataResult.getErrors().contains("Date(tradeDate):2016-08-11 has to be before date (valueDate): 2016-08-08 LINKAGE: TradeDataDto[customer='PLUT02', ccyPair='EURUSD', type='Forward', direction='BUY', style='null', strategy='null', payCcy='null', premiumCcy='null', premiumType='null', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=2016-08-08, deliveryDate=null, expiryDate=null, excerciseStartDate=null, premiumDate=null, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=null]"));
        assertThat(tradeDataResult.getErrors().contains("Date(expiryDate):2016-08-25 has to be before date (deliveryDate): 2016-08-22 LINKAGE: TradeDataDto[customer='PLUTO1', ccyPair='EURUSD', type='VanillaOption', direction='BUY', style='EUROPEAN', strategy='CALL', payCcy='USD', premiumCcy='USD', premiumType='%USD', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=null, deliveryDate=2016-08-22, expiryDate=2016-08-25, excerciseStartDate=null, premiumDate=2016-08-12, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=0.20]\""));
        assertThat(tradeDataResult.getErrors().contains("Value date is wrong for Spot product type, should be: 2016-08-15, not: 2016-08-22 LINKAGE: TradeDataDto[customer='PLUTO1', ccyPair='EURUSD', type='Spot', direction='SELL', style='null', strategy='null', payCcy='null', premiumCcy='null', premiumType='null', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=2016-08-22, deliveryDate=null, expiryDate=null, excerciseStartDate=null, premiumDate=null, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=null]"));
        assertThat(tradeDataResult.getErrors().contains("Date(tradeDate):2016-08-11 has to be before date (excerciseStartDate): 2016-08-10 LINKAGE: TradeDataDto[customer='PLUTO1', ccyPair='EURUSD', type='VanillaOption', direction='BUY', style='AMERICAN', strategy='CALL', payCcy='USD', premiumCcy='USD', premiumType='%USD', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=null, deliveryDate=2016-08-22, expiryDate=2016-08-19, excerciseStartDate=2016-08-10, premiumDate=2016-08-12, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=0.20]"));
        assertThat(tradeDataResult.getErrors().contains("Date(tradeDate):2016-08-11 has to be before date (valueDate): 2016-08-08 LINKAGE: TradeDataDto[customer='PLUTO2', ccyPair='EURUSD', type='Forward', direction='BUY', style='null', strategy='null', payCcy='null', premiumCcy='null', premiumType='null', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=2016-08-08, deliveryDate=null, expiryDate=null, excerciseStartDate=null, premiumDate=null, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=null]"));
        assertThat(tradeDataResult.getErrors().contains("Counterparty not supported: PLUT02 LINKAGE: TradeDataDto[customer='PLUT02', ccyPair='EURUSD', type='Forward', direction='BUY', style='null', strategy='null', payCcy='null', premiumCcy='null', premiumType='null', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=2016-08-08, deliveryDate=null, expiryDate=null, excerciseStartDate=null, premiumDate=null, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=null]"));
        assertThat(tradeDataResult.getErrors().contains("Date 2016-08-21 cannot be during the weekend LINKAGE: TradeDataDto[customer='PLUTO2', ccyPair='EURUSD', type='Forward', direction='BUY', style='null', strategy='null', payCcy='null', premiumCcy='null', premiumType='null', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=2016-08-21, deliveryDate=null, expiryDate=null, excerciseStartDate=null, premiumDate=null, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=null]"));
        assertThat(tradeDataResult.getErrors().contains("Counterparty not supported: PLUTO3 LINKAGE: TradeDataDto[customer='PLUTO3', ccyPair='EURUSD', type='Forward', direction='BUY', style='null', strategy='null', payCcy='null', premiumCcy='null', premiumType='null', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=2016-08-22, deliveryDate=null, expiryDate=null, excerciseStartDate=null, premiumDate=null, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=null]"));
        assertThat(tradeDataResult.getErrors().contains("Date(tradeDate):2016-08-11 has to be before date (excerciseStartDate): 2016-08-10 LINKAGE: TradeDataDto[customer='PLUTO3', ccyPair='EURUSD', type='VanillaOption', direction='SELL', style='AMERICAN', strategy='CALL', payCcy='USD', premiumCcy='USD', premiumType='%USD', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=null, deliveryDate=2016-08-22, expiryDate=2016-08-19, excerciseStartDate=2016-08-10, premiumDate=2016-08-12, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=0.20]"));
        assertThat(tradeDataResult.getErrors().contains("Date(expiryDate):2016-08-25 has to be before date (deliveryDate): 2016-08-22 LINKAGE: TradeDataDto[customer='PLUTO1', ccyPair='EURUSD', type='VanillaOption', direction='BUY', style='AMERICAN', strategy='CALL', payCcy='USD', premiumCcy='USD', premiumType='%USD', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=null, deliveryDate=2016-08-22, expiryDate=2016-08-25, excerciseStartDate=2016-08-12, premiumDate=2016-08-12, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=0.20]"));
        assertThat(tradeDataResult.getErrors().contains("Counterparty not supported: PLUTO3 LINKAGE: TradeDataDto[customer='PLUTO3', ccyPair='EURUSD', type='VanillaOption', direction='SELL', style='AMERICAN', strategy='CALL', payCcy='USD', premiumCcy='USD', premiumType='%USD', legalEntity='CS Zurich', trader='Johann Baumfiddler', tradeDate=2016-08-11, valueDate=null, deliveryDate=2016-08-22, expiryDate=2016-08-19, excerciseStartDate=2016-08-10, premiumDate=2016-08-12, amount1=1000000.00, amount2=1120000.00, rate=1.12, premium=0.20]"));
    }

    @Test
    public void shouldShowSuccessMessage() throws IOException {
        //given
        String body = new String(Files.readAllBytes(Paths.get(TEST_PATH + "sample_test_trade_data_success.json")));

        //when
        Response response = sendTradeData(body, 200);

        //then
        String responseString = response.getBody().prettyPrint();
        TradeDataResult tradeDataResult = readTradeDataResult(responseString);

        assertThat(tradeDataResult.getStatus()).isEqualTo(ResponseStatusEnum.SUCCESS);
        assertThat(tradeDataResult.getErrors().size()).isEqualTo(0);
    }

    @Test
    public void shouldFailMissingRequiredFields() throws IOException {
        //given
        String body = new String(Files.readAllBytes(Paths.get(TEST_PATH + "sample_test_trade_data_missing_required_fields.json")));

        //when
        Response response = sendTradeData(body, 400);

        //then
        String responseString = response.getBody().prettyPrint();
        TradeDataResult tradeDataResult = readTradeDataResult(responseString);

        assertThat(tradeDataResult.getStatus()).isEqualTo(ResponseStatusEnum.ERROR);
        assertThat(tradeDataResult.getErrors().size()).isEqualTo(9);
        assertThat(tradeDataResult.getErrors().contains(CUSTOMER_CANNOT_BE_NULL));
        assertThat(tradeDataResult.getErrors().contains(CCY_PAIR_CANNOT_BE_NULL));
        assertThat(tradeDataResult.getErrors().contains(TYPE_CANNOT_BE_NULL));
        assertThat(tradeDataResult.getErrors().contains(DIRECTION_CANNOT_BE_NULL));
        assertThat(tradeDataResult.getErrors().contains(LEGAL_ENTITY_CANNOT_BE_NULL));
        assertThat(tradeDataResult.getErrors().contains(TRADER_CANNOT_BE_NULL));
        assertThat(tradeDataResult.getErrors().contains(AMOUNT1_CANNOT_BE_NULL));
        assertThat(tradeDataResult.getErrors().contains(AMOUNT2_CANNOT_BE_NULL));
        assertThat(tradeDataResult.getErrors().contains(RATE_CANNOT_BE_NULL));
    }

    @Test
    public void shouldFailWrongValueDate() throws IOException {
        //given
        String body = new String(Files.readAllBytes(Paths.get(TEST_PATH + "sample_test_trade_data_wrong_date.json")));

        //when
        Response response = sendTradeData(body, 400);

        //then
        String responseString = response.getBody().prettyPrint();
        TradeDataResult tradeDataResult = readTradeDataResult(responseString);

        assertThat(tradeDataResult.getStatus()).isEqualTo(ResponseStatusEnum.ERROR);
        assertThat(tradeDataResult.getErrors().size()).isEqualTo(1);
        assertThat(tradeDataResult.getErrors().contains("Value date is wrong for Spot product type, should be: 2016-08-15, not: 2016-08-16"));
    }

    private Response sendTradeData(String body, int statusCode) {
        return given().
            contentType("application/json").
            body(body).
            when().
            post(API_TRADE_DATA).
            then().
            statusCode(statusCode).
            extract()
            .response();
    }

}
