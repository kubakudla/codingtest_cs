package com.jkudla.codingtest;

import com.jkudla.codingtest.dto.TradeDataDto;
import com.jkudla.codingtest.errors.BusinessErrors;
import org.junit.Test;

import java.time.LocalDate;

import static com.jkudla.codingtest.validator.TradeValueSpecificValidator.validateForwardValueDate;
import static com.jkudla.codingtest.validator.TradeValueSpecificValidator.validateSpotFXValueDate;
import static org.assertj.core.api.Assertions.assertThat;


public class TradeValueSpecificValidatorTest {

    @Test
    public void success_validateSpotFXValueDate(){
        //given
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);

        TradeDataDto dto1 = createTradeDataDto(LocalDate.of(2016,9,2), LocalDate.of(2016,9,7));
        TradeDataDto dto2 = createTradeDataDto(LocalDate.of(2016,9,8), LocalDate.of(2016,9,12));

        //when
        validateSpotFXValueDate(errors, dto1);
        validateSpotFXValueDate(errors, dto2);

        //then
        assertThat(errors.getErrorsMap()).isEmpty();
    }

    @Test
    public void error_validateSpotFXValueDate(){
        //given
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);

        TradeDataDto dto1 = createTradeDataDto(LocalDate.of(2016,9,2), LocalDate.of(2016,9,6));
        TradeDataDto dto2 = createTradeDataDto(LocalDate.of(2016,9,8), LocalDate.of(2016,9,10));

        //when
        validateSpotFXValueDate(errors, dto1);
        validateSpotFXValueDate(errors, dto2);

        //then
        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Value date is wrong for Spot product type, should be: 2016-09-07, not: 2016-09-06"));
        assertThat(errors.getErrorMessages().contains("Value date is wrong for Spot product type, should be: 2016-09-12, not: 2016-09-10"));
    }

    @Test
    public void success_validateForwardValueDate(){
        //given
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);

        TradeDataDto dto1 = createTradeDataDto(LocalDate.of(2016,9,2), LocalDate.of(2016,9,6));
        TradeDataDto dto2 = createTradeDataDto(LocalDate.of(2016,9,8), LocalDate.of(2016,9,10));

        //when
        validateForwardValueDate(errors, dto1);
        validateForwardValueDate(errors, dto2);

        //then
        assertThat(errors.getErrorsMap()).isEmpty();
    }

    @Test
    public void error_validateForwardValueDate(){
        //given
        BusinessErrors errors = new BusinessErrors();
        initKeyTradeDataDto(errors);

        TradeDataDto dto1 = createTradeDataDto(LocalDate.of(2016,9,2), LocalDate.of(2016,9,7));
        TradeDataDto dto2 = createTradeDataDto(LocalDate.of(2016,9,8), LocalDate.of(2016,9,12));

        //when
        validateForwardValueDate(errors, dto1);
        validateForwardValueDate(errors, dto2);

        //then
        assertThat(errors.getErrorsMap()).isNotEmpty();
        assertThat(errors.getErrorMessages().contains("Value date is wrong for Forward product type, shouldn't be: 2016-09-07"));
        assertThat(errors.getErrorMessages().contains("Value date is wrong for Forward product type, shouldn't be: 2016-09-12"));
    }

    private TradeDataDto createTradeDataDto(LocalDate tradeDate, LocalDate valueDate){
        TradeDataDto dto = new TradeDataDto();
        dto.setTradeDate(tradeDate);
        dto.setValueDate(valueDate);
        dto.setCcyPair("EURUSD");
        return dto;
    }

    private void initKeyTradeDataDto(BusinessErrors errors) {
        errors.setCurrentTradeDataDto(new TradeDataDto());
    }
}
