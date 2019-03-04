package com.jkudla.codingtest;

import com.jkudla.codingtest.dto.TradeDataDto;
import com.jkudla.codingtest.errors.BusinessErrors;
import com.jkudla.codingtest.util.StyleEnum;
import org.junit.Test;

import java.time.LocalDate;

import static com.jkudla.codingtest.validator.CommonValidator.validateCounterparty;
import static com.jkudla.codingtest.validator.CommonValidator.validateCurrency;
import static com.jkudla.codingtest.validator.CommonValidator.validateCurrencyPair;
import static com.jkudla.codingtest.validator.CommonValidator.validateDateAfterAnotherDate;
import static com.jkudla.codingtest.validator.CommonValidator.validateDateNotWeekendNotNonWorkingDayForCurrency;
import static com.jkudla.codingtest.validator.CommonValidator.validateValueDateNotWeekend;
import static com.jkudla.codingtest.validator.CommonValidator.validateWithEnum;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonValidatorTest {

    @Test
    public void success_ValidateValueDateNotWeekend() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        validateValueDateNotWeekend(errors, LocalDate.of(2016, 9, 5));
        validateValueDateNotWeekend(errors, LocalDate.of(2016, 9, 6));
        validateValueDateNotWeekend(errors, LocalDate.of(2016, 9, 7));
        validateValueDateNotWeekend(errors, LocalDate.of(2016, 9, 8));
        validateValueDateNotWeekend(errors, LocalDate.of(2016, 9, 9));

        assertThat(errors.getErrorsMap()).isEmpty();
    }

    @Test
    public void error_ValidateValueDateWeekend() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        validateValueDateNotWeekend(errors, LocalDate.of(2016, 9, 10));
        validateValueDateNotWeekend(errors, LocalDate.of(2016, 9, 11));

        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Date 2016-09-10 cannot be during the weekend"));
        assertThat(errors.getErrorMessages().contains("Date 2016-09-11 cannot be during the weekend"));
    }

    @Test
    public void success_validateDateAfterAnotherDate() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        validateDateAfterAnotherDate(errors, LocalDate.of(2016, 9, 12), LocalDate.of(2016, 9, 11),
            "date1", "date2");

        assertThat(errors.getErrorsMap()).isEmpty();
    }

    @Test
    public void error_validateDateAfterAnotherDate() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        validateDateAfterAnotherDate(errors, LocalDate.of(2016, 9, 10), LocalDate.of(2016, 9, 11),
                "date1", "date2");

        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Date(date2):2016-09-11 has to be before date (date1): 2016-09-10"));
    }

    @Test
    public void error_validateDateAfterAnotherDate_SameDates() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        validateDateAfterAnotherDate(errors, LocalDate.of(2016, 9, 10), LocalDate.of(2016, 9, 10),
                "date1", "date2");

        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Date(date2):2016-09-10 has to be before date (date1): 2016-09-10"));
    }

    @Test
    public void success_validateCounterparty() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        validateCounterparty(errors, "PLUTO1");
        validateCounterparty(errors, "PLUTO2");

        assertThat(errors.getErrorsMap()).isEmpty();
    }

    @Test
    public void error_validateCounterparty() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        String plut01 = "PLUT01";
        String pluto3 = "PLUTO3";
        validateCounterparty(errors, plut01);
        validateCounterparty(errors, pluto3);

        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Counterparty not supported: " + plut01));
        assertThat(errors.getErrorMessages().contains("Counterparty not supported: " + pluto3));
    }

    @Test
    public void success_validateCurrency() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);

        validateCurrency(errors, "EUR", "field1");
        validateCurrency(errors, "USD", "field2");
        validateCurrency(errors, "PLN", "field3");

        assertThat(errors.getErrorsMap()).isEmpty();
    }

    @Test
    public void error_validateCurrency() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        String field1 = "field1";
        String field2 = "field2";
        String field3 = "field3";
        String eu = "EU";
        String usd = "Usd";
        String zloty = "zloty";

        validateCurrency(errors, eu, field1);
        validateCurrency(errors, usd, field2);
        validateCurrency(errors, zloty, field3);

        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Currency validation failed for field: " + field1 + ". Failed currency: " + eu));
        assertThat(errors.getErrorMessages().contains("Currency validation failed for field: " + field2 + ". Failed currency: " + usd));
        assertThat(errors.getErrorMessages().contains("Currency validation failed for field: " + field3 + ". Failed currency: " + zloty));
    }

    @Test
    public void success_validateCurrencyPair() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        validateCurrencyPair(errors, "EURUSD", "field1");
        validateCurrencyPair(errors, "USDEUR", "field2");
        validateCurrencyPair(errors, "PLNEUR", "field3");

        assertThat(errors.getErrorsMap()).isEmpty();
    }

    @Test
    public void error_validateCurrencyPair() {
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);
        
        String field1 = "field1";
        String field2 = "field2";
        String field3 = "field3";
        String pair1 = "EURUSd";
        String pair2 = "USDER";
        String pair3 = "PLN/EUR";

        validateCurrencyPair(errors, pair1, field1);
        validateCurrencyPair(errors, pair2, field2);
        validateCurrencyPair(errors, pair3, field3);

        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Currency pair validation failed for field: " + field1 + ". Failed currency: " + pair1));
        assertThat(errors.getErrorMessages().contains("Currency pair validation failed for field: " + field2 + ". Failed currency: " + pair2));
        assertThat(errors.getErrorMessages().contains("Currency pair validation failed for field: " + field3 + ". Failed currency: " + pair3));
    }

    @Test
    public void success_validateValueDateNotWeekendNotNonWorkingDayForCurrency(){
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);

        validateDateNotWeekendNotNonWorkingDayForCurrency(errors, LocalDate.of(2016,9,6), "EURUSD");
        validateDateNotWeekendNotNonWorkingDayForCurrency(errors, LocalDate.of(2016,9,26), "EURUSD");

        assertThat(errors.getErrorsMap()).isEmpty();
    }

    @Test
    public void error_validateValueDateNotWeekendNotNonWorkingDayForCurrency(){
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);

        LocalDate date1 = LocalDate.of(2016, 9, 5);
        LocalDate date2 = LocalDate.of(2016, 9, 11);

        validateDateNotWeekendNotNonWorkingDayForCurrency(errors, date1,
                "EURUSD");
        validateDateNotWeekendNotNonWorkingDayForCurrency(errors, date2,
                "EURUSD");

        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Value date is holiday: " + date1));
        assertThat(errors.getErrorMessages().contains("Date " + date2 + " cannot be during the weekend"));
    }

    @Test
    public void success_validateWithEnum(){
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);

        validateWithEnum(errors, "American", "style", StyleEnum.class);
        validateWithEnum(errors, "European", "style", StyleEnum.class);

        assertThat(errors.getErrorsMap()).isEmpty();
    }

    @Test
    public void error_validateWithEnum(){
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);

        validateWithEnum(errors, "wrong value", "style", StyleEnum.class);
        validateWithEnum(errors, "America", "style", StyleEnum.class);

        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Field (style): 'wrong value' is incorrect. Correct values: American,European"));
        assertThat(errors.getErrorMessages().contains("Field (style): 'America' is incorrect. Correct values: American," + "European"));
    }

    private void initKeyTradeDataDto(BusinessErrors errors) {
        errors.setCurrentTradeDataDto(new TradeDataDto());
    }

}
