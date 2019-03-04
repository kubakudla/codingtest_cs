package com.jkudla.codingtest.validator;

import com.jkudla.codingtest.dto.TradeDataDto;
import com.jkudla.codingtest.errors.BusinessErrors;

import java.time.LocalDate;

import static com.jkudla.codingtest.validator.CommonValidator.isHolidayForGivenDateAndCurrencyPair;
import static com.jkudla.codingtest.validator.CommonValidator.isWeekend;

public class TradeValueSpecificValidator {
    /**
     * SPOT: T+2 - is the conventional time period for spot transactions, which means settlement date will be 2
     * working days after transaction day
     *
     * @param errors
     * @param tradeDataDto
     */
    public static void validateSpotFXValueDate(BusinessErrors errors, TradeDataDto tradeDataDto) {
        LocalDate tradeDate = tradeDataDto.getTradeDate();
        LocalDate valueDate = tradeDataDto.getValueDate();
        LocalDate spotDate = findSpotDate(tradeDate, tradeDataDto.getCcyPair());

        if (!spotDate.equals(valueDate)) {
            errors.add("Value date is wrong for Spot product type, should be: " + spotDate + ", not: " + valueDate);
        }
    }

    private static LocalDate findSpotDate(LocalDate tradeDate, String ccyPair) {
        LocalDate spotDate = tradeDate;
        int daysTwoAdd = 2;
        while (daysTwoAdd != 0) {
            spotDate = spotDate.plusDays(1L);
            if (isHolidayOrWeekend(spotDate, ccyPair)) {
                continue;
            }
            daysTwoAdd--;
        }
        return spotDate;
    }

    private static boolean isHolidayOrWeekend(LocalDate date, String ccyPair) {
        return isHolidayForGivenDateAndCurrencyPair(date, ccyPair) ||
                isWeekend(date);
    }

    /**
     * FORWARD: a transaction on T date with value date other than T+2 is known as a forward transaction
     *
     * @param tradeDataDto
     */
    public static void validateForwardValueDate(BusinessErrors errors, TradeDataDto tradeDataDto) {
        LocalDate tradeDate = tradeDataDto.getTradeDate();
        LocalDate spotDate = findSpotDate(tradeDate, tradeDataDto.getCcyPair());
        if (spotDate.equals(tradeDataDto.getValueDate())) {
            errors.add("Value date is wrong for Forward product type, shouldn't be: " + spotDate);
        }
    }
}
