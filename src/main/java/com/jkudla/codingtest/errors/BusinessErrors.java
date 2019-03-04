package com.jkudla.codingtest.errors;

import com.jkudla.codingtest.dto.TradeDataDto;

import java.util.Set;
import java.util.TreeSet;

public class BusinessErrors extends ValidationErrors {

    private TradeDataDto currentTradeDataDto;

    public void add(String errorMessage) {
        if (currentTradeDataDto == null) {
            throw new RuntimeException("currentTradeDataDto not set!");
        }
        Set<String> errorsSet = errorsMap.get(currentTradeDataDto);
        if (errorsSet == null) {
            errorsSet = new TreeSet<>();
            errorsMap.put(currentTradeDataDto, errorsSet);
        }
        errorsSet.add(errorMessage + TRADE_DATA_SPLITTER + currentTradeDataDto.toString());
    }

    public void setCurrentTradeDataDto(TradeDataDto currentTradeDataDto) {
        this.currentTradeDataDto = currentTradeDataDto;
    }

}
