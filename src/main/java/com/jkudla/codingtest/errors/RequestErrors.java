package com.jkudla.codingtest.errors;

import com.jkudla.codingtest.dto.TradeDataDto;

import java.util.Set;
import java.util.stream.Collectors;

public class RequestErrors extends ValidationErrors {

    public void addAll(TradeDataDto tradeDataDto, Set<String> errorsSet) {
        errorsMap.put(tradeDataDto, getErrorListWithTradeDataLinkage(tradeDataDto, errorsSet));
    }

    private Set<String> getErrorListWithTradeDataLinkage(TradeDataDto tradeDataDto, Set<String> errorsSet) {
        return errorsSet.stream().map(v -> v = v + TRADE_DATA_SPLITTER + tradeDataDto.toString()).collect(Collectors.toSet());
    }
}
