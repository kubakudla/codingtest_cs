package com.jkudla.codingtest.validator;

import com.jkudla.codingtest.dto.TradeDataDto;
import com.jkudla.codingtest.errors.RequestErrors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TradeDataDtoValidator {

    public static RequestErrors validate(List<TradeDataDto> tradeDataDtoList) {
        RequestErrors requestErrors = new RequestErrors();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Map<TradeDataDto, Set<ConstraintViolation<TradeDataDto>>> violations = new HashMap<>();
        for (TradeDataDto tradeDataDto : tradeDataDtoList) {
            violations.put(tradeDataDto, validator.validate(tradeDataDto));
        }

        for (Map.Entry<TradeDataDto, Set<ConstraintViolation<TradeDataDto>>> violationEntry : violations.entrySet()) {
            Set<String> violationMessages = violationEntry.getValue().stream().map(v -> v.getMessage()).collect(Collectors.toSet());
            if(violationMessages.size() > 0){
                requestErrors.addAll(violationEntry.getKey(), violationMessages);
            }
        }
        return requestErrors;
    }
}
