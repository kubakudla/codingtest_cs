package com.jkudla.codingtest.service;

import com.jkudla.codingtest.dto.TradeDataDto;
import com.jkudla.codingtest.errors.BusinessErrors;
import com.jkudla.codingtest.errors.RequestErrors;
import com.jkudla.codingtest.errors.ValidationErrors;
import com.jkudla.codingtest.util.StyleEnum;
import com.jkudla.codingtest.util.TypeEnum;
import com.jkudla.codingtest.validator.TradeDataDtoValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.jkudla.codingtest.validator.CommonValidator.validateCounterparty;
import static com.jkudla.codingtest.validator.CommonValidator.validateCurrency;
import static com.jkudla.codingtest.validator.CommonValidator.validateCurrencyPair;
import static com.jkudla.codingtest.validator.CommonValidator.validateDateAfterAnotherDate;
import static com.jkudla.codingtest.validator.CommonValidator.validateDateNotWeekendNotNonWorkingDayForCurrency;
import static com.jkudla.codingtest.validator.CommonValidator.validateWithEnum;
import static com.jkudla.codingtest.validator.TradeValueSpecificValidator.validateForwardValueDate;
import static com.jkudla.codingtest.validator.TradeValueSpecificValidator.validateSpotFXValueDate;

/**
 * Service for trade data validation
 * <p>
 */
@Service
public class TradeValueService {

    public static final String PAY_CCY = "payCcy";
    public static final String PREMIUM_CCY = "premiumCcy";
    public static final String CCY_PAIR = "ccyPair";
    public static final String VALUE_DATE = "valueDate";
    public static final String TRADE_DATE = "tradeDate";
    public static final String DELIVERY_DATE = "deliveryDate";
    public static final String EXCERCISE_START_DATE = "excerciseStartDate";
    public static final String EXPIRY_DATE = "expiryDate";
    public static final String PREMIUM_DATE = "premiumDate";
    public static final String STYLE = "style";

    public ValidationErrors validate(List<TradeDataDto> tradeDataDtoList) {
        RequestErrors requestErrors = TradeDataDtoValidator.validate(tradeDataDtoList);
        if (!requestErrors.isEmpty()) {
            return requestErrors;
        }
        return validateBusinessRequirements(tradeDataDtoList);
    }


    public ValidationErrors validateBusinessRequirements(List<TradeDataDto> tradeDataDtoList) {
        BusinessErrors errors = new BusinessErrors();
        validateAll(tradeDataDtoList, errors);
        validateSpotAndForward(tradeDataDtoList, errors);
        validateOptions(tradeDataDtoList, errors);
        return errors;
    }

    private void validateAll(List<TradeDataDto> tradeDataDtoList, BusinessErrors requestErrors) {
        for (TradeDataDto tradeDataDto : tradeDataDtoList) {
            initKeyTradeDataDto(requestErrors, tradeDataDto);

            validateDateAfterAnotherDate(requestErrors, tradeDataDto.getValueDate(), tradeDataDto.getTradeDate(), VALUE_DATE, TRADE_DATE);
            validateCounterparty(requestErrors, tradeDataDto.getCustomer());
            validateCurrency(requestErrors, tradeDataDto.getPayCcy(), PAY_CCY);
            validateCurrency(requestErrors, tradeDataDto.getPremiumCcy(), PREMIUM_CCY);
            validateCurrencyPair(requestErrors, tradeDataDto.getCcyPair(), CCY_PAIR);
            validateDateNotWeekendNotNonWorkingDayForCurrency(requestErrors, tradeDataDto.getValueDate(), tradeDataDto.getCcyPair());
        }
    }

    private void validateSpotAndForward(List<TradeDataDto> tradeDataDtoList, BusinessErrors errors) {
        validateSpot(tradeDataDtoList, errors);
        validateForward(tradeDataDtoList, errors);
    }

    private void validateSpot(List<TradeDataDto> tradeDataDtoList, BusinessErrors errors) {
        tradeDataDtoList.stream().filter(dto -> dto.getType().equalsIgnoreCase(TypeEnum.SPOT.name())).
            forEach(dto -> {
                initKeyTradeDataDto(errors, dto);
                validateSpotFXValueDate(errors, dto);
            });
    }

    private void validateForward(List<TradeDataDto> tradeDataDtoList, BusinessErrors errors) {
        tradeDataDtoList.stream().filter(dto -> dto.getType().equalsIgnoreCase(TypeEnum.FORWARD.name())).
            forEach(dto -> {
                initKeyTradeDataDto(errors, dto);
                validateForwardValueDate(errors, dto);
            });
    }

    private void validateOptions(List<TradeDataDto> tradeDataDtoList, BusinessErrors errors) {
        validateAllOptions(tradeDataDtoList, errors);
        validateAmericanOptions(tradeDataDtoList, errors);
    }

    private void validateAllOptions(List<TradeDataDto> tradeDataDtoList, BusinessErrors errors) {
        tradeDataDtoList = tradeDataDtoList.stream().filter(dto -> dto.getType().equalsIgnoreCase(TypeEnum.VANILLAOPTION.name())).collect(Collectors.toList());

        for (TradeDataDto dto : tradeDataDtoList) {
            initKeyTradeDataDto(errors, dto);
            validateWithEnum(errors, dto.getStyle(), STYLE, StyleEnum.class);
            validateDateAfterAnotherDate(errors, dto.getDeliveryDate(), dto.getExpiryDate(), DELIVERY_DATE, EXPIRY_DATE);
            validateDateAfterAnotherDate(errors, dto.getDeliveryDate(), dto.getPremiumDate(), DELIVERY_DATE, PREMIUM_DATE);
        }
    }

    private void validateAmericanOptions(List<TradeDataDto> tradeDataDtoList, BusinessErrors errors) {
        tradeDataDtoList = tradeDataDtoList.stream().filter(dto -> dto.getType().equalsIgnoreCase(TypeEnum.VANILLAOPTION.name())).
            filter(dto -> dto.getStyle().equalsIgnoreCase(StyleEnum.American.name())).collect(Collectors.toList());

        for (TradeDataDto dto : tradeDataDtoList) {
            initKeyTradeDataDto(errors, dto);
            validateDateAfterAnotherDate(errors, dto.getExcerciseStartDate(), dto.getTradeDate(), EXCERCISE_START_DATE, TRADE_DATE);
            validateDateAfterAnotherDate(errors, dto.getExpiryDate(), dto.getExcerciseStartDate(), EXPIRY_DATE, EXCERCISE_START_DATE);
        }
    }

    private void initKeyTradeDataDto(BusinessErrors errors, TradeDataDto dto) {
        errors.setCurrentTradeDataDto(dto);
    }
}
